from app.constants import *
from app.record import Record
from app.sequential_file import SequentialFile
from app.serial_file import SerialFile
from app.tree_file import TreeFile
from app.overshoot_zone import OvershootZone

from help_functions import *


def task1():
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


def task2():
    i = 1
    files = all_files()
    validate_files = []  # lista potencijalnih aktivnih

    for file in files:
        if 'serial' not in file and 'sequential' not in file and 'txt' not in file\
                and 'overshoot' not in file and 'tree' not in file and 'primary' not in file:
            validate_files.append(file)

    if not validate_files:  # ako je lista prazna, ne postoji ni jedan fajl koji moze da bude aktivan
        print('Još uvek ne postoji fajl koji bi mogao da bude aktivan!')
        return

    print('\nMOGUĆI AKTIVNI FAJLOVI:')   # ispis svih mogucih aktivnih fajlova
    for file in validate_files:
        print(str(i) + ' - ' + file)
        i += 1

    index = input('\nUnesite broj koji se nalazi ispred naziva željene aktivne datoteke: ')

    # izvrsava se dok se unese validan broj
    while not index.isnumeric() or int(index) > len(validate_files) or int(index) < 1:
        print('\nGreška!')
        index = input('Unesite broj koji se nalazi ispred naziva željene aktivne datoteke: ')
    # u suprotnom, uspesan odabir aktivne datoteke
    else:
        active_file = validate_files[int(index)-1]
        print('\nUspešno ste izabrali aktivnu datoteku!')

    return active_file


# UPIS U SERIJSKU DATOTEKU
def task4():
    rec = Record(ATTRIBUTES, FMT, CODING)
    file_name = 'data/serial_file.dat'
    binary_file = SerialFile(file_name, rec, F)
    binary_file.init_file()     # inicijalizacija
    rows = read_txt('data/in.txt')
    # upis podatka u datoteku
    for i in range(0, len(rows)):
        binary_file.insert_record(rows[i])


# UPIS U SEKVENCIJALNU DATOTEKU
def task5():
    # učitavanje slogova iz serijske datoteke promena u dinamičku strukturu podataka
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

    # upis članova strukture podataka u sekvencijalnu datoteku
    rec = Record(ATTRIBUTES, FMT, CODING)
    file_name = 'data/sequential_file.dat'
    binary_file = SequentialFile(file_name, rec, F)
    binary_file.init_file()  # inicijalizacija
    for rec in record_list:
        binary_file.insert_record(rec)


