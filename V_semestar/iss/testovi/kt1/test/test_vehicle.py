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


class VehicleTest(unittest.TestCase):
    def setUp(self):
        self.base_path = f'http://localhost:{PORT}/api/vehicle'

    def test_put_api_vehicle_id_location(self):
        request_data = load_json_from_file('request_bodies/put_api_vehicle_id_location_request_body.json')
        response = send_put_request(data=request_data, url=f'{self.base_path}/1/location')
        self.assertEqual(response.status_code, 204)
