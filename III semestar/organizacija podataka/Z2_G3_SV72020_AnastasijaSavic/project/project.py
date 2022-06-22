from app.constants import *
from app.record import Record
from app.sequential_file import SequentialFile
from app.serial_file import SerialFile

import os
import datetime
import random


def read_txt(fn):
    rows = []
    with open(fn, 'r') as f:
        for line in f.readlines():
            cols = line.split()
            rows.append({
                'id': int(cols[0]),
                'prisoner code': cols[1],
                'date and time': cols[2] + ' ' + cols[3],
                'cell': cols[4],
                'punishment': int(cols[5]),
                'status': 1
            })
    return rows


def write_data_in_file(active_file):
    rows = read_txt('data/in.txt')
    binary_file = SequentialFile('data/'+active_file, Record(ATTRIBUTES, FMT, CODING), F)
    for i in range(0, len(rows)):
        binary_file.insert_record(rows[i])


def all_files():
    for root, dirs, files in os.walk('data'):
        return files


def create_empty_file():
    file_name = input('Unesite naziv datoteke: ')
    while file_name in all_files():
        print('\nUneli ste postojeći naziv datoteke!')
        file_name = input('Unesite naziv datoteke: ')
    rec = Record(ATTRIBUTES, FMT, CODING)
    file_name = 'data/' + file_name
    if '.dat' not in file_name:
        file_name = file_name + '.dat'
    binary_file = SequentialFile(file_name, rec, F)
    binary_file.init_file()


def choose_active_file():
    i = 1
    files = all_files()
    validate_files = []  # lista potencijalnih aktivnih

    for file in files:
        if 'serial' not in file and 'sequential' not in file and 'error' not in file and 'txt' not in file:
            validate_files.append(file)

    if not validate_files:
        print('Još uvek ne postoji fajl koji bi mogao da bude aktivan!')
        return

    print('\nMOGUĆI AKTIVNI FAJLOVI:')
    for file in validate_files:
        print(str(i) + ' - ' + file)
        i += 1

    index = input('\nUnesite broj koji se nalazi ispred naziva željene aktivne datoteke: ')

    while not index.isnumeric() or int(index) > len(validate_files) or int(index) < 1:
        print('\nGreška!')
        index = input('Unesite broj koji se nalazi ispred naziva željene aktivne datoteke: ')
    else:
        active_file = validate_files[int(index)-1]
        print('\nUspešno ste izabrali aktivnu datoteku!')

    write_data_in_file(active_file)

    return active_file


def serial_file_changes():
    rec = Record(ATTRIBUTES, FMT, CODING)
    file_name = 'data/serial_file.dat'
    binary_file = SerialFile(file_name, rec, F)
    binary_file.init_file()  # inicijalizacija

    while True:
        print('\nKREIRANJE SERIJSKE DATOTEKE PROMENA')
        print('Odaberite opciju:')
        print('1. Nov podatak')
        print('2. Izmena podatka')
        print('3. Brisanje podatka')
        print('4. KRAJ')
        choice = input('Unesite broj željene operacije: ')
        if choice == '1':
            id, prisoner_code, date_time, cell, punishment = input_data()
            row = {
                'id': id,
                'prisoner code': prisoner_code,
                'date and time': date_time,
                'cell': cell,
                'punishment': punishment,
                'status': 0                 # nov podatak
            }
            binary_file.insert_record(row)
        elif choice == '2':
            id, prisoner_code, date_time, cell, punishment = input_data()
            row = {
                'id': id,
                'prisoner code': prisoner_code,
                'date and time': date_time,
                'cell': cell,
                'punishment': punishment,
                'status': 1                 # izmena podatka
            }
            binary_file.insert_record(row)
        elif choice == '3':
            id = input('\nEvidencioni broj (8 cifara): ')
            while len(id) > 8 or not id.isnumeric():
                print('\nEvidencioni broj može da sadrži samo 8 cifara!')
                id = input('\nEvidencioni broj (8 cifara): ')

            row = {
                'id': int(id),
                'prisoner code': '',
                'date and time': '',
                'cell': '',
                'punishment': 0,
                'status': 2               # brisanje podatka
            }
            binary_file.insert_record(row)
        elif choice == '4':
            break
        # print('--------------------------------------------------------------------------')
        # binary_file.print_file()


