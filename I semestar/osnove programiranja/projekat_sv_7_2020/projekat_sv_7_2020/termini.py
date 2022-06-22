import tretmani
import korisnici
import kozmeticari
from datetime import date, datetime, timedelta

def ucitavanje_termina_tretmana():
    lista_termina = []
    for lines in open('termini.txt', encoding='utf-8').readlines():
        sifra, sifra1, datum, pocetak, kraj, username, klijent, tretman, kozmeticar, odradjen, recepcioner = lines.strip().split('|')
        termin = {
            'sifra': sifra,
            'sifra1': sifra1,
            'datum': datum,
            'pocetak': pocetak,
            'kraj': kraj,
            'username': username,
            'klijent': klijent,
            'tretman': tretman,
            'kozmeticar': kozmeticar,
            'odradjen': odradjen,
            'recepcioner': recepcioner
        }
        lista_termina.append(termin)
    return lista_termina

def zaglavljee():
    return ('{0:20}|{1:5}'.format(
        'Naziv tretmana', 'Količina prodatih')) + '\n' + '=' * 40

def najpopularniji_tretmani(tip_tretmana):
    lista_tretmana_dict = tretmani.pretraga_po_tipu_tretmana(tip_tretmana)
    lista_tretmana = []
    for tretman in lista_tretmana_dict:
        lista_tretmana.append(tretman['naziv'])
    danas = date.today().strftime('%Y-%m-%d')
    danas = datetime.strptime(danas, '%Y-%m-%d')
    prosla = date.today().strftime('2020-%m-%d')
    prosla = datetime.strptime(prosla, '%Y-%m-%d')
    lista_svih_tretmana = []
    for termin in ucitavanje_termina_tretmana():
        datum = datetime.strptime(termin['datum'], '%Y-%m-%d')
        if prosla < datum < danas:
            lista_svih_tretmana.append(termin['tretman'])
    from collections import Counter
    a_dict = dict(Counter(lista_svih_tretmana))
    a_lista = sorted(a_dict, key=a_dict.get, reverse=True)
    rezultat = ''
    for element in a_lista:
        if element in lista_tretmana:
            rezultat += '{0:20}|{1:5}'.format(element, a_dict[element]) + '\n'
    return rezultat

def provera_validnosti_pri_zakazivanju(odb_tretman, kozmeticar):
    for termin in ucitavanje_termina_tretmana():
        if odb_tretman == termin['tretman'] and kozmeticar.title == termin['kozmeticar']:
            return True
    return False

def odabir_datuma_za_odredjenog_kozmeticara(zeljeni_kozmeticar, pocetak = 10, kraj = 17):
    dan1 = date.today()
    dan2 = dan1
    zauzeti_termini = []
    slobodni_termini = []
    for i in range(5):
        dan1 += timedelta(days=1)
        for termin in ucitavanje_termina_tretmana():
            if zeljeni_kozmeticar == termin['kozmeticar'] and str(dan1) == termin['datum']:
                sat, minuti = termin['pocetak'].split(':')
                zauzeti_termini.append((sat, termin['datum']))
    print('\nDostupni termini:')
    broj = 1
    for i in range(5):
        dan2 += timedelta(days=1)
        for j in range(pocetak, kraj):
            rezultat = True
            for termin in zauzeti_termini:
                if str(j) == termin[0] and str(dan2) == termin[1]:
                    rezultat = False
            if rezultat:
                print(str(broj) + '. - ' + str(j) + ':00 ' + str(dan2))
                slobodni_termini.append((str(j), str(dan2)))
                broj += 1
    print('\nIzaberite željeni datum unosom rednog broja:')
    izbor = input('>> ')
    duzina = len(slobodni_termini)
    lista_izbora = [str(x) for x in range(1, duzina + 1)]
    while izbor not in [i for i in lista_izbora]:
        print('Greška!')
        izbor = input('>> ')
    return slobodni_termini[int(izbor) - 1]

