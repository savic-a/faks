from kafka import KafkaProducer
import json
import time

producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                            value_serializer=lambda msg: json.dumps(msg).encode('utf-8'))

producer.send('generated_queries', value = {
    'entity_id': 3,
    'query_type': "SELECT"
})
producer.flush()
# Treba da prodje jer je moguce da imamo vise paralelnih "SELET"
# upita, posto takvi upiti nista ne menjaju te ne mogu jedan drugom smetati
producer.send('generated_queries', value = {
    'entity_id': 3,
    'query_type': "SELECT"
})
producer.flush()
# Ne treba da prodje, jer cemo simulirati u ostalim servisima da jos traje
# jednan od prethodna 2 upita (a ne zelimo da ovaj upit odbrise neke podatke koji
# se trenutno citaju u nekoj drugoj transakciji)
producer.send('generated_queries', value = {
    'entity_id': 3,
    'query_type': "DELETE"
})
producer.flush()

time.sleep(5)
# Ovaj upit treba da prodje jer su se u simulaciji izvrsila
# oba upita nad ovim entitetom
producer.send('generated_queries', value = {
    'entity_id': 3,
    'query_type': "DELETE"
})
# Ovaj upit ne treba da prodje
producer.send('generated_queries', value = {
    'entity_id': 3,
    'query_type': "SELECT"
})
producer.flush()