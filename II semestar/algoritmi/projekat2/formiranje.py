from graf import *
from trie import Trie


def kreiranje_grana_grafa(E):
    g = Graph()
    V = set()
    for e in E:
        V.add(e[0])
        V.add(e[1])
    vertices = {}
    for v in V:
        vertices[v] = g.insert_vertex(v)
    for e in E:
        src = e[0]
        dest = e[1]
        element = e[2]
        g.insert_edge(vertices[src], vertices[dest], element)
    return g


def formiranje_grafa():
    podaci_grana = []
    E = []
    fajl = open('calls.txt', 'r')
    for linija in fajl.readlines():
        vrednost = linija.split(', ')
        broj = vrednost[0]
        broj1 = broj.replace(' ', '').replace('-', '')
        broj = vrednost[1]
        broj2 = broj.replace(' ', '').replace('-', '')
        datum_vreme = vrednost[2].split(' ')
        datum = datum_vreme[0]
        vreme = datum_vreme[1]
        trajanje = vrednost[3][:-1]
        podaci_grana.extend([datum, vreme, trajanje])
        E.extend([[broj1, broj2, podaci_grana]])
        podaci_grana = []
    return kreiranje_grana_grafa(E)


def formiranje_trie():
    t_brojevi, t_imena, t_prezimena = Trie(), Trie(), Trie()
    i = 1
    fajl = open('phones.txt', 'r')
    for linija in fajl.readlines()[1:]:
        vrednost = linija.split(',')
        ime_prezime = vrednost[0].split(' ')
        ime = ime_prezime[0]
        prezime = ime_prezime[1]
        broj = vrednost[1].replace(' ', '').replace('-', '')[:-1]
        t_imena.insert(ime, prezime, broj)
        t_prezimena.insert(prezime, ime, broj)
        t_brojevi.insert(broj, ime, prezime)
        i += 1
    return t_imena, t_prezimena, t_brojevi