def odabir_tretmana_za_neodredjenog_kozmeticara(moguci_kozmeticari, pocetak = 10, kraj = 17):
    zauzeti_termini = []
    slobodni_termini = []
    for kozmeticar in moguci_kozmeticari:
        dan1 = date.today()
        for i in range(5):
            dan1 += timedelta(days=1)
            for termin in ucitavanje_termina_tretmana():
                if kozmeticar['ime'] + ' ' + kozmeticar['prezime'] == termin['kozmeticar'] and str(dan1) == termin['datum']:
                    sat, minuti = termin['pocetak'].split(':')
                    zauzeti_termini.append((sat, termin['datum'], termin['kozmeticar']))
    dan2 = date.today()
    print('\nDostupni termini:')
    broj = 1
    for kozmeticar in moguci_kozmeticari:
        for i in range(5):
            dan2 += timedelta(days=1)
            for j in range(pocetak, kraj):
                rezultat = True
                for termin in zauzeti_termini:
                    if str(j) == termin[0] and str(dan2) == termin[1] \
                            and kozmeticar['ime'] + ' ' + kozmeticar['prezime'] == termin[2]:
                        rezultat = False
                if rezultat:
                    print(str(broj) + '. - ' + str(j) + ':00  ' + str(dan2) + '  ' + kozmeticar['ime'] + ' '
                          + kozmeticar['prezime'])
                    slobodni_termini.append((str(j), str(dan2), kozmeticar['ime'], kozmeticar['prezime']))
                    broj += 1
        dan2 = date.today()
    print('\nIzaberite željeni datum unosom rednog broja:')
    izbor = input('>> ')
    duzina = len(slobodni_termini)
    lista_izbora = [str(x) for x in range(1, duzina + 1)]
    while izbor not in [i for i in lista_izbora]:
        print('Greška!')
        izbor = input('>> ')
    return slobodni_termini[int(izbor) - 1]

def kreiranje_sifre_zakazanog_tretmana():
    sifra = 0
    for tretman in ucitavanje_termina_tretmana():
        while int(tretman['sifra']) > sifra:
            sifra = int(tretman['sifra'])
    sifra += 1
    if sifra < 100:
        sifra = '00' + str(sifra)
    elif 99 < sifra < 1000:
        sifra = '0' + str(sifra)
    else:
        sifra = str(sifra)
    return sifra

def zakazivanje(izabran_termin, sifra, username, kozmeticar, ime):
    trajanje, naziv = tretmani.vreme_trajanja_tretmana(sifra)
    klijent = ''
    if ime == '':
        ime_klijenta, prezime_klijenta = korisnici.ime_i_prezime_prijavljenog_klijenta(username)
        klijent = ime_klijenta + ' ' + prezime_klijenta
    if not kozmeticar:
        ime_kozmeticara = izabran_termin[2]
        prezime_kozmeticara = izabran_termin[3]
        kozmeticar = ime_kozmeticara + ' ' + prezime_kozmeticara
    pocetak_tretmana = izabran_termin[0]
    datum = izabran_termin[1]
    if int(trajanje) < 60:
        kraj = pocetak_tretmana + ':' + trajanje
    else:
        kraj = int(pocetak_tretmana) + 1
        kraj = str(kraj) + ':00'
    pocetak_tretmana = izabran_termin[0] + ':00'
    return datum, pocetak_tretmana, kraj, klijent, naziv, kozmeticar

def potvrda_zakazivanja_klijent(sifra_zakazivanja, sifra, datum, pocetak, kraj, username, klijent, naziv, kozmeticar):
    fajl = open('termini.txt', 'a', encoding='utf-8')
    fajl.write(
        sifra_zakazivanja + '|' + sifra + '|' + datum + '|' + pocetak + '|' + kraj + '|' + username + '|' + klijent +
        '|' + naziv + '|' + kozmeticar + '|zakazan' + '|' + '' + '\n')
    fajl.close()

