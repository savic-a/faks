import json

def get_all(event, context):
    body = {
        'message': 'Hello World from the dogs lambda!',
        'data': ['dog1', 'dog2', 'dog3']
    }

    return { 'statusCode': 200, 'body': json.dumps(body) }
