import termini
import menadzer
import kozmeticari
import korisnici
import tretmani
from datetime import timedelta, datetime, date

def ucitavanje_recepcionera():
    lista_recepcionera = []
    for lines in open('recepcioner.txt', encoding='utf-8').readlines():
        username, lozinka, ime, prezime, uloga, broj, email, pol = lines.strip().split('|')
        recepcioner = {
            'username': username,
            'lozinka': lozinka,
            'ime': ime,
            'prezime': prezime,
            'uloga': uloga,
            'broj': broj,
            'email': email,
            'pol': pol
        }
        lista_recepcionera.append(recepcioner)
    return lista_recepcionera

def prijavljivanje_recepcionera(username, lozinka):
    for recepcioner in ucitavanje_recepcionera():
        if username == recepcioner['username'] and lozinka == recepcioner['lozinka']:
            return username
    return False

def registracija_recepcionera(username, lozinka, tel, email, ime, prezime, pol):
    ime = ime.capitalize()
    prezime = prezime.capitalize()
    fajl = open('recepcioner.txt', 'a', encoding='utf-8')
    fajl.write(
        username + '|' + lozinka + '|' + ime + '|' + prezime + '|' + '3' + '|' + tel + '|' + email + '|' + pol + '\n')
    fajl.close()

def provera_validnosti_pri_registraciji(username, tel, email):
    lista_username = []
    for recepcioner in ucitavanje_recepcionera():
        lista_username.append(recepcioner['username'])
    if username in lista_username:
        return False
    elif not tel.isdigit():
        return False
    elif '@' not in email:
        return False
    return True

def zaglavlje():
    return ('{0:9}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:20}|{7:8}'.format(
        '\nŠifra', 'Naziv tretmana', 'Datum', 'Početak', 'Kraj', 'Klijent', 'Kozmetičar', 'Odrađen/Zakazan')) + \
           '\n' + '=' * 125

def ispis(lista):
    rezultat = ''
    for termin in lista:
        rezultat += '{0:8}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:20}|{7:8}'.format(
            termin['sifra'],
            termin['tretman'],
            termin['datum'],
            termin['pocetak'],
            termin['kraj'],
            termin['klijent'],
            termin['kozmeticar'],
            termin['odradjen']) + '\n'
    if rezultat == '':
        print('Ne postoji tretman tog dana.')
        return False
    else:
        print(zaglavlje())
        print(rezultat)
        return True

def pregled_tretmana(datum):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['datum'] == datum and termin['odradjen'] != 'brisanje':
            lista.append(termin)
    if not ispis(lista):
        return
    lista.clear()
    izbor = '0'
    while izbor.upper() != 'X':
        print('\n1 - Prethodni dan')
        print('2 - Naredni dan')
        print('3 - Unesite datum')
        print('X - Nazad')
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', '3', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            print('\n1 - Prethodni dan')
            print('2 - Naredni dan')
            print('3 - Unesite datum')
            print('X - Nazad')
            izbor = input(">> ")
        if izbor == '1':
            try:
                datum = datetime.strptime(datum, '%Y-%m-%d').date()
                datum = str(datum + timedelta(days=-1))
            except ValueError:
                print('\nNe postoji tretman tog dana.')
                return
            for termin in termini.ucitavanje_termina_tretmana():
                if termin['datum'] == datum and termin['odradjen'] != 'brisanje':
                    lista.append(termin)
            ispis(lista)
            lista.clear()
        elif izbor == '2':
            try:
                datum = datetime.strptime(datum, '%Y-%m-%d').date()
                datum = str(datum + timedelta(days=1))
            except ValueError:
                print('Ne postoji tretman tog dana.')
                return
            for termin in termini.ucitavanje_termina_tretmana():
                if termin['datum'] == datum and termin['odradjen'] != 'brisanje':
                    lista.append(termin)
            ispis(lista)
            lista.clear()
        elif izbor == '3':
            datum = input('\nUnesite novi datum za pregled (yyyy-mm-dd): ')
            for termin in termini.ucitavanje_termina_tretmana():
                if termin['datum'] == datum and termin['odradjen'] != 'brisanje':
                    lista.append(termin)
            if not ispis(lista):
                return
        lista.clear()

