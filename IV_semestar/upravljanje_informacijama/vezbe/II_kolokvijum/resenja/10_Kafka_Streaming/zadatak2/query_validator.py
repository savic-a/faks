from kafka import KafkaConsumer
from kafka import KafkaProducer
import json

consumer = KafkaConsumer('generated_queries',
                         group_id='validator',
                         bootstrap_servers=['localhost:9092'],
                         value_deserializer=lambda x: json.loads(x.decode('utf-8')))
producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                            value_serializer=lambda msg: json.dumps(msg).encode('utf-8'))

for message in consumer:
    # U slucaju da upit nije validan, validator samo nista ne javlja, to jest ne salje
    # dalje nikakav odgovor. (U zavisnosti od nacina na koji implementirate resenje, kod vas mozda
    # bude neophodno da validator posalje neku poruku i kad je upit nevalidan)

    # U ovoj verziji cemo simulirati da su svi upiti validni
    producer.send('validated_queries', value = {
        'entity_id': message.value['entity_id'],
        'query_type': message.value['query_type'],
        'valid': True
    })
    producer.flush()