# KREIRANJE INDEKS-SEKVENCIJALNE DATOTEKE
def task6():
    # KREIRANJE PRIMARNE ZONE
    rec = Record(ATTRIBUTES, FMT, CODING)
    primary_file = SequentialFile('data/primary_file.dat', rec, F)
    primary_file.init_file()     # incijalizacija primarne zone (zone podataka)

    rec_tree = Record(ATTRIBUTES_1, FMT_1, CODING)
    tree_file = TreeFile('data/tree_file.dat', rec_tree, F_TREE)
    tree_file.init_file()       # inicijalizacija zone indeksa

    records = []
    record_tree = {
        'id': 000000000,
        'address': '1p'
    }
    tree_file.insert_record(record_tree)  # najmanja moguca vrednost
    records.append(record_tree)

    i = 1
    j = 0
    with open('data/sequential_file.dat', 'rb') as file:
        binary_file = SequentialFile(file, rec, F)
        while True:
            record = binary_file.read_record(file)
            if record.get('id') == -1:
                break

            primary_file.insert_record(record)

            # KREIRANJE ZONE INDEKSA
            # svaki prvi slog iz bloka se upisuje u zonu indeksa, jer on ima najmanju
            # vrednost kljuca od svih ostalih slogova u tom bloku
            if i % 5 == 1:
                j += 1
                record_tree = {
                    'id': record.get('id'),
                    'address': str(j) + 'p'
                }
                if i != 1:
                    tree_file.insert_record(record_tree)  # dodavanje listova u datoteku
                tree_file.insert_record(record_tree)
                # za naredne slogove stabla (roditelje) dodajemo prve slogove blokova lista
                if j % 2 == 1 and j != 1:
                    records.append(record_tree)
            i += 1

    empty_record = {'id': -111, 'address': ''}

    number_records = 1
    parents = []
    while True:
        if len(parents) == 2:
            parents[0]['address'] = str(number_records) + 'i'  # menjamo adresu, tj. pokazivac na blok
            tree_file.insert_record(parents[0])  # dodajemo slog u datoteku
            tree_file.insert_record(empty_record)
            number_records += 1
            parents[1]['address'] = str(number_records) + 'i'  # menjamo adresu, tj. pokazivac na blok
            tree_file.insert_record(parents[1])  # dodajemo slog u datoteku
            break
        for record in records:
            record['address'] = str(number_records) + 'i'  # menjamo adresu, tj. pokazivac na blok
            tree_file.insert_record(record)     # dodajemo blok u datoteku
            tree_file.insert_record(empty_record)  # dodajemo prazan slog kako bismo odzali kompletan blok
            if number_records % 2 == 1:
                parents.append(record)
            number_records += 1
        if len(records) % 2 == 1:
            tree_file.insert_record(empty_record)  # dodajemo prazan slog kako bismo odzali kompletan blok
            tree_file.insert_record(empty_record)  # dodajemo prazan slog kako bismo odzali kompletan blok
        records = parents

    # KREIRANJE ZONE PREKORACENJA
    overshoot_file = OvershootZone('data/overshoot_file.dat', Record(ATTRIBUTES_Z, FMT, CODING), Fz)
    overshoot_file.write_header({'address': 0})   # prvu slobodnu lokaciju cuvamo u zaglavlju
    overshoot_file.init_file()  # inicijalizacija zone prekoracioca


