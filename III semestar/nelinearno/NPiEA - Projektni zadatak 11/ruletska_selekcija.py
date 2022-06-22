import numpy as np
from ann_criterion import optimality_criterion


def inicijalna_populacija(length, min, max, pop_size):
    # pravimo inicijalnu poplaciju dimenzije length, velicine pop_size
    lista = []
    for i in range(pop_size):
        w = list(np.random.uniform(min, max, length))
        lista.append(w)

    return lista


def rank(parents):
    # rangiranje inicijalne populacije
    y = list(map(optimality_criterion, parents))  # pozivamo funkciju koja izracunava vrijednost y
    ranked = sorted(list(zip(parents, y)), key=lambda c: c[1])  # sortiramo listu i pravimo tuple
    return list(zip(*ranked))


def ruletska_selekcija(parents):
    parovi = []

    # skoro uvijek ce biti djeljivo sa 2
    if len(parents) % 2 == 0:
        range_num = len(parents) // 2
    else:
        range_num = (len(parents) + 1) // 2

    for i in range(range_num):  # broj uparivanja je duplo manji od kardinalnosti skupa populacije
        parents_random = []
        for i in range(len(parents)):
            parents_random.append((len(parents) - i) * np.random.uniform(0, 1))

        # trazimo indekse najboljih u populaciji
        if parents_random[0] > parents_random[1]:
            index_max1 = 0
            index_max2 = 1
        else:
            index_max1 = 1
            index_max2 = 0

        # idemo od 2 jer smo gore vec provjerili prva dva
        for i in range(2, len(parents)):
            if parents_random[i] > parents_random[index_max1]:
                index_max2 = index_max1
                index_max1 = i
            elif parents_random[i] > parents_random[index_max2]:
                index_max2 = i
        parovi.append([parents[index_max1], parents[index_max2]])

    return parovi


if __name__ == '__main__':
    # print([[random.uniform(-10, 10) for j in range(3)] for i in range(10)])
    # print(inicjalna_populacija(60, -10, 10, 100))
    pop = inicijalna_populacija(60, -10, 10, 200)
    rangirana_pop = rank(pop)
    print(rangirana_pop)
    pairs = (ruletska_selekcija(rangirana_pop))

    print(pairs)
    print("Duzina: ", len(pairs))
