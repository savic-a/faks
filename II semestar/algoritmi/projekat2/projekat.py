from functools import reduce

from formiranje import *
from datetime import datetime, date
import Levenshtein
import operator


def simulacija_uzivo():
    trajanje, odlazni, dolazni, prekid = ' ', ' ', ' ', ' '

    pozivalac = input('\nUnesite broj pozivaoca: ').replace(' ', '').replace('-', '')
    if trie_broj.find(pozivalac) is None:
        pozivalac = postojeci_broj(pozivalac)
        if not pozivalac:
            return

    primalac = input('\nUnesite broj koji želite da pozovete: ').replace(' ', '').replace('-', '')
    if trie_broj.find(primalac) is None:
        primalac = postojeci_broj(primalac)
        if not primalac:
            return

    fajl = open('blocked.txt', 'r')
    for broj in fajl.readlines():
        if broj.replace(' ', '').replace('-', '')[:-1] == primalac:
            print('Broj koji želite da pozovete je blokiran!')
            return
        elif broj.replace(' ', '').replace('-', '')[:-1] == pozivalac:
            print('Vaš broj je blokiran!')
            return

    if pozivalac == primalac:
        print('Ne možete da pozovete samog sebe!')
        return

    datum = datetime.now().strftime('%d.%m.%Y')
    pocetak = datetime.time(datetime.now())
    while prekid != '':
        prekid = input('\nPRITISNITE ENTER KADA ŽELITE DA ZAVRŠITE POZIV ')
        if prekid == '':
            trajanje = datetime.combine(date.today(), datetime.time(datetime.now())) - datetime.combine(date.today(),
                                                                                                        pocetak)
    pocetak = pocetak.strftime('%H:%M:%S')
    trajanje = str(trajanje)[2:].replace('.', ':')[:-4]
    odlazni, dolazni = vrati_vertex(pozivalac, primalac)
    graf.insert_edge(odlazni, dolazni, [datum, pocetak, trajanje])
    popularnost([odlazni, dolazni])
    sadrzaj = '{0:14}|{1:14}|{2:12}|{3:13}|{4:12}'.format(
        pozivalac,
        primalac,
        datum,
        pocetak,
        trajanje) + '\n'
    return sadrzaj


def simulacija_fajl():
    fajl = open('2zadatak.txt', 'r')
    sadrzaj = ''
    for linija in fajl.readlines():
        vrednost = linija.split(', ')
        pozivalac = vrednost[0].replace(' ', '').replace('-', '')
        primalac = vrednost[1].replace(' ', '').replace('-', '')
        datum_vreme = vrednost[2].split(' ')
        datum = datum_vreme[0]
        vreme = datum_vreme[1]
        trajanje = vrednost[3][:-1]
        u, v = vrati_vertex(pozivalac, primalac)
        graf.insert_edge(u, v, [datum, vreme, trajanje])
        popularnost([u, v])
        sadrzaj += '{0:14}|{1:14}|{2:12}|{3:13}|{4:12}'.format(
                pozivalac,
                primalac,
                datum,
                vreme,
                trajanje) + '\n'
    return sadrzaj


def istorija_dva_broja():
    broj1 = input('\nUnesite prvi broj telefona: ').replace(' ', '').replace('-', '')
    if trie_broj.find(broj1) is None:
        broj1 = postojeci_broj(broj1)
        if not broj1:
            return

    broj2 = input('\nUnesite drugi broj telefona: ').replace(' ', '').replace('-', '')
    if trie_broj.find(broj2) is None:
        broj2 = postojeci_broj(broj2)
        if not broj2:
            return

    v, u = vrati_vertex(broj1, broj2)
    odlazni = graf.get_edge(v, u)
    dolazni = graf.get_edge(u, v)

    rezultat = []

    if odlazni is not None:
        for i in range(0, len(odlazni.data), 3):
            rezultat.extend([[odlazni.origin.element, odlazni.destination.element, datetime.strptime(
                odlazni.data[i] + ' ' + odlazni.data[i+1], '%d.%m.%Y %H:%M:%S'), odlazni.data[i+2]]])

    if dolazni is not None:
        for j in range(0, len(dolazni.data), 3):
            rezultat.extend([[dolazni.origin.element, dolazni.destination.element, datetime.strptime(
                dolazni.data[j] + ' ' + dolazni.data[j+1], '%d.%m.%Y %H:%M:%S'), dolazni.data[j+2]]])

    rezultat = sorted(rezultat, key=operator.itemgetter(2))

    sadrzaj = ''
    for i in range(len(rezultat)):
        datum_vreme = rezultat[i][2].strftime('%d.%m.%Y, %H:%M:%S').split(', ')
        datum = datum_vreme[0]
        vreme = datum_vreme[1]
        sadrzaj += '{0:14}|{1:14}|{2:12}|{3:13}|{4:12}'.format(
            rezultat[i][0],
            rezultat[i][1],
            datum,
            vreme,
            rezultat[i][3]) + '\n'
    return sadrzaj