def potvrda_zakazivanja_recepc(sifra_zakaz, sifra, datum, pocetak, kraj, username, klijent, naziv, kozmeticar, ime, tel, email, pol, username_klijenta):
    fajl = open('termini.txt', 'a', encoding='utf-8')
    if klijent != '' and ime != '':
        fajl.write(
            sifra_zakaz + '|' + sifra + '|' + datum + '|' + pocetak + '|' + kraj + '|' + username + '|' + klijent +
            '|' + naziv + '|' + kozmeticar + '|zakazan' + '|' + ime + ',' + tel + ',' + email + ',' + pol + '\n')
    elif ime != '' and klijent == '':
        fajl.write(
            sifra_zakaz + '|' + sifra + '|' + datum + '|' + pocetak + '|' + kraj + '|' + username + '|' + ime +
            '|' + naziv + '|' + kozmeticar + '|zakazan' + '|' + tel + ',' + email + ',' + pol + '\n')
    else:
        fajl.write(
            sifra_zakaz + '|' + sifra + '|' + datum + '|' + pocetak + '|' + kraj + '|' + username + '|' + klijent +
            '|' + naziv + '|' + kozmeticar + '|zakazan' + '|' + username_klijenta + '\n')
    fajl.close()

def zakazivanje_sl_tretmana(datum, pocetak, kraj, kozmeticar, naziv):
    if kraj > '16:00':
        print('\nNije više moguće zakazati tretman u ovom danu.')
        return False, False
    pocetak_sat = pocetak.split(':')[0]
    pocetak_sat = int(pocetak_sat) + 1
    nov_pocetak = str(pocetak_sat) + ':00'
    for termin in ucitavanje_termina_tretmana():
        if termin['kozmeticar'] == kozmeticar and termin['datum'] == datum and termin['pocetak'] == nov_pocetak:
            print('\nKozmetičar više nije dostupan.')
            return False, False
    tip_tretmana = moguci_tretmani(kozmeticar)
    lista_tretmana_dict = tretmani.pretraga_po_tipu_tretmana(tip_tretmana)
    lista_tretmana = []
    for tretman in lista_tretmana_dict:
        lista_tretmana.append(tretman['naziv'])
    brojac = len(lista_tretmana)
    vec_zakazani = []
    j = brojac
    for i in range(brojac):
        for termin in ucitavanje_termina_tretmana():
            stari_pocetak = int(pocetak.split(':')[0]) - j
            stari_pocetak = str(stari_pocetak) + ':00'
            if datum == termin['datum'] and kozmeticar == termin['kozmeticar'] and termin['pocetak'] == stari_pocetak:
                vec_zakazani.append(termin['tretman'])
        j -= 1
    if naziv in lista_tretmana:
        lista_tretmana.remove(naziv)
    for zakazano in vec_zakazani:
        if zakazano in lista_tretmana:
            lista_tretmana.remove(zakazano)
    rezultat = ''
    i = 1
    for tretman in lista_tretmana:
        rezultat += str(i) + '. - ' + tretman + '\n'
        i += 1
    if lista_tretmana:
        print('\nDostupni tretmani:')
        print(rezultat)
        print('Unesite broj tretmana:')
        izbor = input('>> ')
        duzina = len(lista_tretmana)
        lista_izbora = [str(x) for x in range(1, duzina + 1)]
        while izbor not in [i for i in lista_izbora]:
            print('Greška!')
            izbor = input('>> ')
        return lista_tretmana[int(izbor) - 1], nov_pocetak
    else:
        print('\nNe postoji više tretman koji obavlja taj kozmetičar.')
        return False, False

def moguci_tretmani(izabran_kozmeticar):
    for kozmeticar in kozmeticari.ucitavanje_kozmeticara():
        if izabran_kozmeticar == kozmeticar['ime'] + ' ' + kozmeticar['prezime']:
            return kozmeticar['tip']

def sifra_odabranog_tretmana(izabran_tretman):
    for tretman in tretmani.ucitavanje_tretmana():
        if tretman['naziv'] == izabran_tretman:
            return tretman['sifra']

