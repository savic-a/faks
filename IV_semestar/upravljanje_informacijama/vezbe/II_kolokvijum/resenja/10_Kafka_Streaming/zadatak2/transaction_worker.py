# Da bi ste mogli da pokrenete resenje treba da u kafka brokeru izvrsite naredne dve komande
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic generated_queries
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic validated_queries
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic queries_for_execution
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic executed_queries

import faust

app = faust.App('transaction_worker', broker = 'kafka://localhost:9092')

class Query(faust.Record):
    entity_id: int
    # Za naredna dva polja postavljamo default vrednosti da 
    # ne bi doslo do greske kada se ova dva polja ne nadju u objektu
    # koji se procita iz kafka queue-a
    query_type: str = ''
    valid: bool = True
    
valedated_queries_topic = app.topic('validated_queries', value_type=Query)
queries_for_execution_topic = app.topic('queries_for_execution', value_type=Query)
executed_queries_topic = app.topic('executed_queries', value_type=Query)

# Ovde cemo cuvadi sve aktivne transakcije, gde ce kljuc biti id entiteta,
# a vrednost tip upita koji se izvrsava (da li je select upit ili neki drugi,
# koji modifikuje sadrzaj). Ovo nam je bitno jer ako se trenutno izvrsava SELECT
# upit, i dodje jos jedan select upit, on moze da se izvrsi, ali ako dodje neki 
# modifikujuci upit, on ne moze
active_transactions={}

@app.agent(valedated_queries_topic)
async def validated_queries(queries: faust.Stream):
    global active_transactions

    async for query in queries:
        if query.entity_id in active_transactions:
            if active_transactions[query.entity_id][0]=='MODIFYING' or query.query_type != 'SELECT':
                print("Blocked query:")
                print(query)
                continue
        
        if query.query_type != 'SELECT':
            active_transactions[query.entity_id] = ('MODIFYING',1)
        else:
            if query.entity_id in active_transactions:
                current_number_of_select_queries = active_transactions[query.entity_id][1]
                active_transactions[query.entity_id] = ('SELECT',current_number_of_select_queries+1)
            else:
                active_transactions[query.entity_id] = ('SELECT',1)

        print('New query starting:')
        print(query)
        await queries_for_execution_topic.send(value=query)

@app.agent(executed_queries_topic)
async def executed_queries(queries: faust.Stream):
    global active_transactions

    async for query in queries:
        print('Query done for entity with id %d' % (query.entity_id))
        if active_transactions[query.entity_id][0] == 'MODIFYING' or \
            active_transactions[query.entity_id][1] == 1:
            del active_transactions[query.entity_id]
        else:
            current_number_of_select_queries = active_transactions[query.entity_id][1]
            active_transactions[query.entity_id] = ('SELECT',current_number_of_select_queries-1)
