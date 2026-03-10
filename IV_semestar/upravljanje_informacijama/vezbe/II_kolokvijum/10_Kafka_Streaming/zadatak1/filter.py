import faust


class Transakcija(faust.Record):
    broj_kartice: int
    kolicina: int

app = faust.App('fraudDetectionApp', broker='kafka://localhost')
topic = app.topic('fraudDetectionTopic', value_type=Transakcija)


@app.agent(topic)
async def hello(transakcije: faust.Stream):
    async for transakcija in transakcije:
        print(f'Hello from {greeting.from_name} to {greeting.to_name}')


output_topic = app.topic('fraudDetectionTOutputTopic', value_type=Transakcija)


@app.timer(interval=1.0)
async def example_sender():
    await hello.send(value=Greeting(from_name='Faust', to_name='you'))