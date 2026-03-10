import unittest
import json

from deepdiff import DeepDiff
from .request_sending import send_post_request, send_get_request, send_put_request, send_delete_request
from .server_port import PORT


def load_json_from_file(file_path):
    f = open(file_path, encoding='utf-8')
    data = json.load(f)
    f.close()
    return data


class PanicTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/panic'

    def test_get_api_panic(self):
        response_data = load_json_from_file('response_bodies/get_api_panic_response_body.json')
        response = send_get_request(url=f'{self.base_path}')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)