def zakazivanje_istog_tretmana(sifra, kozmeticar, datum, pocetak, kraj):
    tretmani.trazenje_tipa_zeljenog_tretmana(sifra)
    lista_kozmeticara = kozmeticari.lista_mogucih_kozmeticara(sifra)
    lista_moguc_kozm = []
    rezultat = ''
    i = 0
    for kozm in lista_kozmeticara:
        moguc_kozmeticar = kozm['ime'] + ' ' + kozm['prezime']
        if moguc_kozmeticar != kozmeticar:
            lista_moguc_kozm.append(moguc_kozmeticar)
    for termin in ucitavanje_termina_tretmana():
        for kozm in lista_kozmeticara:
            moguc_kozmeticar = kozm['ime'] + ' ' + kozm['prezime']
            if termin['datum'] == datum and moguc_kozmeticar == termin['kozmeticar'] and pocetak == termin['pocetak'] \
                    and kraj == termin['kraj']:
                lista_kozmeticara.remove(kozm)
    for kozm in lista_kozmeticara:
        rezultat += str(i + 1) + '. - ' + kozm['ime'] + ' ' + kozm['prezime'] + '\n'
        i += 1
    if rezultat:
        print('\nPreostali kozmetičari koji obavljaju ovaj tretman:')
        print(rezultat)
    else:
        print('\nViše ne postoji dotupnih kozmetičara za ovaj tretman.')
    return lista_kozmeticara

def provera_dostupnosti_kozmeticara(lista_kozmeticara, kozmeticar, datum, pocetak, kraj):
    slobodni_kozmeticari = []
    rezultat = ''
    broj = 1
    for termin in ucitavanje_termina_tretmana():
        if kozmeticar:
            if termin['kozmeticar'] == kozmeticar and termin['datum'] == datum and termin['pocetak'] == pocetak \
                    and termin['kraj'] == kraj:
                return False
            return True
        for kozm in lista_kozmeticara:
            kozmeticar = kozm['ime'] + ' ' + kozm['prezime']
            if termin['kozmeticar'] == kozmeticar and termin['datum'] == datum and termin['pocetak'] == pocetak \
                    and termin['kraj'] == kraj:
                return False
            else:
                rezultat += str(broj) + '. - ' + datum + ' ' + pocetak + ' ' + kraj + ' ' + kozmeticar + '\n'
                slobodni_kozmeticari.append(kozmeticar)
                broj += 1
        print('\nMogući tretmani:')
        print(rezultat)
        print('\nUnesite broj tretmana:')
        izbor = input('>> ')
        duzina = len(slobodni_kozmeticari)
        lista_izbora = [str(x) for x in range(1, duzina + 1)]
        while izbor not in [i for i in lista_izbora]:
            print('Greška!')
            izbor = input('>> ')
        return slobodni_kozmeticari[int(izbor) - 1]

def zaglavlje():
    return ('{0:8}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:15}|{7:8}'.format(
        '\nBroj', 'Naziv tretmana', 'Datum', 'Početak', 'Kraj', 'Klijent', 'Kozmetičar', 'Cena')) + '\n' +\
        '='*110

def kreiranje_liste_tretmana_u_poslednjih_godinu_dana(username):
    danas = date.today()
    prosla_god = danas.replace(year=danas.year-1)
    lista_tretmana = []
    for termin in ucitavanje_termina_tretmana():
        datum = datetime.strptime(termin['datum'], '%Y-%m-%d').date()
        if (username == termin['username'] or username == termin['recepcioner']) and prosla_god <= datum <= danas\
                and termin['odradjen'] == 'odrađen':
            naziv_tretmana = termin['tretman']
            lista_tretmana.append(naziv_tretmana)
    return lista_tretmana

