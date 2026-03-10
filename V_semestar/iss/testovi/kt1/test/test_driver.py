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


class DriverTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/driver'

    def test_post_api_driver(self):
        request_data = load_json_from_file('request_bodies/post_api_driver_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_driver_response_body.json')
        response = send_post_request(data=request_data, url=self.base_path)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver(self):
        query_params = {
            'page': 1,
            'size': 10
        }
        response_data = load_json_from_file('response_bodies/get_api_driver_response_body.json')
        response = send_get_request(url=self.base_path, query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver_id(self):
        response_data = load_json_from_file('response_bodies/get_api_driver_id_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_driver_id(self):
        request_data = load_json_from_file('request_bodies/put_api_driver_id_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_driver_id_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver_id_documents(self):
        response_data = load_json_from_file('response_bodies/get_api_driver_id_documents_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/documents')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_post_api_driver_id_documents(self):
        request_data = load_json_from_file('request_bodies/post_api_driver_id_documents_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_driver_id_documents_response_body.json')
        response = send_post_request(data=request_data, url=f'{self.base_path}/1/documents')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_delete_api_driver_document_document_id(self):
        response = send_delete_request(url=f'{self.base_path}/document/1')
        self.assertEqual(response.status_code, 204)

    def test_get_api_driver_id_vehicle(self):
        response_data = load_json_from_file('response_bodies/get_api_driver_id_vehicle_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/vehicle')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_post_api_driver_id_vehicle(self):
        request_data = load_json_from_file('request_bodies/post_api_driver_id_vehicle_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_driver_id_vehicle_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1/vehicle')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_driver_id_vehicle(self):
        request_data = load_json_from_file('request_bodies/put_api_driver_id_vehicle_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_driver_id_vehicle_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1/vehicle')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver_id_working_hour(self):
        query_params = {
            'page': 1,
            'size': 10,
            'from': '2017-07-21',
            'to': '2023-07-21'
        }
        response_data = load_json_from_file('response_bodies/get_api_driver_id_working_hour_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/working-hour', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_post_api_driver_id_working_hour(self):
        request_data = load_json_from_file('request_bodies/post_api_driver_id_working_hour_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_driver_id_working_hour_response_body.json')
        response = send_post_request(data=request_data, url=f'{self.base_path}/1/working-hour')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver_id_ride(self):
        query_params = {
            'page': 1,
            'size': 10,
            'sort': 'startTime',
            'from': '2017-07-21',
            'to': '2023-07-21'
        }
        response_data = load_json_from_file('response_bodies/get_api_driver_id_ride_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1/ride', query_params=query_params)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_driver_working_hour_id(self):
        response_data = load_json_from_file('response_bodies/get_api_driver_working_hour_id_response_body.json')
        response = send_get_request(url=f'{self.base_path}/working-hour/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_driver_id_working_hour(self):
        request_data = load_json_from_file('request_bodies/put_api_driver_id_working_hour_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_driver_id_working_hour_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/working-hour/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

