import faust

class Majca(faust.Record):
    brend: str
    velicina: str

app = faust.App('hello-app', broker='kafka://localhost')
topic = app.topic('lak_zadatak', value_type=Majca)
out_topic = app.topic('lak_zadatak_out', value_type=Majca)

velicine = {}
brendovi = {}


@app.agent(topic)
async def func(majce: faust.Stream):
    async for majca in majce:
        if majca.velicina in velicine:
            velicine[majca.velicina] += 1
        else:
            velicine[majca.velicina] = 1

        if majca.brend in brendovi:
            brendovi[majca.brend] += 1
        else:
            brendovi[majca.brend] = 1

    await out_topic.send(
            value=[velicine, brendovi])





@app.timer(interval=3.0)
async def send():
    await out_topic.send(
        value=[velicine, brendovi])
