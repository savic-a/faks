import termini
import kozmeticari
import tretmani
import korisnici
import recepcioneri
import main
from datetime import date, datetime, timedelta

def ucitavanje_menadzera():
    lista_menadzera = []
    for lines in open('menadzeri.txt', encoding='utf-8').readlines():
        username, lozinka, ime, prezime, uloga, broj, email, pol = lines.strip().split('|')
        menadzer = {
            'username': username,
            'lozinka': lozinka,
            'ime': ime,
            'prezime': prezime,
            'uloga': uloga,
            'broj': broj,
            'email': email,
            'pol': pol
        }
        lista_menadzera.append(menadzer)
    return lista_menadzera

def prijavljivanje_menadzera(username, lozinka):
    for menadzer in ucitavanje_menadzera():
        if username == menadzer['username'] and lozinka == menadzer['lozinka']:
            return username
    return False

def zaglavlje():
    return ('{0:9}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:15}'.format(
        '\nŠifra', 'Naziv tretmana', 'Datum', 'Početak', 'Kraj', 'Klijent', 'Kozmetičar')) + '\n' + '=' * 105

def pretraga_tretmana_meni():
    print('\nIzaberite način pretrage: ')
    print('1 - Po imenu i prezimenu kozmetičara')
    print('2 - Po nazivu tretmana')
    print('3 - Po datumu')
    print('4 - Po vremenu izvođenja')
    print('5 - Po imenu i prezimenu klijenta')
    print('X - Nazad')

def pretraga_realizovanih_tretmana():
    izbor = '0'
    while izbor.upper() != 'X':
        pretraga_tretmana_meni()
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', '3', '4', '5', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            pretraga_tretmana_meni()
            izbor = input(">> ")
        if izbor == '1':
            kozmeticar = input('\nUnesite ime i prezime kozmetičara: ')
            if kozmeticari.provera_validnosti_kozmeticara(kozmeticar):
                lista = pretraga_po_kozmeticaru(kozmeticar)
                if lista:
                    print(zaglavlje())
                    print(ispis(lista))
                else:
                    print('Ne postoji nijedan realizovan tretman ovog kozmetičara.')
            else:
                print('Ne postoji kozmetičar sa unetim imenom i prezimenom.')
        elif izbor == '2':
            tretman = input('\nUnesite naziv tretmana: ')
            if tretmani.provera_validnosti_pri_zakazivanju(tretman):
                lista = pretraga_po_nazivu_tretmana(tretman)
                if lista:
                    print(zaglavlje())
                    print(ispis(lista))
                else:
                    print('Ne postoji nijedan realizovan tretman.')
            else:
                print('Ne postoji unesen tretman.')
        elif izbor == '3':
            datum = input('\nUnesite datum(yyyy-mm-dd): ')
            lista = pretraga_po_datumu(datum)
            if lista:
                print(zaglavlje())
                print(ispis(lista))
            else:
                print('Ne postoji nijedan realizovan tretman tog dana.')
        elif izbor == '4':
            pocetak = input('\nUnesite vreme početka tretmana (sati:minuti): ')
            kraj = input('Unesite vreme kraja tretmana (sati:minuti): ')
            lista = pretraga_po_vremenu(pocetak, kraj)
            if lista:
                print(zaglavlje())
                print(ispis(lista))
            else:
                print('Ne postoji nijedan realizovan tretman koji zadovoljava zadate kriterijume.')
        elif izbor == '5':
            klijent = input('\nUnesite ime i prezime klijenta: ')
            if korisnici.provera_validnosti_klijenta(klijent):
                lista = pretraga_po_klijentu(klijent)
                if lista:
                    print(zaglavlje())
                    print(ispis(lista))
                else:
                    print('Ne postoji nijedan realizovan tretman ovog klijenta.')
            else:
                print('Ne postoji klijent sa unetim imenom i prezimenom.')

def ispis(lista):
    rezultat = ''
    for termin in lista:
        rezultat += '{0:8}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:15}'.format(
            termin['sifra'],
            termin['tretman'],
            termin['datum'],
            termin['pocetak'],
            termin['kraj'],
            termin['klijent'],
            termin['kozmeticar']) + '\n'
    return rezultat