def pretraga_svih_zakazanih_tretmana():
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

def meni():
    print('\nIzaberite način pretrage kozmetičkog tretmana:')
    print('1 - Pretraga svih zakazanih tretmana')
    print('2 - Unos šifre tretmana')
    print('X - Nazad')

def izmena_konkretnog_tretmana():
    meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2', 'X'):
        meni()
        izbor = input(">> ")
    if izbor == '1':
        lista = pretraga_svih_zakazanih_tretmana()
        rezultat = menadzer.ispis(lista)
        if rezultat == '':
            print('Ne postoji nijedan zakazan tretman.')
        else:
            print(menadzer.zaglavlje())
            print(menadzer.ispis(lista))
            sifra = input('Unesite šifru tretmana koji želite da izmenite: ')
            if provera_validnosti_sifre(sifra):
                azuriranje_konkretnog_tretmana(sifra)
            else:
                print('Uneli ste nepostojeću šifru.')
                izmena_konkretnog_tretmana()
    elif izbor == '2':
        sifra = input('\nUnesite šifru tretmana: ')
        if provera_validnosti_sifre(sifra):
            azuriranje_konkretnog_tretmana(sifra)
        else:
            print('Uneli ste nepostojeću šifru.')
            izmena_konkretnog_tretmana()

def provera_validnosti_sifre(sifra):
    for termin in termini.ucitavanje_termina_tretmana():
        if sifra == termin['sifra']:
            return True
    return False

def citanje_iz_fajla():
    fajl = open('termini.txt', 'r', encoding='utf-8')
    text = fajl.read()
    lista = text.split('\n')
    lista = lista[:-1]
    recnik_termina = {}
    for element in lista:
        string = element.split('|')
        recnik_termina[string[0]] = string
    return recnik_termina

def pisanje_u_fajl(recnik_termina):
    text = ''
    for value in recnik_termina.values():
        line = value[0] + '|' + value[1] + '|' + value[2] + '|' + value[3] + '|' + value[4] + '|' + value[5] + '|' + \
               value[6] + '|' + value[7] + '|' + value[8] + '|' + value[9] + '|' + value[10] + '\n'
        text += line
    fajl = open('termini.txt', 'w', encoding='utf-8')
    fajl.write(text)
    fajl.close()

