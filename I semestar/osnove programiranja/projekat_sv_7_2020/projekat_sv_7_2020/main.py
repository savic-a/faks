import korisnici
import tretmani
import termini
import kozmeticari
import recepcioneri
import menadzer
import poseban_program


def main():
    print('\nDobrodošli u salon Estetiks!!!\nOdaberite neku od sledećih opcija(1-8):\n')
    print('1. - Registracija\n2. - Prijava')
    izbori()
    moj_izbor(0, "")


def izbori():
    print('\nOpšte dostupnosti:\n3. - Izlazak iz aplikacije\n4. - Pregled dostupnih tretmana\n5. - Pretraga tretmana'
          '\n6. - Višekriterijumska pretraga tretmana\n7. - Prikaz 10 najpopularnijih tretmana')


def moj_izbor(brojac, username):
    izbor = input(">> ")
    if brojac == 0:
        if izbor == '1':
            username = registracija()
            if username:
                print('\nUspešno ste se registrovali!')
                klijent_main(username)
            else:
                nazad(brojac, username)
        elif izbor == '2':
            username_klijent, username_kozmeticar, username_recepcioner, username_menadzer = prijavljivanje()
            if not username_klijent and not username_kozmeticar and not username_recepcioner and not username_menadzer:
                print('\nNiste uneli postojeće ime i/ili lozinku.')
                nazad(brojac, username)
            elif username_klijent:
                print('\nUspešno ste se prijavili!')
                klijent_main(username_klijent)
            elif username_kozmeticar:
                print('\nUspešno ste se prijavili!')
                kozmeticar_main(username_kozmeticar)
            elif username_recepcioner:
                print('\nUspešno ste se prijavili!')
                recepcioner_main(username_recepcioner)
            elif username_menadzer:
                print('\nUspešno ste se prijavili!')
                menadzeri_main()
        else:
            opsti_izbori(izbor, brojac, '')
    elif brojac == 1:
        if izbor == '1':
            zakazivanje(username, '')
            nazad(brojac, username)
        elif izbor == '2':
            pregled_nerealizovaih_tretmana(username)
            nazad(brojac, username)
        elif izbor == '8':
            odjava_sa_sistema()
            nazad(brojac, username)
        else:
            opsti_izbori(izbor, brojac, '')
    elif brojac == 2:
        if izbor == '1':
            rezultat = termini.raspored_tretmana_kozmeticara(username)
            if rezultat != '':
                print(termini.zaglavlje_1())
                print(termini.raspored_tretmana_kozmeticara(username))
            nazad(brojac, username)
        elif izbor == '2':
            termini.pregled_radnog_vremena(username)
            nazad(brojac, username)
        elif izbor == '8':
            odjava_sa_sistema()
        else:
            opsti_izbori(izbor, brojac, username)
    elif brojac == 3:
        if izbor == '1':
            zakazivanje('', username)
            nazad(brojac, username)
        elif izbor == '8':
            datum = input('\nUnesite datum(yyyy-mm-dd): ')
            recepcioneri.pregled_tretmana(datum)
            nazad(brojac, username)
        elif izbor == '9':
            recepcioneri.izmena_konkretnog_tretmana()
            nazad(brojac, username)
        elif izbor == '2':
            recepcioneri.otkazivanje_tretmana()
            nazad(brojac, username)
        elif izbor == '10':
            recepcioneri.pretraga_zakazanih_tretmana()
            nazad(brojac, username)
        elif izbor == '11':
            odjava_sa_sistema()
        else:
            opsti_izbori(izbor, brojac, '')
    elif brojac == 4:
        if izbor == '8':
            menadzer.pretraga_realizovanih_tretmana()
            nazad(brojac, username)
        elif izbor == '1':
            menadzer.registracija_kozmeticara_recepcionera()
            nazad(brojac, username)
        elif izbor == '9':
            menadzer.kreiranje_novog_tretmana()
            nazad(brojac, username)
        elif izbor == '10':
            menadzer.izmena_kozmetickog_tretmana()
            nazad(brojac, username)
        elif izbor == '11':
            menadzer.brisanje()
            nazad(brojac, username)
        elif izbor == '2':
            menadzer.izvestavanje()
            nazad(brojac, username)
        elif izbor == '12':
            odjava_sa_sistema()
        else:
            opsti_izbori(izbor, brojac, '')