# UPIS NOVOG SLOGA
def task7():
    new_record = input_data()
    found, address = task8(new_record)
    if found:    # pretraga da li vec postoji slog sa tim id-jem
        print('Slog sa tim id-jem već postoji!')
        return

    zone = address[1]
    block = []
    last_record = {}
    all_leaf = task9()

    # ako je pretraga sloga zavrsena u zoni prekoracenja
    if zone == 'z':
        rec_found = False
        i = 0
        for leaf in all_leaf:
            for rec in leaf:
                if rec.get('address') == address:
                    address = leaf[i-1].get('address')   # adresa bloka u primarnoj zoni
                    rec_found = True
                i += 1

            if rec_found:
                break
            i = 0

    block_number = int(address[0])  # redni broj bloka u primarnoj zoni

    # trazimo blok u primarnoj zoni u koji treba da dodamo slog
    primary = open('data/primary_file.dat', 'rb')
    primary_file = SequentialFile('data/primary_file.dat', Record(ATTRIBUTES, FMT, CODING), F)
    for i in range(block_number):
        block = primary_file.read_block(primary)

    # trazimo slog u bloku primarne zone
    if block[4].get('id') != -1:
        last_record = block[4]
        if last_record.get('id') > new_record.get('id'):
            primary_file.delete_by_id(last_record.get('id'))   # brisi blok koji ide u zonu prekoracenja
            primary_file.insert_record(new_record)  # dodajemo novi blok u primarnu zonu

    # dodavanje prekoracioca u list (ukoliko ima potrebe)
    if block_number % 2 == 0:  # ako je broj bloka paran
        leaf_number = block_number // 2
    else:
        leaf_number = block_number // 2 + 1

    # rad sa zonom prekoracenja
    address_leaf1 = all_leaf[leaf_number-1][1].get('address')    # adresa prvog sloga u bloku lista
    address_leaf2 = all_leaf[leaf_number-1][3].get('address')    # adresa pretposlednjeg sloga u bloku lista

    overshoot_file = OvershootZone('data/overshoot_file.dat', Record(ATTRIBUTES_Z, FMT, CODING), Fz)
    header = overshoot_file.read_header()
    free_address = header.get('address')
    # 1. ako jos uvek ne postoji ni jedan prekoracilac tog bloka, dodajemo ga i u zonu prekoracenja
    # i azuriramo list
    if int(address_leaf1[0]) == block_number and address_leaf1[1] == 'p':
        if last_record.get('id') > new_record.get('id'):
            all_leaf[leaf_number - 1][1] = {'id': last_record.get('id'), 'address': str(free_address + 1) + 'z'}
            last_record['address'] = last_record['status']
            del last_record['status']
            last_record['address'] = 0  # pokazivac na sledeci ( 0 - ne pokazuje ni na kog)
        # 2. novi ide odmah u zonu prekoracenja i prvi je prekoracilac tog bloka
        else:
            all_leaf[leaf_number - 1][1] = {'id': new_record.get('id'), 'address': str(free_address + 1) + 'z'}
            new_record['address'] = new_record['status']
            del new_record['status']
            new_record['address'] = 0
            last_record = new_record

    elif int(address_leaf2[0]) == block_number and address_leaf2[1] == 'p':
        if last_record.get('id') > new_record.get('id'):
            all_leaf[leaf_number - 1][3] = {'id': last_record.get('id'), 'address': str(free_address + 1) + 'z'}
            last_record['address'] = last_record['status']
            del last_record['status']
            last_record['address'] = 0   # pokazivac na sledeci ( 0 - ne pokazuje ni na kog)
        # 2. novi ide odmah u zonu prekoracenja i prvi je prekoracilac tog bloka
        else:
            all_leaf[leaf_number - 1][3] = {'id': new_record.get('id'), 'address': str(free_address + 1) + 'z'}
            new_record['address'] = new_record['status']
            del new_record['status']
            new_record['address'] = 0
            last_record = new_record

    # 3. ukoliko taj blok primarne vec ima prekoracioce
    elif all_leaf[leaf_number - 1][1].get('address')[1] == 'z':
        address_old = int(address_leaf1[0])  # adresa starog lista
        all_leaf[leaf_number - 1][1] = {'id': last_record.get('id'), 'address': str(free_address + 1) + 'z'}
        last_record['address'] = last_record['status']
        del last_record['status']
        last_record['address'] = address_old  # pokazivac na sledeci (pokazuje na onog sto je prethodno u listu)

    elif all_leaf[leaf_number - 1][3].get('address')[1] == 'z':
        address_old = int(address_leaf2[0])  # adresa starog lista
        all_leaf[leaf_number - 1][3] = {'id': last_record.get('id'), 'address': str(free_address + 1) + 'z'}
        last_record['address'] = last_record['status']
        del last_record['status']
        last_record['address'] = address_old  # pokazivac na sledeci (pokazuje na onog sto je prethodno u listu)

    overshoot_file.insert_record(last_record)  # upisujemo slog u zonu prekoracenja
    overshoot_file.update_header(free_address + 1)  # azuriramo zaglavlje
    overshoot_file.print_file()
    # print(overshoot_file.read_header())

    # azuriranje stabla
    tree_file = TreeFile('data/tree_file.dat', Record(ATTRIBUTES_1, FMT_1, CODING), F_TREE)
    with open('data/tree_file.dat', "rb+") as f:
        for leaf in all_leaf:
            for rec in leaf:
                f.write(tree_file.record.dict_to_encoded_values(rec))

    primary_file.print_file()


