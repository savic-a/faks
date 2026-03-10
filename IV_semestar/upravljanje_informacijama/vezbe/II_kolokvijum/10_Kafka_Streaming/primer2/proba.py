import faust

class Transakcija(faust.Record):
    tip_kartice: str
    kolicina: int


app = faust.App('primer2App', broker='kafka://localhost:9092')
topic = app.topic('primer2Topic', value_type=Transakcija)

ukupna_suma_kartica = {}


@app.agent(topic)
async def hello(transakcije: faust.Stream):
    async for transakcija in transakcije:
        if transakcija.tip_kartice in ukupna_suma_kartica:
            ukupna_suma_kartica[transakcija.tip_kartice] += transakcija.kolicina
        else:
            ukupna_suma_kartica[transakcija.tip_kartice] = transakcija.kolicina
        print(ukupna_suma_kartica)


# @app.timer(interval=1.0)
# async def example_sender(app):
#     await hello.send(
#         value=Transakcija(from_name='Faust', to_name='you'),
#     )

if __name__ == '__main__':
    app.main()
