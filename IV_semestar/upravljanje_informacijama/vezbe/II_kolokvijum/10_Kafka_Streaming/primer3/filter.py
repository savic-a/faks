import faust

class Transakcija(faust.Record):
    tip_kartice: str
    kolicina: float


app = faust.App('primer3App', broker='kafka://localhost:9092')
topic = app.topic('primer3Topic', value_type=Transakcija)


suma = {}

@app.agent(topic)
async def hello(transakcije: faust.Stream):
    global suma
    async for transakcija in transakcije:
        if transakcija.tip_kartice in suma:
            suma[transakcija.tip_kartice] += transakcija.kolicina
        else:
            suma[transakcija.tip_kartice] = transakcija.kolicina
        print(suma)


@app.timer(interval=5.0)
async def example_sender():
    global suma
    await hello.send(value=suma)
    suma = {}