def input_data():
    validate = True

    id = input('\nEvidencioni broj (8 cifara): ')
    prisoner_code = input('Šifra zatvorenika (7 karaktera): ')

    date_time = input('Datum i vreme (dd-mm-yyyy HH:MM): ')
    try:
        date = date_time.split(' ')[0]
        time = date_time.split(' ')[1]
        datetime.datetime.strptime(date, '%d-%m-%Y')
        datetime.datetime.strptime(time, '%H:%M')
    except ValueError or IndexError:
        validate = False

    cell = input('Ćelija (5 karaktera): ')
    punishment = input('Dužina kazne (<481): ')

    while len(id) > 8 or not id.isnumeric() or len(prisoner_code) != 7 or len(cell) != 5 or not punishment.isnumeric()\
            or int(punishment) < 1 or int(punishment) > 480 or not validate:
        print('\nNeki podatak ste uneli pogrešno!')
        id = input('\nEvidencioni broj (8 cifara): ')
        prisoner_code = input('Šifra zatvorenika (7 karaktera): ')
        date_time = input('Datum i vreme (dd-mm-yyyy HH:MM): ')
        try:
            date = date_time.split(' ')[0]
            time = date_time.split(' ')[1]
            datetime.datetime.strptime(date, '%d-%m-%Y')
            datetime.datetime.strptime(time, '%H:%M')
            validate = True
        except ValueError:
            validate = False
        cell = input('Ćelija (5 karaktera): ')
        punishment = input('Dužina kazne (<481): ')

    return int(id), prisoner_code, date_time, cell, int(punishment)


def sequential_file_changes():
    #  učitavanje slogova iz serijske datoteke promena u dinamičku strukturu podataka
    record_list = []
    with open('data/serial_file.dat', 'rb') as file:
        binary_file = SerialFile(file, Record(ATTRIBUTES, FMT, CODING), F)
        while True:
            block = binary_file.read_block(file)

            if not block:
                break

            for rec in block:
                if rec['id'] != -1:
                    record_list.append(rec)

    # sortiranje članova strukture podataka prema rastućoj vrednosti identifikatora
    record_list = sorted(record_list, key=lambda rec: rec['id'])

    # upis članova strukture podataka u sekvencijalnu datoteku promena
    rec = Record(ATTRIBUTES, FMT, CODING)
    file_name = 'data/sequential_file.dat'
    binary_file = SequentialFile(file_name, rec, F)
    binary_file.init_file()  # inicijalizacija
    for rec in record_list:
        binary_file.insert_record(rec, True)

    print('\nSEKVENCIJALNA DATOTEKA PROMENA')
    binary_file.print_file()


def creating_output_and_error_files(file_name):
    rec = Record(ATTRIBUTES, FMT, CODING)
    num = random.randint(0, 99)

    while True:
        try:
            os.rename('data/'+file_name, 'data/old_'+str(num)+file_name)
            break
        except FileExistsError or PermissionError:
            num = random.randint(0, 99)

    old_file = open('data/old_'+str(num)+file_name, 'rb')
    old_file_by = SequentialFile(old_file, rec, F)
    changes_file = open('data/sequential_file.dat', 'rb')
    changes_file_by = SequentialFile(changes_file, rec, F)

    new_file = SequentialFile('data/'+file_name, rec, F)
    new_file.init_file()
    error_file = SequentialFile('data/error_file.dat', rec, F)
    error_file.init_file()

    rec1 = changes_file_by.read_record(changes_file)
    rec2 = old_file_by.read_record(old_file)

    status_old_rec1 = -1

    while rec1.get('id') != -1 and rec2.get('id') != -1:

        if rec1['id'] == rec2['id']:
            while rec1['id'] == rec2['id']:
                stn = 1             # treba da se brise ili menja ili mozda hoce da doda vec postojeci slog
                processing_record(stn, rec1, rec1['status'], new_file, error_file, rec2)
                status_old_rec1 = rec1['status']
                rec1 = changes_file_by.read_record(changes_file)  # naredni slog u dp
                if status_old_rec1 == 2:   # ako je vec obrisan
                    rec2 = changes_file_by.read_record(old_file)  # naredni slog u ds

            else:
                if status_old_rec1 != 2:
                    rec2 = changes_file_by.read_record(old_file)   # naredni slog u ds

        elif rec2['id'] < rec1['id']:     # slog ne postoji u datoteci promena
            rec_new = rec2
            new_file.insert_record(rec_new)
            rec2 = changes_file_by.read_record(old_file)  # naredni slog u ds, i dalje trazimo slog iz dp

        else:
            stn = 0           # nov?
            processing_record(stn, rec1, rec1['status'], new_file, error_file, rec2)
            rec1 = changes_file_by.read_record(changes_file)  # naredni slog u dp
            while new_file.find_by_id(rec1.get('id')) is not None:
                if rec1.get('id') == -1:  # ako nema vise slogova
                    break
                stn = 1
                processing_record(stn, rec1, rec1['status'], new_file, error_file, rec2)
                rec1 = changes_file_by.read_record(changes_file)

    if rec2['id'] != -1:        # slogovi iz ds ciji je id veci od poslednjeg sloga u dp
        while rec2['id'] != -1:
            rec_new = rec2
            new_file.insert_record(rec_new)
            rec2 = changes_file_by.read_record(old_file)

    elif rec1['id'] != -1:    # slogovi iz dp ciji je id veci od sloga u ds
        while rec1['id'] != -1:
            stn = 0  # nov?
            processing_record(stn, rec1, rec1['status'], new_file, error_file, rec2)
            rec1 = changes_file_by.read_record(changes_file)  # naredni slog u dp

    print('\nDATOTEKA GREŠAKA')
    error_file.print_file()


