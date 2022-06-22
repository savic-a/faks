file = open('mountains.txt', 'r')
sadrzaj = file.readlines()


def tabelarni_ispis():
    print('%-30s%-20s%-20s%-30s%-20s' % ('Vrh', 'Visina[m]', 'Visina[ft]', 'Planinski venac', 'Država'))
    print('='*120)
    for lines in sadrzaj:
        values = lines.split('|')
        print('%-30s%-20s%-20s%-30s%-20s' % (values[0], values[1], values[2], values[3], values[4]))


def ispis_vrhova_za_drzavu(drzava):
    drzava_veliko = drzava.capitalize()
    print('%-30s%-20s%-20s%-30s%-20s' % ('Vrh', 'Visina[m]', 'Visina[ft]', 'Planinski venac', 'Država'))
    print('=' * 120)
    for lines in sadrzaj:
        values = lines.split('|')
        if drzava_veliko in values[4]:
            print('%-30s%-20s%-20s%-30s%-20s' % ((values[0]), values[1], values[2], values[3], values[4]))
        elif drzava.upper() in values[4]:
            print('%-30s%-20s%-20s%-30s%-20s' % ((values[0]), values[1], values[2], values[3], values[4]))


def ispis_vrhova_za_string(string):
    string = string.capitalize()
    print('%-30s%-20s%-20s%-30s%-20s' % ('Vrh', 'Visina[m]', 'Visina[ft]', 'Planinski venac', 'Država'))
    print('=' * 120)
    for lines in sadrzaj:
        values = lines.split('|')
        if string in values[0][:len(string)]:
            print('%-30s%-20s%-20s%-30s%-20s' % (values[0], values[1], values[2], values[3], values[4]))


def upis_vrhova_u_fajl():
    vrhovi_lista = []
    file1 = open('vrhovi.txt', 'w')
    for lines in sadrzaj:
        values = lines.split('|')
        if 7700 < int(values[1]) < 8100 and '/' in values[4]:
            file1.write(values[0] + '|' + values[1] + '|' + values[2] + '|' + values[3] + '|' + values[4])
            values[4] = values[4].replace('\n', '')
            vrhovi_lista.append(values)
    file1.close()

    return vrhovi_lista


def ispis_planinskih_venaca():
    print('PLANINSKI VENCI')
    lista_venaca = []
    for lines in sadrzaj:
        values = lines.split('|')
        lista_venaca.append(values[3])
        lista_venaca = list(dict.fromkeys(lista_venaca))
    venac = ''
    for ele in lista_venaca:
        venac += ele + '\n'
    print(venac)


def lista_za_drzavu_visinu(drzava, visina):
    drzava = drzava.capitalize()
    lista_vrhova = []
    for lines in sadrzaj:
        values = lines.split('|')
        if drzava in values[4] and int(values[1]) > visina:
            lista_vrhova.append(values)
        elif drzava.upper() in values[4] and int(values[1]) > visina:
            lista_vrhova.append(values)

    return lista_vrhova


def ispis_vrhova_za_drzavu_visinu(lista_vrhova):
    print('%-30s%-20s%-20s%-30s%-20s' % ('Vrh', 'Visina[m]', 'Visina[ft]', 'Planinski venac', 'Država'))
    print('=' * 120)
    for elem in lista_vrhova:
        print('%-30s%-20s%-20s%-30s%-20s' % (elem[0], elem[1], elem[2], elem[3], elem[4]))


def broj_vrhova():
    print('%-10s%-10s' % ('Država', 'Broj vrhova'))
    print('='*21)
    drzava_lista = []
    for lines in sadrzaj:
        values = lines.split('|')
        values[4] = values[4].replace('\n', '')
        drzava_lista.append(values[4])

    for element in drzava_lista:
        if '/' in element:
            elements = element.split('/')
            drzava_lista.remove(element)
            drzava_lista = drzava_lista + elements

    from collections import Counter
    cnt = Counter()
    for elem in drzava_lista:
        cnt[elem] += 1

    for elements in cnt:
        print('%-15s%-15s' % (elements, cnt[elements]))


def formiranje_liste_venaca():
    lista_venaca = []
    for lines in sadrzaj:
        values = lines.split('|')
        if '/' in values[4]:
            lista_venaca.append(values[3])

    return lista_venaca


def odredjivanje_najbrojnijeg_venca(lista_venaca):
    brojac = 0
    num = lista_venaca[0]
    for i in lista_venaca:
        trenutan_br = lista_venaca.count(i)
        if trenutan_br > brojac:
            brojac = trenutan_br
            num = i
    print('Planinski venac sa najviše "graničnih" vrhova je', num, 'sa', brojac, 'vrhova.')


def meni():
    while True:
        print('\nDOBRODOŠLI U MENI!')
        print('Odaberite opciju:')
        print('1. Tabelarni prikaz svih planinskih vrhova')
        print('2. Ispisivanje planinskih vrhova za unetu državu')
        print('3. Ispisivanje planinskih vrhova za uneti string')
        print('4. Pronalaženje planinskih vrhova koji se nalaze na granici 2 države, viših od 7700m, a nižih od 8100m')
        print('5. Ispisivanje svih planinskih venaca')
        print('6. Pronalaženje vrhova viših od zadate visine za zadatu državu')
        print('7. Tabelarni prikaz broja vrhova iznad 6000m po državi')
        print('8. Ispis planinskog venca koji ima najviše "graničnih" vrhova')
        print('0. IZLAZ\n')
        izbor = int(input('Unesite redni broj operacije, koju želite da izvršite:\n'))
        if izbor == 1:
            tabelarni_ispis()
        elif izbor == 2:
            ispis_vrhova_za_drzavu(input('Unesite željenu državu: '))
        elif izbor == 3:
            ispis_vrhova_za_string(input('Unesite string za pretragu vrha: '))
        elif izbor == 4:
            print(upis_vrhova_u_fajl())
        elif izbor == 5:
            ispis_planinskih_venaca()
        elif izbor == 6:
            ispis_vrhova_za_drzavu_visinu(lista_za_drzavu_visinu(input('Unesite željenu državu: '), eval(input('Unesite visinu u metrima: '))))
        elif izbor == 7:
            broj_vrhova()
        elif izbor == 8:
            odredjivanje_najbrojnijeg_venca(formiranje_liste_venaca())
        elif izbor == 0:
            break


if __name__ == '__main__':
    meni()
