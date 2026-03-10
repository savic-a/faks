import faust

app = faust.App('primerApp', broker = 'kafka://localhost:9092')
topic = app.topic('primer1Topic')

@app.agent(topic)
async def orders_agent(transakcije: faust.Stream):
    async for transakcija in transakcije:
        print(transakcija)