def istorija_jednog_broja():
    broj = input('\nUnesite broj telefona: ').replace(' ', '').replace('-', '')

    if trie_broj.find(broj) is None:
        broj = postojeci_broj(broj)
        if not broj:
            return

    v, sadrzaj = '', ''
    rezultat = []

    for cvor in graf.vertices():
        if broj == cvor.element:
            v = cvor

    for grana in graf.incident_edges(v):
        for i in range(0, len(grana.data), 3):
            rezultat.extend([[grana.origin.element, grana.destination.element, grana.data[i], grana.data[i+1],
                             grana.data[i+2], grana.destination.popularity]])

    pop = v.get_popularity()
    for grana in graf.incident_edges(v, False):
        for j in range(0, len(grana.data), 3):
            rezultat.extend([[grana.origin.element, grana.destination.element, grana.data[j], grana.data[j+1],
                              grana.data[j+2], pop]])

    rezultat = sorted(rezultat, key=operator.itemgetter(5), reverse=True)

    for i in range(len(rezultat)):
        sadrzaj += '{0:14}|{1:14}|{2:12}|{3:13}|{4:12}'.format(
            rezultat[i][0],
            rezultat[i][1],
            rezultat[i][2],
            rezultat[i][3],
            rezultat[i][4]) + '\n'
    return sadrzaj


def pretraga_po_imenu():
    izbor, sadrzaj, v = '', '', ''
    lista_red = []
    ime = input('\nUnesite ime ili dodajte * za autocomplete: ').capitalize()
    if '*' in ime:
        ime = autocomplete(ime, trie_ime)
        if not ime:
            return

    for imena in trie_ime.iteration():
        if ime == imena.char:
            lista_red = imena.row

    if lista_red:
        for cvor in graf.vertices():
            for lista in lista_red:
                if lista[2] == cvor.element:
                    lista.append(cvor.popularity)

        rezultat = sorted(lista_red, key=operator.itemgetter(3), reverse=True)

        for j in range(len(rezultat)):
            sadrzaj += '{0:14}|{1:14}|{2:14}'.format(ime, rezultat[j][1], rezultat[j][2]) + '\n'
        print(zaglavlje_pretraga())
        print(sadrzaj)

    else:
        print('Uneli ste nepostojeće ime.')


def pretraga_po_prezimenu():
    izbor, sadrzaj, v = '', '', ''
    lista_red = []
    prezime = input('\nUnesite prezime ili dodajte * za autocomplete: ').capitalize()
    if '*' in prezime:
        prezime = autocomplete(prezime, trie_prezime)
        if not prezime:
            return

    for prezimena in trie_prezime.iteration():
        if prezime == prezimena.char:
            lista_red = prezimena.row

    if lista_red:
        for cvor in graf.vertices():
            for lista in lista_red:
                if lista[2] == cvor.element:
                    lista.append(cvor.popularity)

        rezultat = sorted(lista_red, key=operator.itemgetter(3), reverse=True)

        for j in range(len(rezultat)):
            sadrzaj += '{0:14}|{1:14}|{2:14}'.format(rezultat[j][1], prezime, rezultat[j][2]) + '\n'
        print(zaglavlje_pretraga())
        print(sadrzaj)

    else:
        print('Uneli ste nepostojeće prezime.')