def pretraga_po_kozmeticaru(kozmeticar):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['kozmeticar'] == kozmeticar.title() and termin['odradjen'] == 'odrađen':
            lista.append(termin)
    return lista

def pretraga_po_nazivu_tretmana(tretman):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['tretman'] == tretman.lower() and termin['odradjen'] == 'odrađen':
            lista.append(termin)
    return lista

def pretraga_po_datumu(datum):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['datum'] == datum and termin['odradjen'] == 'odrađen':
            lista.append(termin)
    return lista

def pretraga_po_vremenu(pocetak, kraj):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if pocetak == termin['pocetak'] and kraj == termin['kraj'] and termin['odradjen'] == 'odrađen':
            lista.append(termin)
    return lista

def pretraga_po_klijentu(klijent):
    lista = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['klijent'] == klijent.title() and termin['odradjen'] == 'odrađen':
            lista.append(termin)
    return lista

def registracija_kozmeticara_recepcionera_meni():
    print('\nDa li registrujete novog:')
    print('1 - Kozmetičara')
    print('2 - Recepcionera')
    print('X - Nazad')

def registracija_kozmeticara_recepcionera():
    izbor = '0'
    while izbor.upper() != 'X':
        registracija_kozmeticara_recepcionera_meni()
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            registracija_kozmeticara_recepcionera_meni()
            izbor = input(">> ")
        if izbor == '1':
            username, lozinka, tel, email, ime, prezime, pol = korisnici.registracija_meni()
            tretman = input('Unesite tip tetmana kozmetičara (masaza, pedikir, manikir, tretman lica): ').lower()
            if kozmeticari.provera_validnosti_pri_registraciji(username, tel, email, tretman):
                print('\nUspešno ste registrovali kozmetičara!')
                kozmeticari.registracija_kozmeticara(username, lozinka, tel, email, ime, prezime, pol, tretman)
            else:
                print('\nNešto niste dobro uneli!')
        elif izbor == '2':
            username, lozinka, tel, email, ime, prezime, pol = korisnici.registracija_meni()
            if recepcioneri.provera_validnosti_pri_registraciji(username, tel, email):
                print('\nUspešno ste registrovali recepcionera!')
                recepcioneri.registracija_recepcionera(username, lozinka, tel, email, ime, prezime, pol)
            else:
                print('\nNešto niste dobro uneli!')

def kreiranje_novog_tretmana_meni():
    sifra = input('\nUnesite sifru tretmana: ').upper()
    naziv = input('Unesite naziv tretmana: ').lower()
    opis = input('Unesite kratak opis tretmana: ')
    trajanje = input('Unesite trajanje tretmana u minutima: ')
    priprema = input('Unesite kratak opis pripreme za tretman: ')
    cena = input('Unesite cenu tretmana: ')
    return sifra.upper(), naziv, opis, trajanje, priprema, cena

def provera_ispravnosti_podataka_pri_kreiranju_tretmana(sifra, naziv, opis, trajanje, cena):
    lista_sifri = []
    lista_naziva = []
    for tretman in tretmani.ucitavanje_tretmana():
        lista_sifri.append(tretman['sifra'])
        lista_naziva.append(tretman['naziv'])
    if sifra in lista_sifri or len(sifra) != 5:
        return False
    elif not sifra[0].isalpha() or not sifra[1].isalpha() or not sifra[2].isalpha() or not sifra[3].isdigit() \
            or not sifra[4].isdigit():
        return False
    elif naziv in lista_naziva:
        return False
    elif len(opis) > 300:
        return False
    elif not cena.isdigit() or not trajanje.isdigit():
        return False
    elif int(trajanje) > 60:
        return False
    return True

def upis_novog_tretmana_u_fajl(sifra, naziv, opis, trajanje, priprema, cena):
    fajl = open('tretmani.txt', 'a', encoding='utf-8')
    fajl.write(
        sifra + '|' + naziv + '|' + opis + '|' + trajanje + '|' + priprema + '|' + cena + '\n')
    fajl.close()

