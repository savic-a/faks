def hello(event, context):
    return {
        "text": "Hello, ",
        "input": event
    }


def world(event, context):
    return {
        "text": "World!",
        "input": event
    }
