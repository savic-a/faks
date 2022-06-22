import kozmeticari
import recepcioneri
import menadzer


def ucitavanje_korisnika():
    lista_klijenata = []
    for lines in open('klijenti.txt', encoding='utf-8').readlines():
        username, lozinka, ime, prezime, uloga, telefon, email, pol = lines.strip().split('|')
        klijent = {
            'username': username,
            'lozinka': lozinka,
            'ime': ime,
            'prezime': prezime,
            'uloga': uloga,
            'telefon': telefon,
            'email': email,
            'pol': pol
        }
        lista_klijenata.append(klijent)
    return lista_klijenata


def prijavljivanje_korisnika(username, lozinka):
    for klijent in ucitavanje_korisnika():
        if username == klijent['username'] and lozinka == klijent['lozinka']:
            return username
    return False


def registracija_meni():
    username = input('\nUnesite korisničko ime: ')
    lozinka = input('Unesite lozinku: ')
    tel = input('Unesite kontakt telefon (samo brojeve): ')
    email = input('Unesite email adresu: ').lower()
    ime = input('Unesite ime: ').capitalize()
    prezime = input('Unesite prezime: ').capitalize()
    pol = input('Unesite pol (muško, žensko): ').lower()
    return username, lozinka, tel, email, ime, prezime, pol


def provera_validnosti_podataka_registracije(username, tel, email, pol):
    lista_username = lista_svih_usernames()
    if username in lista_username:
        print('\nUneli ste već postojeće korisničko ime!')
        return False
    elif not tel.isdigit() or len(tel) < 9 or len(tel) > 11:
        print('\nNevalidan unos broja!')
        return False
    elif '@' not in email or '.com' not in email:
        print('\nNevalidan unos email adrese!')
        return False
    elif 'musko' != pol and 'muško' != pol and 'zensko' != pol and 'žensko' != pol:
        print('\nPazite na unos pola!')
        return False
    return True


def provera_validnog_username_pri_prijavi(username):
    for klijent in ucitavanje_korisnika():
        if username == klijent['username']:
            return True
    return False


def provera_podataka_prijava_kod_recepcionera(tel, email, pol):
    if not tel.isdigit() or len(tel) < 9 or len(tel) > 11:
        print('\nNevalidan unos broja!')
        return False
    elif '@' not in email or '.com' not in email:
        print('\nNevalidan unos email adrese!')
        return False
    elif 'musko' != pol and 'muško' != pol and 'zensko' != pol and 'žensko' != pol:
        print('\nPazite na unos pola!')
        return False
    return True


def dodavanje_klijenta(username, lozinka, tel, email, ime, prezime, pol):
    ime = ime.capitalize()
    prezime = prezime.capitalize()
    fajl = open('klijenti.txt', 'a', encoding='utf-8')
    fajl.write(
        username + '|' + lozinka + '|' + ime + '|' + prezime + '|' + '1' + '|' + tel + '|' + email + '|' + pol + '\n')
    fajl.close()


def ime_i_prezime_prijavljenog_klijenta(username):
    for klijent in ucitavanje_korisnika():
        if username == klijent['username']:
            return klijent['ime'], klijent['prezime']


def provera_validnosti_klijenta(klijent):
    if ' ' not in klijent:
        return False
    klijent = klijent.title()
    klijent_ime = klijent.split()[0]
    klijent_prezime = klijent.split()[1]
    for klijent in ucitavanje_korisnika():
        if klijent_ime == klijent['ime'] and klijent_prezime == klijent['prezime']:
            return True
    return False


def kreiranje_liste_username():
    klijenti_username = []
    for klijent in ucitavanje_korisnika():
        klijenti_username.append(klijent['username'])
    return klijenti_username


def lista_svih_usernames():
    lista_usernames = []
    for klijent in ucitavanje_korisnika():
        lista_usernames.append(klijent['username'])
    for kozmeticar in kozmeticari.ucitavanje_kozmeticara():
        lista_usernames.append(kozmeticar['username'])
    for recepcioner in recepcioneri.ucitavanje_recepcionera():
        lista_usernames.append(recepcioner['username'])
    for menadzeri in menadzer.ucitavanje_menadzera():
        lista_usernames.append(menadzeri['username'])
    return lista_usernames
