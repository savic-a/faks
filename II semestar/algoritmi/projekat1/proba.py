from tabla import Tabla
from stablo_igre import Tree, TreeNode
from copy import deepcopy
from hesmapa import HashMap
import time


class Reversi(object):
    __slots__ = '_trenutno_stanje', '_igrac', 'izabran_cvor', 'tree', 'prethodna_tabla', 'mapa'

    def __init__(self):
        self._trenutno_stanje = Tabla()
        self._igrac = '@'
        self.izabran_cvor = None
        self.tree = Tree()
        self.prethodna_tabla = self._trenutno_stanje
        self.mapa = HashMap()

    def igraj(self):
        while True:
            validni_potezi = []

            if self._igrac == 'O':
                validni_potezi = self._trenutno_stanje.svi_validni_potezi(self._trenutno_stanje)
                if not validni_potezi:
                    self._igrac = '@'

            if self._igrac == '@':
                validni_potezi = self._trenutno_stanje.svi_validni_potezi(self._trenutno_stanje, tile='@')
                if not validni_potezi:
                    self._igrac = 'O'
                for x, y in validni_potezi:
                    if self._trenutno_stanje.get_igrac(x, y) == '-':
                        self._trenutno_stanje.set_igrac(x, y, '.')

            print(self._trenutno_stanje)

            crni, beli = self.broj_novcica()
            print('\tCRNI ' + str(crni) + '\t\t\t\tBELI ' + str(beli) + '\n')

            rezultat = self.kraj(crni, beli)
            if rezultat == 1:
                print('Pobedili ste. Čestitke!!!')
                return

            elif rezultat == -1:
                print('Izgubili ste :(')
                return

            # IGRAC
            if self._igrac == '@':
                while True:
                    px = input('Unesite x koordinatu: ')
                    py = input('Unesite y koordinatu: ')
                    if not (px.isdecimal()) or not (py.isdecimal()):
                        print('\nPotez nije validan! Pokušajte ponovo.\n')
                    else:
                        px = int(px)
                        py = int(py)
                        lista = self._trenutno_stanje.validnost(self._trenutno_stanje, '@', px, py)
                        if not lista:
                            print('\nPotez nije validan! Pokušajte ponovo.\n')
                        else:
                            self._trenutno_stanje.set_igrac(px, py, '@')
                            for x, y in lista:
                                self._trenutno_stanje.set_igrac(x, y, '@')
                            for i, j in validni_potezi:
                                if self._trenutno_stanje.get_igrac(i, j) == '.':
                                    self._trenutno_stanje.set_igrac(i, j, '-')
                            self._igrac = 'O'
                            break
            # RACUNAR
            else:
                depth = 3
                self.prethodna_tabla = self._trenutno_stanje
                start = time.time()
                self.tree.root = TreeNode(self._trenutno_stanje, None, None)
                proslo = 0
                alfa_beta, rez = self.alpha_beta(proslo, self.tree.root, depth, -100000000, 100000000, 1)
                self._trenutno_stanje = alfa_beta.data
                end = time.time()
                print(str(depth) + '\tvreme: {}s'.format(round(end - start, 3)))
                print(str(rez) + ' proslo')

                self._igrac = '@'

    @staticmethod
    def okreni(lista, igrac, prethodna_tabla, x, y):
        tabla = deepcopy(prethodna_tabla)
        tabla.set_igrac(x, y, igrac)
        for i, j in lista:
            tabla.set_igrac(i, j, igrac)
        return tabla

    def broj_novcica(self):
        beli = 0
        crni = 0
        for i in range(8):
            for j in range(8):
                if self._trenutno_stanje.get_igrac(i, j) == '@':
                    crni += 1
                elif self._trenutno_stanje.get_igrac(i, j) == 'O':
                    beli += 1
        return crni, beli

    @staticmethod
    def heuristika(tabla):
        ukupno, razlika, komsije, mobilnost, uglovi, stabilnost = 0, 0, 0, 0, 0, 0

        v = [[20, -3, 11, 8, 8, 11, -3, 20],
             [-3, -7, -4, 1, 1, -4, -7, -3],
             [11, -4, 2, 2, 2, 2, -4, 11],
             [8, 1, 2, -3, -3, 2, 1, 8],
             [8, 1, 2, -3, -3, 2, 1, 8],
             [11, -4, 2, 2, 2, 2, -4, 11],
             [-3, -7, -4, 1, 1, -4, -7, -3],
             [20, -3, 11, 8, 8, 11, -3, 20]]
        x1 = [-1, -1, 0, 1, 1, 1, 0, -1]
        y1 = [0, 1, 1, 1, 0, -1, -1, -1]

        # RAZLIKA
        crni, beli, beli_komsija, crni_komsija = 0, 0, 0, 0
        for i in range(8):
            for j in range(8):
                if tabla.get_igrac(i, j) == '@':
                    razlika += v[j][i]
                    crni += 1
                elif tabla.get_igrac(i, j) == 'O':
                    razlika -= v[j][i]
                    beli += 1
                if tabla.get_igrac(i, j) != '-':
                    for k in range(8):
                        x = i + x1[k]
                        y = j + y1[k]
                        if 0 <= x <= 7 and 0 <= y <= 7 and tabla.get_igrac(x, y) == '-':
                            if tabla.get_igrac(i, j) == '@':
                                crni_komsija += 1
                            else:
                                beli_komsija += 1
                            # break
        if crni > beli:
            ukupno = (100 * crni) / (crni + beli)
        elif crni < beli:
            ukupno = -(100 * beli) / (crni + beli)
        else:
            ukupno = 0

        if crni_komsija > beli_komsija:
            komsije = -(100 * crni_komsija) / (crni_komsija + beli_komsija)
        elif crni_komsija < beli_komsija:
            komsije = 100 * beli_komsija / (crni_komsija + beli_komsija)
        else:
            komsije = 0

        # MOBILNOST
        crni = len(tabla.svi_validni_potezi(tabla, '@'))
        beli = len(tabla.svi_validni_potezi(tabla))
        if crni > beli:
            mobilnost = 100 * crni / (crni + beli)
        elif crni < beli:
            mobilnost = -(100 * beli) / (crni + beli)
        else:
            mobilnost = 0

        # UGLOVI
        crni, beli = 0, 0
        if tabla.get_igrac(0, 0) == 'O':
            beli += v[0][0]
        elif tabla.get_igrac(0, 0) == '@':
            crni += v[0][0]
        if tabla.get_igrac(0, 7) == 'O':
            beli += v[7][0]
        elif tabla.get_igrac(0, 7) == '@':
            crni += v[7][0]
        if tabla.get_igrac(7, 0) == 'O':
            beli += v[0][7]
        elif tabla.get_igrac(7, 0) == '@':
            crni += v[0][7]
        if tabla.get_igrac(7, 7) == 'O':
            beli += v[7][7]
        elif tabla.get_igrac(7, 7) == '@':
            crni += v[7][7]
        uglovi = 25 * (crni - beli)

        # STABILNOST
        crni, beli = 0, 0
        if tabla.get_igrac(0, 0) == '-':
            if tabla.get_igrac(0, 1) == 'O':
                beli += v[1][0]
            elif tabla.get_igrac(0, 1) == '@':
                crni += v[1][0]
            if tabla.get_igrac(1, 1) == 'O':
                beli += v[1][1]
            elif tabla.get_igrac(1, 1) == '@':
                crni += v[1][1]
            if tabla.get_igrac(1, 0) == 'O':
                beli += v[0][1]
            elif tabla.get_igrac(1, 0) == '@':
                crni += v[0][1]
        if tabla.get_igrac(0, 7) == '-':
            if tabla.get_igrac(0, 6) == 'O':
                beli += v[6][0]
            elif tabla.get_igrac(0, 6) == '@':
                crni += v[6][0]
            if tabla.get_igrac(1, 6) == 'O':
                beli += v[6][1]
            elif tabla.get_igrac(1, 6) == '@':
                crni += v[6][1]
            if tabla.get_igrac(1, 7) == 'O':
                beli += v[7][1]
            elif tabla.get_igrac(1, 7) == '@':
                crni += v[7][1]
        if tabla.get_igrac(7, 0) == '-':
            if tabla.get_igrac(7, 1) == 'O':
                beli += v[1][7]
            elif tabla.get_igrac(7, 1) == '@':
                crni += v[1][7]
            if tabla.get_igrac(6, 1) == 'O':
                beli += v[1][6]
            elif tabla.get_igrac(6, 1) == '@':
                crni += v[1][6]
            if tabla.get_igrac(6, 0) == 'O':
                beli += v[0][6]
            elif tabla.get_igrac(6, 0) == '@':
                crni += v[0][6]
        if tabla.get_igrac(7, 7) == '-':
            if tabla.get_igrac(6, 7) == 'O':
                beli += v[7][6]
            elif tabla.get_igrac(6, 7) == '@':
                crni += v[7][6]
            if tabla.get_igrac(6, 6) == 'O':
                beli += v[6][6]
            elif tabla.get_igrac(6, 6) == '@':
                crni += v[6][6]
            if tabla.get_igrac(7, 6) == 'O':
                beli += v[6][7]
            elif tabla.get_igrac(7, 6) == '@':
                crni += v[6][7]
        stabilnost = -12.5 * (crni - beli)

        return 10 * ukupno + 801.724 * uglovi + 382.026 * stabilnost + 78.922 * mobilnost + 74.396 * komsije + 10 * razlika

    def kraj(self, crni, beli):
        if len(self._trenutno_stanje.svi_validni_potezi(self._trenutno_stanje, '@')) == 0 and \
                len(self._trenutno_stanje.svi_validni_potezi(self._trenutno_stanje)) == 0:
            # CRNI POBEDNIK
            if crni > beli:
                return 1
            else:
                return -1
        else:
            return 0

    # def alpha_beta(self, proslo, node, depth, alpha, beta, maximazing):
    #     if maximazing:
    #         igrac = 'O'
    #     else:
    #         igrac = '@'
    #
    #     if depth == 0:
    #         return self.heuristika(node.data)
    #
    #     for x in range(8):
    #         for y in range(8):
    #             lista = self._trenutno_stanje.validnost(node.data, igrac, x, y)
    #             if lista:
    #                 test = self.okreni(lista, igrac, node.data, x, y)
    #                 dete = TreeNode(test, x, y)
    #                 node.add_child(dete)
    #
    #     if node.is_list():
    #         return self.heuristika(node.data)
    #
    #     if maximazing:
    #         for child in node.children:
    #             prosli_alfa = alpha
    #             if child.data in self.mapa:
    #                 alpha = self.mapa[child.data]
    #                 proslo += 1
    #             else:
    #                 alpha = max(alpha, self.alpha_beta(proslo, child, depth - 1, alpha, beta, 0))
    #                 if child.data in self.mapa:
    #                     alpha = self.mapa[child.data]
    #                     proslo += 1
    #                 else:
    #                     self.mapa[child.data] = alpha
    #             if beta <= alpha:
    #                 break
    #             if depth == 4:
    #                 if prosli_alfa < alpha:
    #                     self.izabran_cvor = child
    #         if depth == 4:
    #             return self.izabran_cvor, proslo
    #         return alpha
    #
    #     else:
    #         for child in node.children:
    #             if child.data in self.mapa:
    #                 beta = self.mapa[child.data]
    #                 proslo += 1
    #             else:
    #                 beta = min(beta, self.alpha_beta(proslo, child, depth - 1, alpha, beta, 1))
    #                 if child.data in self.mapa:
    #                     beta = self.mapa[child.data]
    #                     proslo += 1
    #                 else:
    #                     self.mapa[child.data] = beta
    #             if beta <= alpha:
    #                 break
    #         return beta

    def alpha_beta(self, proslo, node, depth, alpha, beta, maximazing):
        if maximazing:
            igrac = 'O'
        else:
            igrac = '@'

        if depth == 0:
            return self.heuristika(node.data)

        for x in range(8):
            for y in range(8):
                lista = self._trenutno_stanje.validnost(node.data, igrac, x, y)
                if lista:
                    test = self.okreni(lista, igrac, node.data, x, y)
                    dete = TreeNode(test, x, y)
                    node.add_child(dete)

        if node.is_list():
            return self.heuristika(node.data)

        if maximazing:
            for child in node.children:
                kljuc = ''
                prosli_alfa = alpha
                for i in range(8):
                    for j in range(8):
                        if child.data.get_igrac(i, j) == '@':
                            kljuc = kljuc + '@'
                        elif child.data.get_igrac(i, j) == 'O':
                            kljuc = kljuc + 'O'
                        else:
                            kljuc = kljuc + 'X'
                if kljuc in self.mapa:
                    vrednost = self.mapa[kljuc]
                    proslo += 1
                    alpha = vrednost
                else:
                    alpha = max(alpha, self.alpha_beta(proslo, child, depth - 1, alpha, beta, 0))
                    self.mapa[kljuc] = alpha
                if beta <= alpha:
                    break
                if depth == 3:
                    if prosli_alfa < alpha:
                        self.izabran_cvor = child
            if depth == 3:
                return self.izabran_cvor, proslo
            return alpha

        else:
            for child in node.children:
                kljuc = ''
                for i in range(8):
                    for j in range(8):
                        if child.data.get_igrac(i, j) == '@':
                            kljuc = kljuc + '@'
                        elif child.data.get_igrac(i, j) == 'O':
                            kljuc = kljuc + 'O'
                        else:
                            kljuc = kljuc + 'X'
                if kljuc in self.mapa:
                    vrednost = self.mapa[kljuc]
                    beta = vrednost
                    proslo += 1
                else:
                    beta = min(beta, self.alpha_beta(proslo, child, depth - 1, alpha, beta, 1))
                    self.mapa[kljuc] = beta
                if beta <= alpha:
                    break
            return beta