def pretraga_po_broju():
    lista_red, sadrzaj = [], ''
    broj = input('\nUnestite broj telefona ili dodajte * za autocomplete: ').replace(' ', '').replace('-', '')

    if '*' in broj:
        broj = autocomplete(broj, trie_broj)
        if not broj:
            return

    for brojevi in trie_broj.iteration():
        if broj == brojevi.char:
            lista_red = brojevi.row

    if lista_red:
        for cvor in graf.vertices():
            for lista in lista_red:
                if lista[0] == cvor.element:
                    lista.append(cvor.popularity)

        rezultat = sorted(lista_red, key=operator.itemgetter(3), reverse=True)

        for j in range(len(rezultat)):
            sadrzaj += '{0:14}|{1:14}|{2:14}'.format(rezultat[j][1], rezultat[j][2], rezultat[j][0]) + '\n'
        print(zaglavlje_pretraga())
        print(sadrzaj)

    else:
        print('Uneli ste nepostojeći broj.')


def popularnost(lista=None):
    if lista is None:
        for v in graf.vertices():
            br_primljenih = graf.degree(v, False)
            tudji_primljeni, ukupno_trajanje = 0, 0

            for grana in graf.incident_edges(v, False):
                tudji_primljeni += graf.degree(grana.origin, False)
                trajanje = datetime.strptime(grana.data[0] + ' ' + grana.data[2], '%d.%m.%Y %M:%S:%f')
                ukupno_trajanje = int(trajanje.timestamp())

            rezultat = br_primljenih + tudji_primljeni + ukupno_trajanje
            v.set_popularity(rezultat)
    else:
        for v in lista:
            br_primljenih = graf.degree(v, False)
            tudji_primljeni, ukupno_trajanje = 0, 0

            for grana in graf.incident_edges(v, False):
                tudji_primljeni += graf.degree(grana.origin, False)
                trajanje = datetime.strptime(grana.data[0] + ' ' + grana.data[2], '%d.%m.%Y %M:%S:%f')
                ukupno_trajanje = int(trajanje.timestamp())

            rezultat = br_primljenih + tudji_primljeni + ukupno_trajanje
            v.set_popularity(rezultat)


def postojeci_broj(broj):
    slicni = did_you_mean(broj)
    if slicni:
        indeks = input('\nUnesite redni broj broja pozivaoca: ')
        while not indeks.isnumeric() or int(indeks) > len(slicni):
            indeks = input('Niste uneli odgovarajući broj. Unesite redni broj broja pozivaoca: ')
        if indeks == '0':
            return
        elif int(indeks) <= len(slicni):
            broj = slicni[int(indeks)-1]
            return broj
    else:
        return False


def vrati_vertex(u, v):
    for cvor in graf.vertices():
        if cvor.element == u:
            u = cvor
        elif cvor.element == v:
            v = cvor
    return u, v


def did_you_mean(broj):
    slicni, i, j, k = [], 1, 1, 1
    for node in trie_broj.iteration():
        if Levenshtein.distance(broj, node.char) <= 3:
            if i <= 5:
                slicni.append(node.char)
            else:
                break
            i += 1
    if not slicni or len(slicni) < 5:
        for node in trie_broj.iteration():
            if Levenshtein.distance(broj, node.char) <= 5:
                if j <= 5 and node not in slicni and len(slicni) < 5:
                    slicni.append(node.char)
                else:
                    break
                j += 1
    if not slicni:
        print('Uneli ste nepostojeći broj!')
    else:
        print('\nUneli ste pogrešan broj. Da li ste mislili na neki od ovih brojeva?\n')
        for elem in slicni:
            print(str(k) + ' - ' + elem)
            k += 1
        print('0 - NAZAD')
    return slicni


