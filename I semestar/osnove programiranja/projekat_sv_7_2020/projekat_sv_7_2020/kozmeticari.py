import tretmani


def ucitavanje_kozmeticara():
    lista_kozmeticara = []
    for lines in open('kozmeticari.txt', encoding='utf-8').readlines():
        username, lozinka, ime, prezime, uloga, telefon, email, pol, tip = lines.strip().split('|')
        kozmeticar = {
            'username': username,
            'lozinka': lozinka,
            'ime': ime,
            'prezime': prezime,
            'uloga': uloga,
            'telefon': telefon,
            'email': email,
            'pol': pol,
            'tip': tip
        }
        lista_kozmeticara.append(kozmeticar)
    return lista_kozmeticara


def lista_mogucih_kozmeticara(sifra):
    tip = tretmani.trazenje_tipa_zeljenog_tretmana(sifra)
    lista_kozmeticara = []
    for kozmeticar in ucitavanje_kozmeticara():
        if tip == kozmeticar['tip']:
            lista_kozmeticara.append(kozmeticar)
    return lista_kozmeticara


def ispis_mogucih_kozmeticara(sifra):
    lista_kozmeticara = lista_mogucih_kozmeticara(sifra)
    rezultat = ''
    i = 0
    for kozmeticar in lista_kozmeticara:
        rezultat += str(i+1) + ' - ' + kozmeticar['ime'] + ' ' + kozmeticar['prezime'] + '\n'
        i += 1
    return rezultat


def provera_kozmeticara(sifra, izabran_kozmeticar):
    ime = izabran_kozmeticar.split()[0]
    try:
        prezime = izabran_kozmeticar.split()[1]
    except IndexError:
        return False
    for kozmeticar in ucitavanje_kozmeticara():
        if kozmeticar['ime'] == ime and kozmeticar['prezime'] == prezime:
            for kozme in lista_mogucih_kozmeticara(sifra):
                if ime == kozme['ime'] and prezime == kozme['prezime']:
                    return True
    return False


def prijavljivanje_kozmeticara(username, lozinka):
    for kozmeticar in ucitavanje_kozmeticara():
        if username == kozmeticar['username'] and lozinka == kozmeticar['lozinka']:
            return username
    return False


def odredjivanje_imena_i_prezimena(username):
    for kozmeticar in ucitavanje_kozmeticara():
        if username == kozmeticar['username']:
            ime_kozmeticara = kozmeticar['ime'] + ' ' + kozmeticar['prezime']
            return ime_kozmeticara


def provera_validnosti_kozmeticara(kozmeticar):
    if ' ' not in kozmeticar:
        return False
    kozmeticar = kozmeticar.title()
    kozmeticar_ime = kozmeticar.split()[0]
    kozmeticar_prezime = kozmeticar.split()[1]
    for kozmeticar in ucitavanje_kozmeticara():
        if kozmeticar_ime == kozmeticar['ime'] and kozmeticar_prezime == kozmeticar['prezime']:
            return True
    return False


def registracija_kozmeticara(username, lozinka, tel, email, ime, prezime, pol, tretman):
    ime = ime.capitalize()
    prezime = prezime.capitalize()
    fajl = open('kozmeticari.txt', 'a', encoding='utf-8')
    fajl.write(
        username + '|' + lozinka + '|' + ime + '|' + prezime + '|' + '2' + '|' + tel + '|' + email + '|' + pol + '|' + tretman +'\n')
    fajl.close()


def provera_validnosti_pri_registraciji(username, tel, email, tretman):
    lista_username = []
    for kozmeticar in ucitavanje_kozmeticara():
        lista_username.append(kozmeticar['username'])
    if username in lista_username:
        return False
    elif not tel.isdigit():
        return False
    elif '@' not in email:
        return False
    elif tretman != 'masaža' and tretman != 'pedikir' and tretman != 'manikir' and tretman != 'tretman lica':
        return False
    return True