# PRETRAGA
def task8(record=None):
    if record is None:
        key = input('\nUnesite id sloga kog želite da pronađete: ')
        while not key.isdigit():
            print('\nGreška!')
            key = input('Unesite id sloga kog želite da pronađete: ')
        key = int(key)

    else:
        key = record.get('id')

    address = ''

    # pretraga adrese trazenog sloga u stablu pretrage
    with open('data/tree_file.dat', "rb+") as f:
        tree_file = TreeFile(f, Record(ATTRIBUTES_1, FMT_1, CODING), F_TREE)
        f.seek(-tree_file.block_size, 2)  # citamo poslednji blok, tj. koren (od korena krece pretraga)
        block = tree_file.read_block(f)   # koren

        # sve dok ne dodjemo do lista, tj. do sloga koji ima pokazivac na blok primarne ili zone prekoracenja
        while block[1].get('id') == -111:
            if block[0].get('id') < key < block[2].get('id'):  # idemo na desno dete
                address = int(block[0].get('address')[0])  # adresa desnog deteta
                f.seek((address - 1)*tree_file.block_size)  # posto krece od nule mora minus 1
                block = tree_file.read_block(f)
            else:
                if block[2].get('id') == -111:    # provera da li je blok skroz popunjen

                    address = int(block[0].get('address')[0])
                else:
                    address = int(block[2].get('address')[0])  # idemo na levo dete
                f.seek((address - 1)*tree_file.block_size)
                block = tree_file.read_block(f)
        else:
            i = 0
            for record in block:    # uzima se adresa poslednjeg koji je bio manji
                if key >= record.get('id'):
                    i += 1
                else:
                    address = block[i-1].get('address')
    try:
        block_number = int(address[0])
    except TypeError:
        address = block[3].get('address')
        block_number = int(address[0])

    # trazimo slog u primarnoj zoni
    if address[1] == 'p':
        with open('data/primary_file.dat', 'rb') as file:
            primary_file = SequentialFile(file, Record(ATTRIBUTES, FMT, CODING), F)
            for i in range(block_number):
                block = primary_file.read_block(file)

        # trazimo slog u bloku primarne zone
        for record in block:
            if record.get('id') == key:
                print(record)
                return True, address   # ako je slog pronadjen vracamo njegovu adresu

    # slog trazimo u zoni prekoracenja
    else:
        with open('data/overshoot_file.dat', 'rb+') as file:
            overshoot_file = OvershootZone(file, Record(ATTRIBUTES_Z, FMT, CODING), Fz)
            overshoot_address = -1
            while overshoot_address != 0:   # dok ne dodjemo do poslednjeg pokazivaca tog bloka
                file.seek(4+(block_number - 1)*overshoot_file.block_size)    # preskacemo zaglavlje
                block = overshoot_file.read_block(file)
                block_number = block[0].get('address')
                overshoot_address = block[0].get('address')
                if block[0].get('id') == key:
                    print(block[0])
                    return True, address   # ako je slog pronadjen6 vracamo adresu iz lista
            if block[0].get('id') == key:  # proveravamo i za poslednji prekoracilac tog bloka
                print(block[0])
                return True, address  # ako je slog pronadjen vracamo njegovu adresu

    return False, address   # ako slog nije pronadjen, vracamo adresu bloka u koji treba da bude dodat


# SVI LISTOVI
def task9():
    is_leaf = True
    all_leaf = []
    with open('data/tree_file.dat', 'rb') as file:
        tree_file = TreeFile(file, Record(ATTRIBUTES_1, FMT_1, CODING), F_TREE)
        while True:
            block = tree_file.read_block(file)

            for rec in block:
                if rec.get('id') == -111:   # ako postoji prazan slog onda blok nije list
                    is_leaf = False
                    break

            if is_leaf:
                all_leaf.append(block)
            else:
                break  # cim se naidje na prvi koji nije list kraj je
    return all_leaf


