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


class PassengerTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/passenger'

    def test_post_api_passenger(self):
        request_data = load_json_from_file('request_bodies/post_api_passenger_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_passenger_response_body.json')
        response = send_post_request(data=request_data, url=self.base_path)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_passenger(self):
        query_params = {
            'page': 1,
            'size': 10
        }
        response_data = load_json_from_file('response_bodies/get_api_passenger_response_body.json')
        response = send_get_request(url=self.base_path, query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_passenger_activate_activationId(self):
        response = send_get_request(url=f'{self.base_path}/activate/1')
        self.assertEqual(response.status_code, 200)

    def test_get_api_passenger_id(self):
        response_data = load_json_from_file('response_bodies/get_api_passenger_id_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_passenger_id(self):
        request_data = load_json_from_file('request_bodies/put_api_passenger_id_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_passenger_id_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_passenger_id_ride(self):
        query_params = {
            'page': 1,
            'size': 10,
            'sort': 'firstName',
            'from': '2017-07-21',
            'to': '2023-07-21'
        }
        response_data = load_json_from_file('response_bodies/get_api_passenger_id_ride_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/ride', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)
