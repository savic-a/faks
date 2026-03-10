from kafka import KafkaConsumer
from kafka import KafkaProducer
import json
import time

consumer = KafkaConsumer('queries_for_execution',
                         group_id='executor',
                         bootstrap_servers=['localhost:9092'],
                         value_deserializer=lambda x: json.loads(x.decode('utf-8')))
producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                            value_serializer=lambda msg: json.dumps(msg).encode('utf-8'))

for message in consumer:
    time.sleep(1)
    # Za ovu simulaciju nije bitno sta je tacno odgovor na upit koji se izvrsava,
    # samo da se izvrsio, te ce vrednost 'resutl' polja biti prazan string
    producer.send('executed_queries', value = {
       'entity_id': message.value['entity_id'],
       'result': ''
    })
    producer.flush()