def kreiranje_novog_tretmana():
    sifra, naziv, opis, trajanje, priprema, cena = kreiranje_novog_tretmana_meni()
    if provera_ispravnosti_podataka_pri_kreiranju_tretmana(sifra, naziv, opis, trajanje, cena):
        upis_novog_tretmana_u_fajl(sifra, naziv, opis, trajanje, priprema, cena)
        print('\nUspešno ste kreirali novi tretman!')
    else:
        print('\nNešto niste dobro uneli.')

def izmena_kozmetickog_tretmana():
    main.zakazivanje_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2', 'X'):
        print("\nUneli ste pogrešnu komandu.\n")
        main.zakazivanje_meni()
        izbor = input(">> ")
    if izbor == '1':
        rezultat = main.visekriterijumska_pretraga()
        if rezultat == 0:
            izmena_kozmetickog_tretmana()
        else:
            sifra = input('Unesite šifru tretmana: ')
            sifra = sifra.upper()
            if sifra in rezultat:
                azuriranje_tretmana(sifra)
            else:
                print('Uneli ste neadekvatnu šifru.')
                izmena_kozmetickog_tretmana()
    elif izbor == '2':
        sifra = input('\nUnesite šifru tretmana: ')
        sifra = sifra.upper()
        if tretmani.provera_validnosti_sifre(sifra):
            azuriranje_tretmana(sifra)
        else:
            print('Uneli ste neadekvatnu šifru.')
            izmena_kozmetickog_tretmana()

def azuriranje_tretmana(sifra):
    fajl = open('tretmani.txt', 'r', encoding='utf-8')
    text = fajl.read()
    lista = text.split('\n')
    lista = lista[:-1]
    recnik_tretmana = {}
    for element in lista:
        string = element.split("|")
        recnik_tretmana[string[0]] = string
    text = ''
    lista_naziva = []
    for tretman in tretmani.ucitavanje_tretmana():
        lista_naziva.append(tretman['naziv'])
    for value in recnik_tretmana.values():
        if sifra == value[0]:
            izbor_promene = input('\nIzaberite šta želite da izmenite:'
                                  '\n1 - Naziv tretmana'
                                  '\n2 - Opis'
                                  '\n3 - Trajanje'
                                  '\n4 - Opis preipreme'
                                  '\n5 - Cenu'
                                  '\n>> ')
            if izbor_promene == '1':
                value[1] = input('\nUnesite nov naziv tretmana: ')
                if value[1] in lista_naziva:
                    print('Već postoji tretman sa tim nazivom!')
                    return
            elif izbor_promene == '2':
                value[2] = input('\nUnesite kratak opis tretmana: ')
                if len(value[2]) > 300:
                    print('Uneli ste previše dug opis!')
                    return
            elif izbor_promene == '3':
                value[3] = input('\nUnesite trajanje u minutima: ')
                if not value[3].isdigit():
                    print('Nevalidan unos!')
                    return
                elif int(value[3]) > 60:
                    print('Tretman može maksimalo da traje 60 minuta.')
                    return
            elif izbor_promene == '4':
                value[4] = input('\nUnesite opis preipreme: ')
            elif izbor_promene == '5':
                value[5] = input('Unesite cenu tretmana: ')
                if not value[5].isdigit():
                    print('Nevalidan unos!')
                    return
    for value in recnik_tretmana.values():
        line = value[0].upper() + '|' + value[1] + '|' + value[2] + '|' + value[3] + '|' + value[4] + '|' + value[5] + '\n'
        text += line
    fajl = open('tretmani.txt', 'w', encoding='utf-8')
    fajl.write(text)
    fajl.close()
    print('\nUspešno ste izmenili kozmetički tretman!')

