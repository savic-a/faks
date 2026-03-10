# Da bi ste mogli da pokrenete resenje treba da u kafka brokeru izvrsite naredne dve komande
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic fraud_detection_input_topic
# /bin/kafka-topics --create --bootstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic fraud_detection_output_topic

import faust
import time

app = faust.App('primerApp', broker = 'kafka://localhost:9092')

class Transakcija(faust.Record):
    broj_racuna: int
    kolicina: int
    
input_topic = app.topic('fraud_detection_input_topic', value_type=Transakcija)
outgoing_topic = app.topic('fraud_detection_output_topic', value_type=Transakcija)

broj_transakcija_po_broju_racuna = {}   # kljuc: broj racuna kartice
                                        # vrednost: lista u kojoj su sva vremena transakcija
blokirani_racuni = []


@app.agent(input_topic)
async def orders_agent(transakcije: faust.Stream):
    global broj_transakcija_po_broju_racuna

    async for transakcija in transakcije:
        print(transakcija)
        if transakcija.broj_racuna in blokirani_racuni:
            continue

        if transakcija.broj_racuna in broj_transakcija_po_broju_racuna:
            broj_transakcija_po_broju_racuna[transakcija.broj_racuna].append(time.time())
        else:
            broj_transakcija_po_broju_racuna[transakcija.broj_racuna] = [time.time()]

        if len(broj_transakcija_po_broju_racuna[transakcija.broj_racuna]) == 5:
            blokirani_racuni.append(transakcija.broj_racuna)
            print("Blokiran racun %d" % transakcija.broj_racuna)

        await outgoing_topic.send(value=transakcija)


# Chron job koji se pokrece svake sekunde da obrise iz liste transakcija koje se pamte
# sve transakcije koje su starije od ~10 sekundi

@app.timer(interval=1.0)
async def send_aggregated_data():
    global broj_transakcija_po_broju_racuna
    for transakcije in broj_transakcija_po_broju_racuna.values(): 
        while len(transakcije) > 0 and transakcije[0] < time.time() - 10:
            transakcije.pop(0)
