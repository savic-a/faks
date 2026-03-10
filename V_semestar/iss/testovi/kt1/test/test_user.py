import unittest
import json

from deepdiff import DeepDiff
from .request_sending import send_post_request, send_get_request, send_put_request
from .server_port import PORT


def load_json_from_file(file_path):
    f = open(file_path, encoding='utf-8')
    data = json.load(f)
    f.close()
    return data


class UserTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/user'

    def test_get_api_user_id_ride(self):
        query_params = {
            'page': 1,
            'size': 10,
            'sort': 'startTime',
            'from': '2017-07-21',
            'to': '2023-07-21'
        }
        response_data = load_json_from_file('response_bodies/get_api_user_id_ride_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/ride', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_user(self):
        query_params = {
            'page': 1,
            'size': 10,
        }
        response_data = load_json_from_file('response_bodies/get_api_user_response_body.json')
        response = send_get_request(url=f'{self.base_path}', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_post_api_user_login(self):
        request_data = load_json_from_file('request_bodies/post_api_user_login_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_user_login_response_body.json')
        response = send_post_request(data=request_data, url=f'{self.base_path}/login')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_user_id_message(self):
        response_data = load_json_from_file('response_bodies/get_api_user_id_message_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/message')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_post_api_user_id_message(self):
        request_data = load_json_from_file('request_bodies/post_api_user_id_message_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_user_id_message_response_body.json')
        response = send_post_request(data=request_data, url=f'{self.base_path}/1/message')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_user_id_block(self):
        response = send_put_request(data=None, url=f'{self.base_path}/1/block')
        self.assertEqual(response.status_code, 204)

    def test_put_api_user_id_unblock(self):
        response = send_put_request(data=None, url=f'{self.base_path}/1/unblock')
        self.assertEqual(response.status_code, 204)

    def test_post_api_user_id_note(self):
        request_data = load_json_from_file('request_bodies/post_api_user_id_note_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_user_id_note_response_body.json')
        response = send_post_request(data=request_data, url=f'{self.base_path}/1/note')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_user_id_note(self):
        query_params = {
            'page': 1,
            'size': 10,
        }
        response_data = load_json_from_file('response_bodies/get_api_user_id_note_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/note', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)
