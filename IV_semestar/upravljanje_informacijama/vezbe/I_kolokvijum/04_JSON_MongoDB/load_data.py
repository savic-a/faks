from pymongo import MongoClient
import json

client = MongoClient('mongodb://root:root@localhost:27017')

db = client['vezbe04']
collection = db['primer01']

json_data = json.load(open('sample1.json'))

# Upisivanje liste dokumenata
# collection.insert_many(json_data)

# Citanje i ispis svih dokumenata
# 0)
# cursor = collection.find()
# for doc in cursor:
#     print(doc)
# # 1)
# cursor = collection.find({"gender": "male"})
# for doc in cursor:
#     print(doc)
# # 2)
# cursor = collection.find({"gender": "male", "from": "Subotica"})
# for doc in cursor:
#     print(doc)
# # 3)
# cursor = collection.find({"gender": "female", "age": {"$gt": 30}})
# for doc in cursor:
#     print(doc)
# 4)
# cursor = collection.find({"gender": "female", "$and": [{"age": {"$gt": 30}}, {"age": {"$lt": 50}}]})
# for doc in cursor:
#     print(doc)
# 4)
# cursor = collection.find({"gender": "female", "$and": [{"age": {"$gt": 30}}, {"age": {"$lt": 50}}]})
# for doc in cursor:
#     print(doc)



# aggregation = collection.aggregate([{"$match": {"from": {"$in": ["Subotica", "Novi Sad"]}}},
#                                     {"$group": {"_id": "$from", "broj": {"$count": {}}}}])

# for doc in aggregation:
#     print(doc)
client.close()
