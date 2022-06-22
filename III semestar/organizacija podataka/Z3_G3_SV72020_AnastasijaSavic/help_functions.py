import os
import datetime


def all_files():
    for root, dirs, files in os.walk('data'):
        return files


def read_txt(fn):
    rows = []
    with open(fn, 'r') as f:
        for line in f.readlines():
            cols = line.split()
            rows.append({
                'id': int(cols[0]),
                'vehicle code': cols[1],
                'date and time': cols[2] + ' ' + cols[3],
                'class': cols[4],
                'passengers': int(cols[5]),
                'status': 1
            })
    return rows


def input_data():
    validate = True

    id = input('\nEvidencioni broj (9 cifara): ')
    vehicle_code = input('Registarska oznaka vozila (<16 karaktera): ')

    date_time = input('Datum i vreme (dd-mm-yyyy HH:MM): ')
    try:
        date = date_time.split(' ')[0]
        time = date_time.split(' ')[1]
        datetime.datetime.strptime(date, '%d-%m-%Y')
        datetime.datetime.strptime(time, '%H:%M')
    except ValueError or IndexError:
        validate = False

    category = input('Kategorija i klasa vozila (3 karaktera): ')
    passengers = input('Broj putnika (<=1000): ')

    while len(id) > 9 or not id.isnumeric() or len(vehicle_code) > 15 or len(category) != 3 or not passengers.isnumeric()\
            or int(passengers) < 1 or int(passengers) > 1000 or not validate:
        print('\nNeki podatak ste uneli pogrešno!')
        id = input('\nEvidencioni broj (9 cifara): ')
        vehicle_code = input('Registarska oznaka vozila (<16 karaktera): ')
        date_time = input('Datum i vreme (dd-mm-yyyy HH:MM): ')
        try:
            date = date_time.split(' ')[0]
            time = date_time.split(' ')[1]
            datetime.datetime.strptime(date, '%d-%m-%Y')
            datetime.datetime.strptime(time, '%H:%M')
            validate = True
        except ValueError:
            validate = False
        category = input('Kategorija i klasa vozila (3 karaktera): ')
        passengers = input('Broj putnika (<=1000): ')

    record = {
        'id': int(id),
        'vehicle code': vehicle_code,
        'date and time': date_time,
        'class': category,
        'passengers': int(passengers),
        'status': 0  # nov podatak
    }

    return record
