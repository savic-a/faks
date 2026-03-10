import time
from kafka import KafkaProducer
import json

producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                            value_serializer=lambda msg: json.dumps(msg).encode('utf-8'))

for _ in range(4):
    producer.send('fraud_detection_input_topic', value = {
        'broj_racuna': 1111,
        'kolicina': 1
    })
producer.flush()
time.sleep(8)
# Ova poslednja poruka treba da prodje, ali svaka sledeca
# sa ovim brojem racuna treba da bude blokirana
producer.send('fraud_detection_input_topic', value = {
        'broj_racuna': 1111,
        'kolicina': 1
    })
for _ in range(4):
    producer.send('fraud_detection_input_topic', value = {
        'broj_racuna': 2222,
        'kolicina': 1
    })
producer.flush()
time.sleep(3)
# Ova transakcija ne treba da dodje do consumera
producer.send('fraud_detection_input_topic', value = {
        'broj_racuna': 1111,
        'kolicina': 1
    })
time.sleep(8)
# Od ove 2 transakcije, obe treba da prodju (proslo je 
# vise od 10s od prethodne 4 transakcije koje su poslate sa racuna 2222)
for _ in range(2):
    producer.send('fraud_detection_input_topic', value = {
        'broj_racuna': 2222,
        'kolicina': 2
    })
producer.flush()