def opsti_izbori(izbor, brojac, username):
    if izbor == '3':
        izlazak_iz_aplikacije()
    elif izbor == '4':
        tabelarni_ispis_tretmana()
        nazad(brojac, username)
    elif izbor == '5':
        pretraga_tretmana_main()
        nazad(brojac, username)
    elif izbor == '6':
        visekriterijumska_pretraga()
        nazad(brojac, username)
    elif izbor == '7':
        tip_tretmana = input('\nUnesite tip tretmana za pretragu (masaza, pedikir, manikir, tretman lica): ')
        prikaz_najpopularnijih_tretmana(tip_tretmana)
        nazad(brojac, username)
    else:
        print('\nNiste nešto dobro uneli, pokušajte ponovo.')
        moj_izbor(brojac, username)


def prijavljivanje():
    username = input('\nKorisničko ime: ')
    lozinka = input('Lozinka: ')
    username_klijent = korisnici.prijavljivanje_korisnika(username, lozinka)
    username_kozmeticar = kozmeticari.prijavljivanje_kozmeticara(username, lozinka)
    username_recepcioner = recepcioneri.prijavljivanje_recepcionera(username, lozinka)
    username_menadzer = menadzer.prijavljivanje_menadzera(username, lozinka)
    return username_klijent, username_kozmeticar, username_recepcioner, username_menadzer


def tabelarni_ispis_tretmana():
    print(tretmani.zaglavlje1())
    print(tretmani.svi_tretmani())


def pretraga_tretmana_main():
    komanda = '0'
    while komanda != 'X':
        komanda = komanda_pretrage_tretmana()
        if komanda == '1':
            pretraga_po_nazivu()
        elif komanda == '2':
            pretraga_po_tipu_tretmana()
        elif komanda == '3':
            pretraga_po_trajanju()
        elif komanda == '4':
            pretraga_po_ceni()


def komanda_pretrage_tretmana():
    meni_za_pretragu_tretmana()
    izbor = input('>> ')
    while izbor.upper() not in ('1', '2', '3', '4', 'X'):
        print('\nUneli ste pogrešnu komandu.\n')
        meni_za_pretragu_tretmana()
        izbor = input(">> ")
    return izbor.upper()


def meni_za_pretragu_tretmana():
    print('\nIzaberite kriterijum po kom želite da izvršite pretragu:')
    print('1 - Prefiksno ili po punom nazivu')
    print('2 - Tipu tretmana')
    print('3 - Dužini trajanja tretmana')
    print('4 - Ceni')
    print('X - Nazad ')


def formatiranje(lista):
    rezultat = ''
    for tretman in lista:
        rezultat += '{0:8}|{1:20}|{2:65}|{3:10}|{4:40}|{5:8}'.format(
            tretman['sifra'],
            tretman['naziv'],
            tretman['opis'],
            tretman['trajanje'],
            tretman['priprema'],
            tretman['cena']) + '\n'
    return rezultat


def pretraga_po_nazivu():
    unos = input('\nUnesite prefiks ili pun naziv tretmana: ')
    lista = tretmani.pretraga_po_nazivu(unos)
    if not lista:
        print('Ne postoji nijedan termin koji počinje unetim podatkom.')
    else:
        print(tretmani.zaglavlje2())
        print(formatiranje(lista))


def pretraga_po_tipu_tretmana():
    unos = input('\nUnesite tip tretmana za pretragu (masaža, pedikir, manikir, tretman lica): ')
    lista = tretmani.pretraga_po_tipu_tretmana(unos)
    if not lista:
        print('Uneli ste nepostojeći tip tretmana. ')
    else:
        print(tretmani.zaglavlje2())
        print(formatiranje(lista))


def pretraga_po_trajanju():
    minimalno = input('\nUnesite minimalnu dužinu trajanja u minutima: ')
    maximalno = input('Unesite maksimalnu dužinu trajanja u minutima: ')
    lista = tretmani.pretraga_po_trajanju(minimalno, maximalno)
    if not lista:
        print('Nažalost ne postoji nijedan termin koji zadovoljava zadate kriterijume.')
    else:
        print(tretmani.zaglavlje2())
        print(formatiranje(lista))


def pretraga_po_ceni():
    minimalno = input('\nUnesite minimalnu cenu: ')
    maximalno = input('Unesite maksimalnu cenu: ')
    lista = tretmani.pretraga_po_ceni(minimalno, maximalno)
    if not lista:
        print('Nažalost ne postoji nijedan tretman koji zadovoljava zadate kriterijume.')
    else:
        print(tretmani.zaglavlje2())
        print(formatiranje(lista))