def azuriranje_konkretnog_tretmana(sifra):
    fajl = open('termini.txt', 'r', encoding='utf-8')
    recnik_termina = citanje_iz_fajla()
    lista_naziva = []
    for tretman in tretmani.ucitavanje_tretmana():
        lista_naziva.append(tretman['naziv'])
    provera = 0
    for value in recnik_termina.values():
        if sifra == value[0] and 'zakazan' == value[9]:
            provera = 1
            izbor_promene = input('\nIzaberite šta želite da izmenite:'
                                  '\n1 - Vrsta tretmana'
                                  '\n2 - Datum'
                                  '\n3 - Vreme izvođenja'
                                  '\nX - Nazad'
                                  '\n>> ')
            if izbor_promene == '1':
                kozmeticar = value[8]
                tip_tretmana = termini.moguci_tretmani(kozmeticar)
                lista_tretmana_dict = tretmani.pretraga_po_tipu_tretmana(tip_tretmana)
                lista_tretmana = []
                for tretman in lista_tretmana_dict:
                    lista_tretmana.append(tretman['naziv'])
                lista_tretmana.remove(value[7])
                nov_tretman = input('\nUnesite naziv tretmana: ').lower()
                trajanje1, naziv1 = tretmani.vreme_trajanja_tretmana(value[1])
                trajanje1 = int(trajanje1)
                if not tretmani.trazenje_sifre(nov_tretman):
                    print('Uneli ste nepostojeći tretman!')
                    return
                elif tretmani.trazenje_sifre(nov_tretman):
                    sifra_tretmana = tretmani.trazenje_sifre(nov_tretman)
                    trajanje2, naziv2 = tretmani.vreme_trajanja_tretmana(sifra_tretmana)
                    trajanje2 = int(trajanje2)
                    if trajanje1 != trajanje2:
                        print('Promenite i vreme jer tretmani ne traju isto! Izmenjen tretman traje', trajanje2, 'minuta.')
                    value[1] = sifra_tretmana
                    value[7] = nov_tretman
                    provera = 1
                elif nov_tretman not in lista_tretmana:
                    print('Kozmetičar ne može da obavlja unesen tretman!')
                    return
            elif izbor_promene == '2':
                value[2] = input('\nUnesite nov datum(yyyy-mm-dd): ')
                try:
                    nov_datum = datetime.strptime(value[2], '%Y-%m-%d').date()
                    pocetak = datetime.strptime(value[3], '%H:%M').time()
                    if nov_datum > date.today() or (nov_datum == date.today() and pocetak > datetime.today().time()):
                        pocetak = str(pocetak).split(':')[0] + ':' + str(pocetak).split(':')[1]
                        for line in fajl.readlines():
                            if str(nov_datum) in line and str(pocetak) in line and value[4] in line and value[8] in line:
                                print('Kozmetičar nije slobodan tada tog datuma!')
                                return
                    else:
                        print('Uneli ste nevalidan datum!')
                        return
                except ValueError:
                    print('Nevalidan unos!')
                    return
            elif izbor_promene == '3':
                value[3] = input('\nUnesite početak tretmana (sati:minuti): ')
                value[4] = input('Unesite kraj tretmana (sati:minuti): ')
                try:
                    nov_pocetak = datetime.strptime(value[3], '%H:%M').time()
                    nov_kraj = datetime.strptime(value[4], '%H:%M').time()
                    trajanje, naziv = tretmani.vreme_trajanja_tretmana(value[1])
                    trajanje = int(trajanje)
                    if str(nov_pocetak) < '10:00' or str(nov_kraj) > '16:00':
                        print('Radno vreme salona je od 10 do 17 časova!')
                        return
                    if trajanje < 60:
                        zbir = datetime.combine(date.today(), nov_pocetak) + timedelta(minutes=trajanje)
                    else:
                        zbir = datetime.combine(date.today(), nov_pocetak) + timedelta(hours=1)
                    if str(zbir.time()) != (str(nov_kraj)):
                        print('\nProverite koliko traje tretman koji menjate!')
                        return
                    nov_pocetak = str(nov_pocetak).split(':')[0] + ':' + str(nov_pocetak).split(':')[1]
                    nov_kraj = str(nov_kraj).split(':')[0] + ':' + str(nov_kraj).split(':')[1]
                    for line in fajl.readlines():
                        if value[2] in line and str(nov_pocetak) in line and str(nov_kraj) in line and value[8] in line:
                            print('Kozmetičar nije slobodan tada!')
                            return
                except ValueError:
                    print('Nevalidan unos!')
                    return
            elif izbor_promene.upper() == 'X':
                return
            else:
                print('Uneli ste pogrešnu komandu!')
                return
    if provera == 0:
        print('Uneli ste nepostojeću šifru!')
    else:
        pisanje_u_fajl(recnik_termina)
        print('\nUspešno ste izmenili tretman!')
        azuriranje_konkretnog_tretmana(sifra)
    fajl.close()