def kreiranje_liste_nerealizovanih_tretmana(username):
    lista_tretmana = []
    for termin in ucitavanje_termina_tretmana():
        if (username == termin['username'] or username == termin['recepcioner']) and termin['odradjen'] == 'zakazan':
            naziv_tretmana = termin['tretman']
            lista_tretmana.append(naziv_tretmana)
    return lista_tretmana

def kreiranje_liste_cena(lista_tretmana):
    lista_cena = []
    nazivi_tretmani = []
    for tretman in tretmani.ucitavanje_tretmana():
        nazivi_tretmani.append(tretman['naziv'])
    for elem in lista_tretmana:
        for tretman in tretmani.ucitavanje_tretmana():
            if elem in nazivi_tretmani:
                if elem == tretman['naziv']:
                    lista_cena.append(tretman['cena'])
    return lista_cena

def pregled_nerealizovanih_tretmana(username):
    rezultat = ''
    total = 0
    lista_lojalnih = []
    for klijent in korisnici.ucitavanje_korisnika():
        username_klijent = klijent['username']
        lista_tretmana_lojalnost = kreiranje_liste_tretmana_u_poslednjih_godinu_dana(username_klijent)
        lista_cena_lojalnost = kreiranje_liste_cena(lista_tretmana_lojalnost)
        for cena in range(len(lista_cena_lojalnost)):
            total += int(lista_cena_lojalnost[cena])
        if total >= 30000:
            lista_lojalnih.append(username_klijent)
        total = 0
    lista_tretmana = kreiranje_liste_nerealizovanih_tretmana(username)
    lista_cena = kreiranje_liste_cena(lista_tretmana)
    if not lista_cena:
        return rezultat
    lista_novih_cena = []
    for username_klijenta in lista_lojalnih:
        if username_klijenta == username:
            for cena in lista_cena:
                cena = 90 * int(cena) // 100
                lista_novih_cena.append(cena)
    if lista_novih_cena:
        lista_cena.clear()
        for cena in lista_novih_cena:
            lista_cena.append(str(cena))
    i = 0
    for termin in ucitavanje_termina_tretmana():
        if (username == termin['username'] or username == termin['recepcioner']) and 'zakazan' == termin['odradjen']:
            rezultat += '{0:7}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}|{6:15}|{7:8}'.format(
                    termin['sifra'],
                    termin['tretman'],
                    termin['datum'],
                    termin['pocetak'],
                    termin['kraj'],
                    termin['klijent'],
                    termin['kozmeticar'],
                    lista_cena[i]) + '\n'
            i += 1
            if i == len(lista_cena):
                return rezultat

def zaglavlje_1():
    return ('{0:8}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}'.format(
        '\nBroj', 'Naziv tretmana', 'Datum', 'Početak', 'Kraj', 'Klijent')) + '\n' + '=' * 85

def raspored_tretmana_kozmeticara(username):
    ime_kozmeticara = kozmeticari.odredjivanje_imena_i_prezimena(username)
    dan = date.today().weekday()
    vreme = datetime.now().hour
    if dan == 6 and vreme >= 17:
        print('\nKraj nedelje!')
        return
    elif dan == 6 and vreme < 17:
        opseg = 0
    else:
        opseg = 6 - dan
    rezultat = ''
    for termin in ucitavanje_termina_tretmana():
        datum = datetime.strptime(termin['datum'], '%Y-%m-%d').date()
        pocetak = datetime.strptime(termin['pocetak'], '%H:%M').time()
        if ime_kozmeticara == termin['kozmeticar'] and date.today() <= datum <= (date.today() + timedelta(days=opseg)):
            if datum == date.today() and pocetak <= datetime.now().time():
                continue
            else:
                rezultat += '{0:7}|{1:20}|{2:15}|{3:10}|{4:10}|{5:20}'.format(
                    termin['sifra'],
                    termin['tretman'],
                    termin['datum'],
                    termin['pocetak'],
                    termin['kraj'],
                    termin['klijent']) + '\n'
    if dan != 6 and rezultat == '':
        print('\nNemate nijedan zakazan tretman do kraja nedelje.')
    return rezultat