def visekriterijumska_pretraga():
    print('\nUkoliko želite da preskočite neki kriterijum možete pritisnuti enter!')
    naziv = input('\nUnesite prefiks ili pun naziv tretmana: ').lower()
    tip = input('Unesite tip tretmana za pretragu (masaža, pedikir, manikir, tretman lica): ').lower()
    trajanje1 = input('Unesite minimalnu dužinu trajanja u minutima: ')
    trajanje2 = input('Unesite maksimalnu dužinu trajanja u minutima: ')
    cena1 = input('Unesite minimalnu cenu: ')
    cena2 = input('Unesite maksimalnu cenu: ')
    rezultat = tretmani.visekriterijumska_pretraga(naziv, tip, trajanje1, trajanje2, cena1, cena2)
    if rezultat == '':
        print('\nNažalost ne postoji nijedan tretman koji zadovoljava zadate kriterijume.')
        return 0
    else:
        print(tretmani.zaglavlje2())
        print(tretmani.visekriterijumska_pretraga(naziv, tip, trajanje1, trajanje2, cena1, cena2))
        return tretmani.visekriterijumska_pretraga(naziv, tip, trajanje1, trajanje2, cena1, cena2)


def prikaz_najpopularnijih_tretmana(tip_tretmana):
    rezultat = termini.najpopularniji_tretmani(tip_tretmana)
    if rezultat != '':
        print()
        naslov = 'NAJPOPULARNIJI TRETMANI'
        print(naslov.center(40))
        print('='*40)
        print(termini.zaglavljee())
        print(termini.najpopularniji_tretmani(tip_tretmana))
    else:
        print('Uneli ste nepostojeći tip tretmana.')


def registracija():
    username, lozinka, tel, email, ime, prezime, pol = korisnici.registracija_meni()
    if korisnici.provera_validnosti_podataka_registracije(username, tel, email, pol):
        korisnici.dodavanje_klijenta(username, lozinka, tel, email, ime, prezime, pol)
    else:
        username = ''
    return username


def zakazivanje_meni():
    print('\nIzaberite način pretrage kozmetičkog tretmana:')
    print('1 - Višekriterijumska pretraga')
    print('2 - Unos šifre tretmana')


def zakazivanje(username_klijent, username_recepcioner):
    zakazivanje_meni()
    izbor = input('>> ')
    while izbor.upper() not in ('1', '2'):
        print('\nUneli ste pogrešnu komandu.\n')
        zakazivanje_meni()
        izbor = input(">> ")
    if izbor == '1':
        rezultat = visekriterijumska_pretraga()
        if rezultat == 0:
            zakazivanje(username_klijent, username_recepcioner)
        else:
            sifra = input('Unesite šifru tretmana koji želite da zakažete: ')
            sifra = sifra.upper()
            if sifra in rezultat:
                lista_kozmeticara = kozmeticari.lista_mogucih_kozmeticara(sifra)
                if not lista_kozmeticara:
                    print('Ne postoji kozmetičar koji obavlja taj tretman.')
                    zakazivanje(username_klijent, username_recepcioner)
                else:
                    print('\nDostupni kozmetičari:')
                    print(kozmeticari.ispis_mogucih_kozmeticara(sifra))
                    komanda_izbora_kozmeticara(sifra, username_klijent, username_recepcioner)
            else:
                print('Uneli ste neadekvatnu šifru')
                zakazivanje(username_klijent, username_recepcioner)
    elif izbor == '2':
        sifra = input('\nUnesite šifru tretmana kog želite da zakažete: ')
        sifra = sifra.upper()
        if tretmani.provera_validnosti_sifre(sifra):
            lista_kozmeticara = kozmeticari.lista_mogucih_kozmeticara(sifra)
            if not lista_kozmeticara:
                print('Ne postoji kozmetičar koji obavlja taj tretman.')
                zakazivanje(username_klijent, username_recepcioner)
            else:
                print('\nDostupni kozmetičari:')
                print(kozmeticari.ispis_mogucih_kozmeticara(sifra))
                komanda_izbora_kozmeticara(sifra, username_klijent, username_recepcioner)
        else:
            print('Ne postoji tretman za unetu šifru.')
            zakazivanje(username_klijent, username_recepcioner)


def izbor_kozmeticara_meni():
    print('Da li želite da tretman zakažete kod određenog kozmetičara?')
    print('1 - Da')
    print('2 - Ne')