def brisanje():
    lista = []
    lista_sifri = []
    for termin in termini.ucitavanje_termina_tretmana():
        if termin['odradjen'] == 'brisanje':
            lista.append(termin)
            lista_sifri.append(termin['sifra'])
    print(zaglavlje())
    print(ispis(lista))
    izbor = '0'
    while izbor.upper() != 'X':
        print('\nUnesite željenu opciju:')
        print('1 - Obrišite sve prikazane tretmane')
        print('2 - Odaberite 1 ili više tretmana za brisanje')
        print('X - Nazad')
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', '3', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            print('\nUnesite željenu opciju:')
            print('1 - Obrišite sve prikazane tretmane')
            print('2 - Odaberite 1 ili više tretmana za brisanje')
            print('X - Nazad')
            izbor = input(">> ")
        if izbor == '1':
            brisanje1()
            print('\nUspešno ste obrisali željene termine!')
        elif izbor == '2':
            sifre = input('\nUnesite šifru ili šifre tretmana koje želite da obrišete (šifre odvojite speceom): ')
            if not brisanje2(sifre, lista_sifri):
                print('Uneli ste pogrešnu šifru!')
            else:
                print('Uspešno ste obrisali željene termine!')

def brisanje1():
    with open('termini.txt', 'r', encoding='utf-8') as fajl:
        lines = fajl.readlines()
    with open('termini.txt', 'w', encoding='utf-8') as fajl:
        for line in lines:
            if 'brisanje' not in line.strip('\n'):
                fajl.write(line)

def brisanje2(sifre, lista):
    lista_sifri = []
    sifre = sifre.split(' ')
    lista_sifri.extend(sifre)
    provera1 = all(item in lista for item in lista_sifri)
    if provera1 is True:
        with open('termini.txt', 'r', encoding='utf-8') as fajl:
            lines = fajl.readlines()
        with open('termini.txt', 'w', encoding='utf-8') as fajl:
            for line in lines:
                provera2 = any(item in line.strip('\n') for item in lista_sifri)
                if provera2 is True:
                    continue
                else:
                    fajl.write(line)
        return True
    else:
        return False

def izvestavanje():
    print('\nIzaberite izveštaj:')
    print('1 - Lista realizovanih kozmetičkih tretmana za izabran datum')
    print('2 - Lista realizovanih kozmetičkih tretmana za izabranog kozmetičara')
    print('3 - Lista klijenata koji se kvalifikuju za karticu lojalnosti')
    print('4 - Godišnji pregled angožovanja za određenog kozmetičara')
    print('5 - Mesečni pregled angožovanja svih kozmetičara')
    print('6 - Pregled zastupljenosti pojedinačnih tretmana u odnosu na ukupan broj tretmana')
    print('7 - Ukupan broj i cena realizovanih tretmana za izabrani datum i izabranog recepcionera')
    izbor = input('\nUnesite redni broj izveštaja koji želite: ')
    izbor_izvestaja(izbor)