def otkazivanje_tretmana():
    meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2', 'X'):
        meni()
        izbor = input(">> ")
    if izbor == '1':
        lista = pretraga_svih_zakazanih_tretmana()
        rezultat = menadzer.ispis(lista)
        if rezultat == '':
            print('Ne postoji nijedan zakazan tretman.')
        else:
            print(menadzer.zaglavlje())
            print(menadzer.ispis(lista))
            sifra = input('Unesite šifru tretmana koji želite da izmenite: ')
            if provera_validnosti_sifre(sifra):
                brisanje(sifra)
            else:
                print('Uneli ste nepostojeću šifru.')
                otkazivanje_tretmana()
    elif izbor == '2':
        sifra = input('\nUnesite šifru tretmana: ')
        if provera_validnosti_sifre(sifra):
            brisanje(sifra)
        else:
            print('Uneli ste nepostojeću šifru.')
            otkazivanje_tretmana()

def brisanje(sifra):
    recnik_termina = citanje_iz_fajla()
    for value in recnik_termina.values():
        if sifra == value[0]:
            value[9] = 'brisanje'
    pisanje_u_fajl(recnik_termina)
    print('\nUspešno ste otkazali tretman!')

def pretraga_zakazanih_tretmana():
    izbor = '0'
    while izbor.upper() != 'X':
        menadzer.pretraga_tretmana_meni()
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', '3', '4', '5', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            menadzer.pretraga_tretmana_meni()
            izbor = input(">> ")
        if izbor == '1':
            kozmeticar = input('\nUnesite ime i prezime kozmetičara: ')
            if kozmeticari.provera_validnosti_kozmeticara(kozmeticar):
                lista = pretraga_po_kozmeticaru(kozmeticar)
                if lista:
                    print(menadzer.zaglavlje())
                    print(menadzer.ispis(lista))
                else:
                    print('Ne postoji nijedan zakazan tretman ovog kozmetičara.')
            else:
                print('Ne postoji kozmetičar sa unetim imenom i prezimenom.')
        elif izbor == '2':
            tretman = input('\nUnesite naziv tretmana: ')
            if tretmani.provera_validnosti_pri_zakazivanju(tretman):
                lista = pretraga_po_nazivu_tretmana(tretman)
                if lista:
                    print(menadzer.zaglavlje())
                    print(menadzer.ispis(lista))
                else:
                    print('Ne postoji nijedan zakazan tretman.')
            else:
                print('Ne postoji unesen tretman.')
        elif izbor == '3':
            datum = input('\nUnesite datum (yyyy-mm-dd): ')
            lista = pretraga_po_datumu(datum)
            if lista:
                print(menadzer.zaglavlje())
                print(menadzer.ispis(lista))
            else:
                print('Ne postoji nijedan zakazan tretman tog dana.')
        elif izbor == '4':
            pocetak = input('\nUnesite vreme početka tretmana (sati:minuti): ')
            kraj = input('Unesite vreme kraja tretmana (sati:minuti): ')
            lista = pretraga_po_vremenu(pocetak, kraj)
            if lista:
                print(menadzer.zaglavlje())
                print(menadzer.ispis(lista))
            else:
                print('Ne postoji nijedan zakazan tretman koji zadovoljava zadate kriterijume.')
        elif izbor == '5':
            klijent = input('\nUnesite ime i prezime klijenta: ').title()
            if korisnici.provera_validnosti_klijenta(klijent):
                lista = pretraga_po_klijentu(klijent)
                if lista:
                    print(menadzer.zaglavlje())
                    print(menadzer.ispis(lista))
                else:
                    print('Ne postoji nijedan zakazan tretman ovog klijenta.')
            else:
                print('Ne postoji klijent sa unetim imenom i prezimenom.')

def pretraga_po_kozmeticaru(kozmeticar):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['kozmeticar'] == kozmeticar.title() and termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

def pretraga_po_nazivu_tretmana(tretman):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['tretman'] == tretman.lower() and termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

def pretraga_po_datumu(datum):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['datum'] == datum and termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

def pretraga_po_vremenu(pocetak, kraj):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if pocetak == termin['pocetak'] and kraj == termin['kraj'] and termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

def pretraga_po_klijentu(klijent):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['klijent'] == klijent.title() and termin['odradjen'] == 'zakazan':
            lista.append(termin)
    return lista