def komanda_izbora_kozmeticara(sifra, username_klijent, username_recepcioner):
    izbor_kozmeticara_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2'):
        print("\nUneli ste pogrešnu komandu.\n")
        izbor_kozmeticara_meni()
        izbor = input(">> ")
    kozmeticar = ''
    if izbor == '1':
        kozmeticar = input('\nUnesite ime i prezime kozmetičara: ').title()
        while not kozmeticari.provera_kozmeticara(sifra, kozmeticar):
            print('Nevalidan unos.')
            kozmeticar = input('\nUnesite ime i prezime kozmetičara: ').title()
        if username_klijent == '':
            print('\nUspešno ste izabrali kozmetičara!')
            izabran_termin = termini.odabir_datuma_za_odredjenog_kozmeticara(kozmeticar)
            username_klijenta, ime, tel, email, pol = prijava_klijenta_kod_recepcionera()
            if username_klijenta != '' or ime != '':
                datum, pocetak, kraj, klijent, naziv, kozmeticar = termini.zakazivanje(
                    izabran_termin, sifra, username_klijenta, kozmeticar, ime)
                potvrda_zakazivanja(sifra, datum, pocetak, kraj, username_klijenta, klijent, naziv, kozmeticar,
                                    username_recepcioner, ime, tel, email, pol)
        else:
            print('\nUspešno ste izabrali kozmetičara!')
            izabran_termin = termini.odabir_datuma_za_odredjenog_kozmeticara(kozmeticar)
            zakazivanje_za_sebe_ili_drugu_osobu(izabran_termin, sifra, username_klijent, kozmeticar)
    elif izbor == '2':
        lista_kozmeticara = kozmeticari.lista_mogucih_kozmeticara(sifra)
        izabran_termin = termini.odabir_tretmana_za_neodredjenog_kozmeticara(lista_kozmeticara)
        kozmeticar = ''
        if username_klijent != '':
            zakazivanje_za_sebe_ili_drugu_osobu(izabran_termin, sifra, username_klijent, kozmeticar)
        else:
            username_klijenta, ime, tel, email, pol = prijava_klijenta_kod_recepcionera()
            if username_klijenta != '' or ime != '':
                datum, pocetak_tretmana, kraj, klijent, naziv, kozmeticar = termini.zakazivanje(
                    izabran_termin, sifra, username_klijenta, kozmeticar, ime)
                potvrda_zakazivanja(sifra, datum, pocetak_tretmana, kraj, username_klijenta, klijent, naziv, kozmeticar,
                                    username_recepcioner, ime, tel, email, pol)
    return kozmeticar


def zakazivanje_za_sebe_ili_drugu_osobu_meni():
    print('\nZakazujete tretman za:')
    print('1 - Sebe')
    print('2 - Drugu osobu')


def zakazivanje_za_sebe_ili_drugu_osobu(izabran_termin, sifra, username, kozmeticar):
    zakazivanje_za_sebe_ili_drugu_osobu_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2'):
        print("\nUneli ste pogrešnu komandu.\n")
        zakazivanje_za_sebe_ili_drugu_osobu_meni()
        izbor = input(">> ")
    if izbor == '1':
        datum, pocetak_tretmana, kraj, klijent, naziv, kozmeticar = termini.zakazivanje(
            izabran_termin, sifra, username, kozmeticar, ime='')
        potvrda_zakazivanja(sifra, datum, pocetak_tretmana, kraj, username, klijent, naziv, kozmeticar,
                            recepcioner='', ime='', tel='', email='', pol='')
    elif izbor == '2':
        osoba = input('\nUnesite ime i prezime osobe za koju zakazujete tretman: ')
        datum, pocetak_tretmana, kraj, klijent, naziv, kozmeticar = termini.zakazivanje(
            izabran_termin, sifra, username, kozmeticar, ime='')
        klijent = osoba
        potvrda_zakazivanja(sifra, datum, pocetak_tretmana, kraj, username, klijent, naziv, kozmeticar,
                            recepcioner='', ime='', tel='', email='', pol='')


def potvrda_zakazivanja_meni():
    print('\nPotvrdite zakazivanje:')
    print('1 - Potvrđujem')
    print('X - Odustajem')


