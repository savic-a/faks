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


class RideTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/ride'

    def test_post_api_ride(self):
        request_data = load_json_from_file('request_bodies/post_api_ride_request_body.json')
        response_data = load_json_from_file('response_bodies/post_api_ride_response_body.json')
        response = send_post_request(data=request_data, url=self.base_path)
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_ride_driver_id_active(self):
        response_data = load_json_from_file('response_bodies/get_api_ride_driver_id_active_response_body.json')
        response = send_get_request(url=f'{self.base_path}/driver/1/active')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_ride_passenger_id_active(self):
        response_data = load_json_from_file('response_bodies/get_api_ride_passenger_id_active_response_body.json')
        response = send_get_request(url=f'{self.base_path}/passenger/1/active')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_get_api_ride(self):
        response_data = load_json_from_file('response_bodies/get_api_ride_response_body.json')
        response = send_get_request(url=f'{self.base_path}/1')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_ride_id_withdraw(self):
        response_data = load_json_from_file('response_bodies/put_api_ride_id_withdraw_response_body.json')
        response = send_put_request(data=None, url=f'{self.base_path}/1/withdraw')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_ride_id_panic(self):
        request_data = load_json_from_file('request_bodies/put_api_ride_id_panic_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_ride_id_panic_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1/panic')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)
        
    def test_put_api_ride_id_accept(self):
        response_data = load_json_from_file('response_bodies/put_api_ride_id_accept_response_body.json')
        response = send_put_request(data=None, url=f'{self.base_path}/1/withdraw')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_ride_id_end(self):
        response_data = load_json_from_file('response_bodies/put_api_ride_id_end_response_body.json')
        response = send_put_request(data=None, url=f'{self.base_path}/1/withdraw')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)

    def test_put_api_ride_id_cancel(self):
        request_data = load_json_from_file('request_bodies/put_api_ride_id_cancel_request_body.json')
        response_data = load_json_from_file('response_bodies/put_api_ride_id_cancel_response_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1/cancel')
        self.assertEqual(response.status_code, 200)
        ddiff = DeepDiff(response_data, response.json())
        self.assertEqual(ddiff.get('dictionary_item_removed', None), None)