def autocomplete(prefix, trie):
    prefix = prefix[:-1]
    svi_pocetci = trie.starts_with(prefix)
    if not svi_pocetci:
        if trie == trie_ime:
            print('Ne postoji nijedna osoba sa tim imenom.')
        elif trie == trie_prezime:
            print('Ne postoji nijedna osoba sa tim prezimenom')
        elif trie == trie_broj:
            print('Ne postoji broj koji počinje unetim ciframa.')
        return False

    else:
        lista_brojeva, rezultat = [], []
        svi_pocetci = reduce(operator.add, svi_pocetci)  # uklanjanje liste [[[][][]], [[][][][]], ...]
        if trie == trie_ime or trie == trie_prezime:
            for i in range(len(svi_pocetci)):
                lista_brojeva.append(svi_pocetci[i][2])

            for cvor in graf.vertices():
                if cvor.element in lista_brojeva:
                    for lista in svi_pocetci:
                        if lista[2] == cvor.element:
                            lista.append(cvor.popularity)

            rezultat = sorted(svi_pocetci, key=operator.itemgetter(3), reverse=True)
    indeks, osoba = '', ''
    i = 1
    if trie == trie_ime:
        lista_imena, novo = [], []  # da se ime ne bi ponovilo pri ispisu
        for elem in rezultat:
            if i < 6:
                if elem[0] in lista_imena:
                    rezultat.remove(elem)
                else:
                    lista_imena.append(elem[0])
                    novo.append(elem)
                    print(str(i) + ' - ' + elem[0])
                    i += 1
        rezultat = novo

        print('0 - NAZAD')
        indeks = input('\nUnesite broj imena osobe koju tražite: ')

    elif trie == trie_prezime:
        for elem in rezultat:
            if i < 6:
                print(str(i) + ' - ' + elem[0])
                i += 1

        print('0 - NAZAD')
        indeks = input('\nUnesite broj prezimena osobe koju tražite: ')

    elif trie == trie_broj:
        rezultat = svi_pocetci
        for elem in rezultat:
            if i < 6:
                print(str(i) + ' - ' + elem[0])
                i += 1

        print('0 - NAZAD')
        indeks = input('\nUnesite broj broja koji tražite: ')

    while not indeks.isnumeric() or int(indeks) > len(rezultat) or int(indeks) > 5 or int(indeks) < 0:
        indeks = input('Niste uneli odgovarajući broj. Pokušajte opet: ')
    if int(indeks) <= len(rezultat) and 0 < int(indeks) <= 5:
        osoba = rezultat[int(indeks)-1][0]
    return osoba


def zaglavlje():
    return \
        '\nPozivalac     |Pozvani broj  |Datum       |Vreme poziva |Trajanje  \n' \
        '================================================================='


def zaglavlje_pretraga():
    return '\nIme           |Prezime       |Broj          \n' \
            '=========================================='


def meni():
    while True:
        print('\nOdaberite opciju:')
        print('1. Simulacija pozivanja uživo')
        print('2. Simulacija pozivanja iz fajla')
        print('3. Istorija poziva dva broja')
        print('4. Istorija poziva jednog broja')
        print('5. Pretraga telefonskog imenika unosom imena')
        print('6. Pretraga telefonskog imenika unosom prezimena')
        print('7. Pretraga telefonskog imenika unosom početnih cifara broja telefona')
        print('0. IZLAZ\n')
        izbor = input('Unesite redni broj operacije, koju želite da izvršite: ')
        if izbor == '1':
            rez = simulacija_uzivo()
            if rez is not None:
                print(zaglavlje())
                print(rez)
        elif izbor == '2':
            rez = simulacija_fajl()
            print(zaglavlje())
            print(rez)
        elif izbor == '3':
            rez = istorija_dva_broja()
            if rez != '' and rez is not None:
                print(zaglavlje())
                print(rez)
            elif rez == '':
                print('\nUneti brojevi nisu obavili nijedan poziv.')
        elif izbor == '4':
            rez = istorija_jednog_broja()
            if rez:
                print(zaglavlje())
                print(rez)
            else:
                print('Unet broj nema istoriju poziva.')
        elif izbor == '5':
            pretraga_po_imenu()
        elif izbor == '6':
            pretraga_po_prezimenu()
        elif izbor == '7':
            pretraga_po_broju()
        elif izbor == '0':
            exit()
        else:
            print('Niste odabrali odgovarajuću opciju!')


if __name__ == '__main__':
    graf = formiranje_grafa()
    popularnost()
    trie_ime, trie_prezime, trie_broj = formiranje_trie()
    meni()