def izbor_izvestaja(izbor):
    if izbor == '1':
        datum = input('\nUnesite datum(yyyy-mm-dd) za koji želite da vidite listu tretmana: ')
        lista = izvestaj1(0, datum)
        if not lista:
            print('Za uneti datum ne postoji nijedan realizovan tretman.')
            izvestavanje()
        else:
            print(zaglavlje())
            print(ispis(lista))
            komanda = input('Da li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
            if komanda == 'da':
                cuvanje1(0, lista)
                print('Uspešno ste sačuvali Vaš izveštaj!')
    elif izbor == '2':
        kozmeticar = input('\nUnesite ime i prezime kozmetičara za kog želite da vidite listu tretmana: ').title()
        lista = izvestaj1(1, kozmeticar)
        if not lista:
            print('Za unetog kozmetičara ne postoji nijedan realizovan tretman.')
            izvestavanje()
        else:
            print(zaglavlje())
            print(ispis(lista))
            komanda = input('Da li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
            if komanda == 'da':
                cuvanje1(1, lista)
                print('Uspešno ste sačuvali Vaš izveštaj!')
    elif izbor == '3':
        rezultat, lista = izvestaj2()
        print()
        naslov = 'LISTA LOJALNIH KLIJENATA'
        print(naslov.center(30))
        print('=' * 30)
        print(rezultat)
        komanda = input('Da li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
        if komanda == 'da':
            cuvanje2(rezultat)
            print('Uspešno ste sačuvali Vaš izveštaj!')
    elif izbor == '4':
        broj, total = izvestaj3()
        komanda = input('\nDa li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
        if komanda == 'da':
            cuvanje3(0, broj, total)
    elif izbor == '5':
        lista_kozmeticara, lista_broja_tretmana, lista_total = izvestaj4()
        komanda = input('Da li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
        if komanda == 'da':
            cuvanje4(lista_kozmeticara, lista_broja_tretmana, lista_total)
    elif izbor == '6':
        lista_tretmana, lista_broja_tretmana, ukupno_tretmana, lista_procenti = izvestaj5()
        komanda = input('Da li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
        if komanda == 'da':
            cuvanje5(lista_tretmana, lista_broja_tretmana, ukupno_tretmana, lista_procenti)
    elif izbor == '7':
        broj, total = izvestaj6()
        komanda = input('\nDa li želite da sačuvate izveštaj? (unesite "da" ako želite): ').lower()
        if komanda == 'da':
            cuvanje3(1, broj, total)
    else:
        print('\nVerovatno nešto niste dobro uneli, proverite. Na raspolaganju imate samo izveštaje od 1 do 7!')
        izvestavanje()

def izvestaj1(brojac, izbor):
    lista = []
    if brojac == 0:
        for termin in termini.ucitavanje_termina_tretmana():
            if termin['datum'] == izbor and termin['odradjen'] == 'odrađen':
                lista.append(termin)
    else:
        for termin in termini.ucitavanje_termina_tretmana():
            if termin['kozmeticar'] == izbor and termin['odradjen'] == 'odrađen':
                lista.append(termin)
    return lista

def izvestaj2():
    total = 0
    lista_lojalnih = []
    for klijent in korisnici.ucitavanje_korisnika():
        username = klijent['username']
        lista_tretmana = termini.kreiranje_liste_tretmana_u_poslednjih_godinu_dana(username)
        lista_cena = termini.kreiranje_liste_cena(lista_tretmana)
        for cena in range(len(lista_cena)):
            total += int(lista_cena[cena])
        if total >= 30000:
            lista_lojalnih.append(username)
        total = 0
    rezultat = ''
    for klijent in korisnici.ucitavanje_korisnika():
        if klijent['username'] in lista_lojalnih:
            rezultat += klijent['ime'] + ' ' + klijent['prezime'] + '\n'
    return rezultat, lista_lojalnih

def izvestaj3():
    total = 0
    kozmeticar = input('\nUnesite ime i prezime kozmetičara: ').title()
    username = ''
    try:
        ime = kozmeticar.split()[0]
        prezime = kozmeticar.split()[1]
    except IndexError:
        print('\nTreba da unesete ime i prezime kozmetičara!')
        return False, False
    for kozm in kozmeticari.ucitavanje_kozmeticara():
        if ime == kozm['ime'] and prezime == kozm['prezime']:
            username = kozm['username']
    if username == '':
        print('\nUneli ste nepostojećeg kozmetičara!')
        return False, False
    klijenti_username = korisnici.kreiranje_liste_username()
    danas = date.today()
    prosla_god = danas.replace(year=danas.year-1)
    lista_tretmana = kreiranje_liste_tretmana(klijenti_username, kozmeticar, prosla_god)
    if not lista_tretmana:
        print('\nKozmetičar nema nijedan izveden tretman!')
        return False, False
    lojalni, lista_lojalnih = izvestaj2()
    lista_cena = kreiranje_liste_cene(lista_lojalnih, lista_tretmana)
    br_tretmana = len(lista_cena)
    for cena in range(len(lista_cena)):
        total += int(lista_cena[cena])
    print(('{0:15}|{1:15}'.format('\nBr tretmana', 'Zarada[din]')) + '\n' + '=' * 30)
    print(('{0:7}{1:16}'.format(br_tretmana, total)))
    return br_tretmana, total

def izvestaj4():
    print(('{0:20}|{1:12}|{2:10}'.format('\nKozmetičar', 'Br tretmana', 'Zarada[din]')) + '\n' + '=' * 50)
    lojalni, lista_lojalnih = izvestaj2()
    klijenti_username = korisnici.kreiranje_liste_username()
    danas = date.today()
    mesec_pre = danas + timedelta(days=-30)
    lista_kozmeticara = []
    lista_broja_tretmana = []
    lista_total = []
    total = 0
    for kozmeticar in kozmeticari.ucitavanje_kozmeticara():
        ime_prezime = kozmeticar['ime'] + ' ' + kozmeticar['prezime']
        lista_kozmeticara.append(ime_prezime)
        lista_tretmana = kreiranje_liste_tretmana(klijenti_username, ime_prezime, mesec_pre)
        lista_cena = kreiranje_liste_cene(lista_lojalnih, lista_tretmana)
        lista_broja_tretmana.append(len(lista_cena))
        for cena in range(len(lista_cena)):
            total += int(lista_cena[cena])
        lista_total.append(total)
        total = 0
        lista_tretmana.clear()
        lista_cena.clear()
    rezultat = ''
    for i in range(len(lista_total)):
        rezultat += ('{0:19}|{1:12}|{2:15}'.format(lista_kozmeticara[i], str(lista_broja_tretmana[i]), str(lista_total[i])))+'\n'
    print(rezultat)
    return lista_kozmeticara, lista_broja_tretmana, lista_total

def izvestaj5():
    ukupno_tretmana = len(open('termini.txt', 'r', encoding='utf-8').readlines())
    lista_tretmana = []
    lista_broja_tretmana = []
    lista_procenti = []
    for tretman in tretmani.ucitavanje_tretmana():
        lista_tretmana.append(tretman['naziv'])
        with open('termini.txt', 'r', encoding='utf-8') as fajl:
            broj = 0
            for line in fajl:
                broj += line.count(tretman['naziv'])
                procenat = broj / ukupno_tretmana * 100
            lista_broja_tretmana.append(broj)
            lista_procenti.append(str(round(procenat, 2)))
    rezultat = ''
    for i in range(len(lista_tretmana)):
        rezultat += ('{0:24}|{1:15}|{2:15}'.format(
            lista_tretmana[i], str(lista_broja_tretmana[i]) + '/' + str(ukupno_tretmana), lista_procenti[i] + '%'))+'\n'
    print(('{0:25}|{1:15}|{2:10}'.format('\nTretman', 'Br tretmana', 'Zastupljenost')) + '\n' + '=' * 55)
    print(rezultat)
    return lista_tretmana, lista_broja_tretmana, ukupno_tretmana, lista_procenti

def izvestaj6():
    zeljeni_recep = input('\nUnesite ime i prezime željenog recepcionera: ').title()
    zeljeni_datum = input('Unesite željeni datum(yyyy-mm-dd): ')
    try:
        ime_recep = zeljeni_recep.split()[0]
        prezime_recep = zeljeni_recep.split()[1]
    except IndexError:
        print('\nTreba da unesete ime i prezime recepcionera!')
        return False, False
    try:
        zeljeni_datum = datetime.strptime(zeljeni_datum, '%Y-%m-%d').date()
    except ValueError:
        print('\nNevalidan unos datuma!')
        return False, False
    if zeljeni_datum > date.today():
        print('\nTreba da unesete neki od datuma koji je već bio!')
    username = ''
    for recepcioner in recepcioneri.ucitavanje_recepcionera():
        if ime_recep == recepcioner['ime'] and prezime_recep == recepcioner['prezime']:
            username = recepcioner['username']
    klijenti_username = korisnici.kreiranje_liste_username()
    lista_tretmana = []
    for termin in termini.ucitavanje_termina_tretmana():
        if username == termin['username'] and str(zeljeni_datum) == termin['datum'] and termin['odradjen'] == 'odrađen':
            if termin['recepcioner'] in klijenti_username:
                lista_tretmana.append((termin['tretman'], termin['recepcioner']))
            else:
                lista_tretmana.append((termin['tretman'], ''))
    if not lista_tretmana:
        print('\nRecepcioner nije zakazao nijedan tretman tog dana!')
        return False, False
    lojalni, lista_lojalnih = izvestaj2()
    lista_cena = kreiranje_liste_cene(lista_lojalnih, lista_tretmana)
    total = 0
    br_tretmana = len(lista_cena)
    for cena in range(len(lista_cena)):
        total += int(lista_cena[cena])
    print(('{0:18}{1:15}'.format('\nBr tretmana', 'Cena[din]')) + '\n' + '=' * 30)
    print(('{0:7}{1:16}'.format(br_tretmana, total)))
    return br_tretmana, total

def cuvanje1(brojac, lista):
    fajl = ''
    if brojac == 0:
        fajl = open('izvestaj1', 'w', encoding='utf-8')
    if brojac == 1:
        fajl = open('izvestaj2', 'w', encoding='utf-8')
    for termin in lista:
        fajl.write(termin['sifra'] + '|' + termin['tretman'] + '|' + termin['datum'] + '|' + termin['pocetak'] + '|' +
                   termin['kraj'] + '|' + termin['klijent'] + '|' + termin['kozmeticar'] + '\n')
    fajl.close()

def cuvanje2(rezultat):
    fajl = open('izvestaj3', 'w', encoding='utf-8')
    fajl.write(rezultat)
    fajl.close()

def cuvanje3(brojac, br_tretman, total):
    fajl = ''
    if brojac == 0:
        fajl = open('izvestaj4', 'w', encoding='utf-8')
    elif brojac == 1:
        fajl = open('izvestaj7', 'w', encoding='utf-8')
    fajl.write(str(br_tretman) + '|' + str(total))
    fajl.close()

def cuvanje4(lista_kozmeticara, lista_broja_tretmana, lista_total):
    fajl = open('izvestaj5', 'w', encoding='utf-8')
    for i in range(len(lista_total)):
        fajl.write(lista_kozmeticara[i] + '|' + str(lista_broja_tretmana[i]) + '|' + str(lista_total[i]) + '\n')
    fajl.close()

def cuvanje5(lista_tretmana, lista_broja_tretmana, ukupno_tretmana, lista_procenti):
    fajl = open('izvestaj6', 'w', encoding='utf-8')
    for i in range(len(lista_tretmana)):
        fajl.write(lista_tretmana[i] + '|' + str(lista_broja_tretmana[i]) + '/' + str(ukupno_tretmana) + '|' +
                   lista_procenti[i] + '%' + '\n')
    fajl.close()

def kreiranje_liste_tretmana(klijenti_username, kozmeticar, vreme):
    danas = date.today()
    lista_tretmana = []
    for termin in termini.ucitavanje_termina_tretmana():
        datum = datetime.strptime(termin['datum'], '%Y-%m-%d').date()
        if kozmeticar == termin['kozmeticar'] and vreme <= datum <= danas and termin['odradjen'] == 'odrađen' \
                and termin['username'] in klijenti_username:
            lista_tretmana.append((termin['tretman'], termin['username']))
        elif kozmeticar == termin['kozmeticar'] and vreme <= datum <= danas and termin['odradjen'] == 'odrađen' \
                and termin['recepcioner'] in klijenti_username:
            lista_tretmana.append((termin['tretman'], termin['recepcioner']))
    return lista_tretmana

def kreiranje_liste_cene(lista_lojalnih, lista_tretmana):
    lista_cena = []
    nazivi_tretmani = []
    for tretman in tretmani.ucitavanje_tretmana():
        nazivi_tretmani.append(tretman['naziv'])
    for elem in lista_tretmana:
        for tretman in tretmani.ucitavanje_tretmana():
            elem = str(elem)
            naziv = elem.split(',')[0][2:][:-1]
            username = elem.split(',')[1][2:][:-2]
            if naziv == tretman['naziv'] and username in lista_lojalnih:
                cena = int(tretman['cena']) * 90 // 100
                lista_cena.append(str(cena))
            elif naziv == tretman['naziv'] and username not in lista_lojalnih:
                lista_cena.append(tretman['cena'])
    return lista_cena