def processing_record(stn, sp, statp, dn, dg, ss):
    if stn == 1:
        if statp == 2:  # za brisanje
            if dn.find_by_id(sp['id']):      # ako slog vec postoji u izlaznoj datoteci
                dn.delete_by_id(sp['id'])
        elif statp == 1:  # za izmenu
            dn.insert_record(sp, 'promena')
        else:
            sp['status'] = 0
            dg.insert_record(sp, True)  # pokusaj dodavanje vec postojeceg sloga
            if dn.find_by_id(sp.get('id')) is None:
                dn.insert_record(ss)  # ali taj postojeci moramo upisati u datoteku, ako on vec prethodno nije dodat
    else:
        if statp == 0:  # nov
            stn = 1
            sn = sp
            if dn.find_by_id(sn['id']):  # pokusaj da se doda vec postojeci slog
                dg.insert_record(sp, True)
            else:
                sn['status'] = stn  # status = 1, slog je popunjen
                dn.insert_record(sp, 'promena')
        else:
            sp['status'] = 1
            dg.insert_record(sp, True)  # pokusaj brisanja ili izmene nepostojeceg sloga


def print_active_file(file_name):
    binary_file = SequentialFile('data/'+file_name, Record(ATTRIBUTES, FMT, CODING), F)
    binary_file.print_file()


def menu():
    active_file = ''
    serial_file_created, sequential_file_created = False, False

    while True:
        print('\nEVIDENCIJA PRISPEĆA ZATVORENIKA')
        print('Odaberite opciju:')
        print('1. Formiranje prazne datoteke')
        print('2. Izbor aktivne datoteke')
        print('3. Prikaz naziva aktivne datoteke')
        print('4. Formiranje serijske datoteke promena')
        print('5. Formiranje sekvencijalne datoteke promena')
        print('6. Formiranje izlazne sekvencijalne datoteke i datoteke grašaka')
        print('7. Prikaz svih slogova')
        print('0. IZLAZ\n')
        choice = input('Unesite broj željene operacije: ')

        if choice == '1':
            create_empty_file()

        elif choice == '2':
            active_file = choose_active_file()

        elif choice == '3':
            if active_file == '':
                print('Još niste izabrali aktivnu datoteku!')
            else:
                print('Naziv aktivne datoteke:', active_file)

        elif choice == '4':
            if active_file == '':
                print('Još niste izabrali aktivnu datoteku!')
            else:
                serial_file_changes()
                serial_file_created = True

        elif choice == '5':
            if serial_file_created:
                sequential_file_changes()
                sequential_file_created = True
            else:
                print('Niste kreirali serijsku datoteku promena!')

        elif choice == '6':
            if serial_file_created and sequential_file_created and active_file != '':
                creating_output_and_error_files(active_file)
                print('\nUspešno ste formirali izlaznu datoteku i datoteku grešaka!')
                serial_file_created, sequential_file_created = False, False
            else:
                print('Niste kreirali serijsku datoteku grešaka i/ili niste izabrali aktivnu datoteku!')

        elif choice == '7':
            if active_file != '':
                print_active_file(active_file)
            else:
                print('Još niste izabrali aktivnu datoteku!')

        elif choice == '0':
            break
