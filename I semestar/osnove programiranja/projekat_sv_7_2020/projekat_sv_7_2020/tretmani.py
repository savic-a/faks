def ucitavanje_tretmana():
    lista_tretmana = []
    for lines in open('tretmani.txt', encoding='utf-8').readlines():
        sifra, naziv, opis, trajanje, priprema, cena = lines.strip().split('|')
        tretman = {
            'sifra': sifra,
            'naziv': naziv,
            'opis': opis,
            'trajanje': trajanje,
            'priprema': priprema,
            'cena': cena
        }
        lista_tretmana.append(tretman)
    return lista_tretmana

def zaglavlje1():
    return \
      '\nŠifra   |Naziv tretmana           |Trajanje    |Cena\n' \
      '=========================================================='

def zaglavlje2():
    return ('{0:9}|{1:20}|{2:65}|{3:10}|{4:40}|{5:8}'.format(
        '\nŠifra', 'Naziv tretmana', 'Opis', 'Trajanje', 'Priprema', 'Cena')) + '\n' +\
        '='*155

def svi_tretmani():
    rezultat = ''
    for tretman in ucitavanje_tretmana():
        rezultat += '{0:8}|{1:25}|{2:12}|{3:8}'.format(
           tretman['sifra'],
           tretman['naziv'],
           tretman['trajanje'],
           tretman['cena']) + '\n'
    return rezultat

def pretraga_po_nazivu(unos):
    unos = unos.lower()
    lista = []
    for tretman in ucitavanje_tretmana():
        if unos in tretman['naziv'][:len(unos)]:
            lista.append(tretman)
    return lista

def pretraga_po_tipu_tretmana(unos):
    unos = unos.lower()
    lista = []
    for tretman in ucitavanje_tretmana():
        if unos in tretman['naziv'] or unos == '':
            lista.append(tretman)
        elif unos == 'tretman lica' or unos == '':
            if 'lica' in tretman['naziv']:
                lista.append(tretman)
        elif unos == 'manikir' and 'masaza' not in tretman['naziv'] and 'lica' not in tretman['naziv'] \
                and 'pedikir' not in tretman['naziv'] or unos == '':
            lista.append(tretman)
    return lista

def pretraga_po_trajanju(minimalno, maximalno):
    lista = []
    for tretman in ucitavanje_tretmana():
        if (minimalno == '' or int(tretman['trajanje']) >= int(minimalno)) \
                and (maximalno == '' or int(tretman['trajanje']) <= int(maximalno)):
            lista.append(tretman)
    return lista

def pretraga_po_ceni(minimalno, maximalno):
    lista = []
    for tretman in ucitavanje_tretmana():
        if (minimalno == '' or int(tretman['cena']) >= int(minimalno)) \
                and (maximalno == '' or int(tretman['cena']) <= int(maximalno)):
            lista.append(tretman)
    return lista

def visekriterijumska_pretraga(naziv, tip, trajanje1, trajanje2, cena1, cena2):
    lista1 = pretraga_po_nazivu(naziv)
    lista2 = pretraga_po_tipu_tretmana(tip)
    lista3 = pretraga_po_trajanju(trajanje1, trajanje2)
    lista4 = pretraga_po_ceni(cena1, cena2)
    lista_rezultat = [x for x in lista1 if x in lista2 and x in lista3 and x in lista4]
    rezultat = ''
    for tretman in lista_rezultat:
        rezultat += '{0:8}|{1:20}|{2:65}|{3:10}|{4:40}|{5:8}'.format(
            tretman['sifra'],
            tretman['naziv'],
            tretman['opis'],
            tretman['trajanje'],
            tretman['priprema'],
            tretman['cena']) + '\n'
    return rezultat

def provera_validnosti_pri_zakazivanju(odabrani_tretman):
    for tretman in ucitavanje_tretmana():
        if tretman['naziv'] == odabrani_tretman or tretman['sifra'] == odabrani_tretman:
            return tretman['naziv']
    return False

def vreme_trajanja_tretmana(sifra):
    for tretman in ucitavanje_tretmana():
        if sifra == tretman['sifra']:
            return tretman['trajanje'], tretman['naziv']

def trazenje_tipa_zeljenog_tretmana(sifra):
    for tretman in ucitavanje_tretmana():
        if tretman['sifra'] == sifra:
            if 'masaza' in tretman['naziv']:
                return 'masaza'
            elif 'lica' in tretman['naziv']:
                return 'tretman lica'
            elif 'pedikir' in tretman['naziv']:
                return 'pedikir'
            else:
                return 'manikir'

def provera_validnosti_sifre(sifra):
    for tretman in ucitavanje_tretmana():
        if sifra == tretman['sifra']:
            return True
    return False

def trazenje_sifre(naziv):
    for tretman in ucitavanje_tretmana():
        if naziv == tretman['naziv']:
            return tretman['sifra']
    return False




