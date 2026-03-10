import faust

class Majca(faust.Record):
    brend: str
    velicina: str

app = faust.App('hello-app', broker='kafka://localhost')

topic = app.topic('orders_for_fulfillment2', value_type=Majca)
dobra_porudzbina = app.topic('dobra-topic', value_type=Majca)
losa_porudzbina = app.topic('wrong_orders', value_type=Majca)


@app.agent(topic)
async def hello(majce: faust.Stream):
    async for majca in majce:
        if majca.velicina == "XXL":
            await losa_porudzbina.send(value={majca.brend: majca.velicina})
        else:
            await dobra_porudzbina.send(value={majca.brend: majca.velicina})


