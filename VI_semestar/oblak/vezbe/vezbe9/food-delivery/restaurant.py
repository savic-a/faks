import random as rand

def check_available(event, context):
    chance = rand.randint(0, 9)
    if chance < 3:
        raise Exception('Not available')
    if chance >= 3 and chance < 5:
        available = False
    if chance >= 5:
        available = True
    return {
        "isAvailable": available    }