def potvrda_zakazivanja(sifra, datum, pocetak, kraj, username, klijent, naziv, kozmeticar, recepcioner, ime, tel, email, pol):
    potvrda_zakazivanja_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', 'X'):
        print("\nUneli ste pogrešnu komandu.\n")
        potvrda_zakazivanja_meni()
        izbor = input(">> ")
    if izbor == '1':
        sifra1 = termini.kreiranje_sifre_zakazanog_tretmana()
        if recepcioner == '':
            termini.potvrda_zakazivanja_klijent(sifra1, sifra, datum, pocetak, kraj, username, klijent, naziv, kozmeticar)
        else:
            termini.potvrda_zakazivanja_recepc(sifra1, sifra, datum, pocetak, kraj, recepcioner, klijent, naziv, kozmeticar, ime, tel, email, pol, username)
        print('\nUspešno ste zakazali termin. Hvala na poverenju!')
        dalje_zakazivanje(username, sifra, datum, pocetak, kraj, kozmeticar, naziv, klijent, recepcioner, ime, tel, email, pol)


def dalje_zakazivanja_meni():
    print('\nNastavite zakazivanje:')
    print('1 - Sledećeg tretmana')
    print('2 - Istog tretmana za drugu osobu')
    print('X - Nazad')


def dalje_zakazivanje(username, sifra, datum, pocetak, kraj, kozmeticar, naziv, klijent, recepcioner, ime, tel, email, pol):
    dalje_zakazivanja_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2', 'X'):
        print("\nUneli ste pogrešnu komandu.\n")
        dalje_zakazivanja_meni()
        izbor = input(">> ")
    if izbor == '1':
        tretman, novi_pocetak = termini.zakazivanje_sl_tretmana(datum, pocetak, kraj, kozmeticar, naziv)
        if tretman:
            sifra = termini.sifra_odabranog_tretmana(tretman)
            trajanje = tretmani.vreme_trajanja_tretmana(sifra)
            if trajanje[0] == '60':
                nov_kraj = int(novi_pocetak.split(':')[0]) + 1
                nov_kraj = str(nov_kraj) + ':00'
            else:
                nov_kraj = novi_pocetak.split(':')[0] + ':' + trajanje[0]
            for korisnik in korisnici.ucitavanje_korisnika():
                if korisnik['username'] == username:
                    klijent = korisnik['ime'] + ' ' + korisnik['prezime']
            potvrda_zakazivanja(sifra, datum, novi_pocetak, nov_kraj, username, klijent, tretman, kozmeticar, recepcioner, ime, tel, email, pol)
    elif izbor == '2':
        ime_prezime = input('\nUnesite ime i prezime osobe za koju zakazujete tretman: ')
        lista_kozmeticara = termini.zakazivanje_istog_tretmana(sifra, kozmeticar, datum, pocetak, kraj)
        if lista_kozmeticara:
            izbor_kozmeticara_2(sifra, username, lista_kozmeticara, ime_prezime, datum, pocetak, kraj, naziv, recepcioner, ime, tel, email, pol)


def izbor_kozmeticara_2(sifra, username, lista_kozmeticara, ime_prezime, datum, pocetak, kraj, naziv, recepcioner, ime, tel, email, pol):
    izbor_kozmeticara_meni()
    izbor = input(">> ")
    while izbor.upper() not in ('1', '2', 'X'):
        print("\nUneli ste pogrešnu komandu.\n")
        izbor_kozmeticara_meni()
        izbor = input(">> ")
    if izbor == '1':
        kozmeticar = input('\nUnesite ime i prezime kozmetičara: ').title()
        while not kozmeticari.provera_kozmeticara(sifra, kozmeticar):
            print('Nevalidan unos.')
            kozmeticar = input('\nUnesite ime i prezime kozmetičara: ').title()
        else:
            print('\nUspešno ste izabrali kozmetičara!')
            if not termini.provera_dostupnosti_kozmeticara(lista_kozmeticara, kozmeticar, datum, pocetak, kraj):
                print('\nNažalost izabran kozmetičar nije dostupan.')
            else:
                potvrda_zakazivanja(sifra, datum, pocetak, kraj, username, ime_prezime, naziv, kozmeticar, recepcioner, ime, tel, email, pol)
    elif izbor == '2':
        kozmeticar = ''
        kozmeticar = termini.provera_dostupnosti_kozmeticara(lista_kozmeticara, kozmeticar, datum, pocetak, kraj)
        if not termini.provera_dostupnosti_kozmeticara(lista_kozmeticara, kozmeticar, datum, pocetak, kraj):
            print('\nNažalost nijedan kozmetičar nije dostupan.')
        else:
            potvrda_zakazivanja(sifra, datum, pocetak, kraj, username, ime_prezime, naziv, kozmeticar, recepcioner, ime, tel, email, pol)


