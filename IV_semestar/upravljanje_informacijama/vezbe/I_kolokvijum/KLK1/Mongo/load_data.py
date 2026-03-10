from pymongo import MongoClient
import json

client = MongoClient('mongodb://root:root@localhost:27017')

db = client['klk']
collection = db['primer01']

json_data = json.load(open('data.json'))

# Upisivanje liste dokumenata
# collection.insert_many(json_data)

# Citanje i ispis svih dokumenata
# 1)
# cursor = collection.find({"image.width": 200})
# for doc in cursor:
#     print(doc)

# # 2)
# cursor = collection.find({"type": {"$in": ["computers", "sports_equipment"]}})
# for doc in cursor:
#     print(doc)

# 3)
# cursor = collection.find({"$or": [{"price": {"$lt": 30}}, {"type": {"$not": {"$eq": "computers"}}}]})
# for doc in cursor:
#     print(doc)

# Za agregacije se koristi ova metoda:
# 5)
aggregation = collection.aggregate([{"$sort": {"price": -1}},
                                    {"$group": {"_id": "$type", "najskuplje": {"$first": "$name"},
                                                "najjeftinije": {"$last": "$name"}}}])

for doc in aggregation:
    print(doc)
client.close()