# PRETRAGA PO DATUMU
def task10():
    date_time1 = input('\nUnesite početni datum i vreme: ')
    date1_str = date_time1.split(' ')[0]
    time1_str = date_time1.split(' ')[1]
    date1 = datetime.datetime.strptime(date1_str, '%d-%m-%Y')
    time1 = datetime.datetime.strptime(time1_str, '%H:%M')
    date_time2 = input('Unesite krajnji datum i vreme: ')
    date2_str = date_time2.split(' ')[0]
    time2_str = date_time2.split(' ')[1]
    date2 = datetime.datetime.strptime(date2_str, '%d-%m-%Y')
    time2 = datetime.datetime.strptime(time2_str, '%H:%M')

    with open('data/primary_file.dat', 'rb') as file:
        primary_file = SequentialFile(file, Record(ATTRIBUTES, FMT, CODING), F)
        while True:
            block = primary_file.read_block(file)

            if not block:
                break

            for rec in block:
                try:
                    date_str = rec.get('date and time').split(' ')[0]
                    time_str = rec.get('date and time').split(' ')[1]
                    date = datetime.datetime.strptime(date_str, '%d-%m-%Y')
                    time = datetime.datetime.strptime(time_str, '%H:%M')
                except IndexError:
                    break
                if date1 < date < date2:
                    print(rec)
                elif date1 == date:
                    if time1 < time:
                        print(rec)
                elif date2 == date:
                    if time < time2:
                        print(rec)

    with open('data/overshoot_file.dat', 'rb') as file:
        overshoot_file = OvershootZone(file, Record(ATTRIBUTES_Z, FMT, CODING), Fz)
        file.seek(4)  # preskacemo zaglavlje
        while True:
            block = overshoot_file.read_block(file)

            if not block:
                break

            for rec in block:
                try:
                    date_str = rec.get('date and time').split(' ')[0]
                    time_str = rec.get('date and time').split(' ')[1]
                    date = datetime.datetime.strptime(date_str, '%d-%m-%Y')
                    time = datetime.datetime.strptime(time_str, '%H:%M')
                except IndexError:
                    break
                if date1 < date < date2:
                    print(rec)
                elif date1 == date:
                    if time1 < time:
                        print(rec)
                elif date2 == date:
                    if time < time2:
                        print(rec)


def menu():
    active_file = ''
    serial_file, sequential_file, IS = False, False, False

    while True:
        print('\nGRANIČNI PRELAZ MALI GRAD')
        print('Odaberite opciju:')
        print('1. Formiranje prazne datoteke')
        print('2. Izbor aktivne datoteke')
        print('3. Prikaz naziva aktivne datoteke')
        print('4. Formiranje serijske datoteke')
        print('5. Formiranje sekvencijalne datoteke')
        print('6. Formiranje indeks-sekvencijalne datoteke')
        print('7. Upis novog sloga u indeks-sekvencijalnu datoteku')
        print('8. Traženje proizvoljnog sloga')
        print('9. Prikaz listova stabla trženja')
        print('10. Pretraga slogova po zadatom opsegu datuma i vremena')
        print('0. IZLAZ\n')
        choice = input('Unesite broj željene operacije: ')

        if choice == '1':
            task1()

        elif choice == '2':
            active_file = task2()

        elif choice == '3':
            if active_file == '':
                print('Još niste izabrali aktivnu datoteku!')
            else:
                print('Naziv aktivne datoteke:', active_file)

        elif choice == '4':
            if active_file == '':
                print('Još niste izabrali aktivnu datoteku!')
            else:
                task4()
                print('Uspešno ste kreirali serijsku datoteku!')
                serial_file = True

        elif choice == '5':
            if serial_file:
                task5()
                print('Uspešno ste kreirali sekvencijalnu datoteku!')
                sequential_file = True
            else:
                print('Niste kreirali serijsku datoteku!')

        elif choice == '6':
            if serial_file and sequential_file and active_file != '':
                task6()
                print('Uspešno ste kreirali indeks-sekvencijalnu datoteku!')
                IS = True
                serial_file, sequential_file = False, False
            else:
                print('Niste kreirali serijsku datoteku i/ili niste izabrali aktivnu datoteku!')

        elif choice == '7':
            if not IS:
                print('Morate prvo da formirate indeks-sekvencijalnu datoteku!')
            else:
                task7()

        elif choice == '8':
            if not IS:
                print('Morate prvo da formirate indeks-sekvencijalnu datoteku!')
            else:
                found, _ = task8()
                if not found:
                    print('Ne postoji slog sa traženim id-jem!')

        elif choice == '9':
            if not IS:
                print('Morate prvo da formirate indeks-sekvencijalnu datoteku!')
            else:
                all_leaf = task9()
                for leaf in all_leaf:
                    print(leaf)

        elif choice == '10':
            if not IS:
                print('Morate prvo da formirate indeks-sekvencijalnu datoteku!')
            else:
                task10()

        elif choice == '0':
            break