def pregled_nerealizovaih_tretmana(username):
    lojalni, lista_lojalnih = menadzer.izvestaj2()
    rezultat = termini.pregled_nerealizovanih_tretmana(username)
    if rezultat:
        if username in lista_lojalnih:
            print('Svaki lojalan klijent ima 10% popusta na svaki zakazan tretman! Vi ste jedan od njih. Hvala!')
        print(termini.zaglavlje())
        print(termini.pregled_nerealizovanih_tretmana(username))
    else:
        print('\nJoš uvek nemate nijedan zakazan tretman.')


def prijava_kljenta_kod_recepcionera_meni():
    print('\nDa li je klijent već registrovan?')
    print('1 - Da')
    print('2 - Ne')
    print('X - Nazad')


def prijava_klijenta_kod_recepcionera():
    username_klijenta = ''
    ime = ''
    tel = ''
    email = ''
    pol = ''
    izbor = '0'
    while izbor.upper() != 'X':
        prijava_kljenta_kod_recepcionera_meni()
        izbor = input(">> ")
        while izbor.upper() not in ('1', '2', 'X'):
            print("\nUneli ste pogrešnu komandu.")
            prijava_kljenta_kod_recepcionera_meni()
            izbor = input(">> ")
        if izbor == '1':
            username_klijenta = input('\nUnesite korisničko ime klijenta: ')
            if korisnici.provera_validnog_username_pri_prijavi(username_klijenta):
                continue
            else:
                print('Uneli ste nepostojeće korisničko ime.')
        elif izbor == '2':
            ime = input('\nUnesite ime i prezime klijenta: ').title()
            tel = input('Unesite kontakt telefon klijenta (samo brojeve): ')
            email = input('Unesite email adresu klijenta: ').lower()
            pol = input('Unesite pol klijenta (muško, žensko): ').lower()
            if korisnici.provera_podataka_prijava_kod_recepcionera(tel, email, pol):
                return username_klijenta, ime, tel, email, pol
    return username_klijenta, ime, tel, email, pol


def klijent_main(username):
    print('\nImate na raspolaganju sledeće mogućnosti: ')
    print('1. - Zakazivanje\n2. - Pregled nerealizovanih tretmana')
    izbori()
    print('8. - Odjava')
    moj_izbor(1, username)


def kozmeticar_main(username):
    print('\nImate na raspolaganju sledeće mogućnosti: ')
    print('1. - Pregled zakazanih tretmana\n2. - Pregled radnog vremena')
    izbori()
    print('8. - Odjava')
    moj_izbor(2, username)


def recepcioner_main(username):
    print('\nImate na raspolaganju sledeće mogućnosti: ')
    print('1. - Zakazivanje tretmana\n2. - Otkazivanje tretmana')
    izbori()
    print('\nDodatne mogućnosti:'
          '\n8. - Pregled tretmana\n9. - Izmena tretmana\n10. - Pretraga zakazanih tretmana\n11. - Odjava')
    moj_izbor(3, username)


def menadzeri_main():
    print('\nImate na raspolaganju sledeće mogućnosti: ')
    print('1. - Registrovanje novih recepcionera i kozmetičara\n2. - Izveštavanje')
    izbori()
    print('\nDodatne mogućnosti:\n8. - Pretraga realizovanih tretmana\n9. - Kreiranje novih tretmana'
          '\n10. - Izmena tretmana\n11. - Brisanje tretmana\n12. - Odjava')
    moj_izbor(4, '')


def nazad(brojac,username):
    korak_unazad = input('\nUnesite "da" ako ste sigurni da želite da se vratite nazad: ').lower()
    if korak_unazad == 'da' and brojac == 0:
        main()
    elif korak_unazad == 'da' and brojac == 1:
        klijent_main(username)
    elif korak_unazad == 'da' and brojac == 2:
        kozmeticar_main(username)
    elif korak_unazad == 'da' and brojac == 3:
        recepcioner_main(username)
    elif korak_unazad == 'da' and brojac == 4:
        menadzeri_main()
    else:
        izlazak_iz_aplikacije()


def odjava_sa_sistema():
    return main()


def izlazak_iz_aplikacije():
    return exit()


if __name__ == '__main__':
    poseban_program.odradjen_ili_zakazan()
    main()