def pregled_radnog_vremena(username):
    opseg = 0
    dan = date.today().weekday()
    kozmeticar = kozmeticari.odredjivanje_imena_i_prezimena(username)
    vreme = datetime.now().hour
    if dan == 6 and vreme >= 17:
        print('\nKraj nedelje!')
    elif dan == 6 and vreme < 17:
        opseg = 1
    else:
        opseg = 6 - dan
    if vreme < 17:
        pocetni_datum = date.today() + timedelta(days=-1)
    else:
        pocetni_datum = date.today()
    zauzeti_termini = []
    for i in range(opseg):
        pocetni_datum += timedelta(days=1)
        for termin in ucitavanje_termina_tretmana():
            if kozmeticar == termin['kozmeticar'] and str(pocetni_datum) == termin['datum']:
                zauzeti_termini.append((termin['pocetak'], termin['kraj']))
                zauzeti_termini.sort()
        if not zauzeti_termini:
            print('\n' + str(pocetni_datum))
            print('Slobodan dan!\n')
        else:
            print('\n' + str(pocetni_datum))
            print('Zauzeti termini')
            for termin in zauzeti_termini:
                print(termin[0] + ' - ' + termin[1])
            print('\nPauza')
            j = 1
            for i in range(10, 17):
                for termin in zauzeti_termini:
                    if datetime.strptime(termin[0], '%H:%M') + timedelta(hours=1) == datetime.strptime(termin[1], '%H:%M') \
                            and str(i) + ':00' == termin[0]:
                        if '10:00' != zauzeti_termini[0][0] and termin[0] == zauzeti_termini[0][0]:
                            print('10:00 - ' + termin[0])
                            if len(zauzeti_termini) == 1 and termin[1] != '17:00':
                                print(termin[1] + ' - 17:00')
                            elif len(zauzeti_termini) == 1 and termin[1] == '17:00':
                                print()
                        elif '10:00' == zauzeti_termini[0][0]:
                            if len(zauzeti_termini) == 1:
                                print(termin[1] + ' - 17:00')
                            else:
                                if termin[1] != zauzeti_termini[1][0] and termin[1] != '17:00':
                                    print(termin[1] + ' - ' + zauzeti_termini[1][0])
                                    j += 1
                                    if j == len(zauzeti_termini):
                                        if zauzeti_termini[j-1][1] != '17:00':
                                            print(termin[1] + ' - 17:00')
                                        else:
                                            print()
                        else:
                            if termin[0] != zauzeti_termini[j-1][1]:
                                print(zauzeti_termini[j-1][1] + ' - ' + zauzeti_termini[j][0])
                                j += 1
                                if j == len(zauzeti_termini):
                                    if termin[1] != '17:00':
                                        print(termin[1] + ' - 17:00')
                            elif j == len(zauzeti_termini) - 1:
                                if termin[1] != '17:00':
                                    print(termin[1] + ' - 17:00')
                                else:
                                    print()
                            else:
                                print(zauzeti_termini[j-1][1] + ' - ' + zauzeti_termini[j][0])
                    elif str(i) + ':00' == termin[0]:
                        if '10:00' != zauzeti_termini[0][0] and termin[0] == zauzeti_termini[0][0]:
                            print('10:00 - ' + termin[0])
                            if len(zauzeti_termini) == 1:
                                print(termin[1] + ' - 17:00')
                        elif '10:00' == zauzeti_termini[0][0]:
                            if len(zauzeti_termini) == 1:
                                print(termin[1] + ' - 17:00')
                            else:
                                try:
                                    print(termin[1] + ' - ' + zauzeti_termini[j][0])
                                    j += 1
                                except IndexError:
                                    print(termin[1] + ' - 17:00')
                        else:
                            print(zauzeti_termini[j-1][1] + ' - ' + zauzeti_termini[j][0])
                            j += 1
                            if j == len(zauzeti_termini):
                                print(termin[1] + ' - 17:00')

        zauzeti_termini.clear()

