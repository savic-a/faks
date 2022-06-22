class Tabla(object):

    def __init__(self):
        self._tabla = []
        for i in range(0, 8):
            self._tabla.append(8*['-'])
        self._tabla[3][3] = '@'
        self._tabla[4][4] = '@'
        self._tabla[3][4] = 'O'
        self._tabla[4][3] = 'O'

    def get_igrac(self, i, j):
        return self._tabla[j][i]

    def set_igrac(self, i, j, igrac):
        self._tabla[j][i] = igrac

    def get_tabla(self):
        return self._tabla

    def __str__(self):
        ret = "\n\n    0   1   2   3   4   5   6   7\n"
        for j in range(8):
            ret += str(j) + " |"
            for i in range(8):
                if self._tabla[j][i] == '-':
                    ret += "   |"
                else:
                    ret += " %s |" % self._tabla[j][i]
            ret += "\n"
        return ret

    @staticmethod
    def svi_validni_potezi(tabla, tile='O'):
        validni_potezi = []
        for x in range(8):
            for y in range(8):
                if tabla.validnost(tabla, tile, x, y):
                    validni_potezi.append([x, y])
        return validni_potezi

    @staticmethod
    def validnost(tabla, igrac, x, y):
        if not (0 <= x <= 7 and 0 <= y <= 7) or tabla.get_igrac(x, y) != '-' and tabla.get_igrac(x, y) != '.':
            return False

        tabla.set_igrac(x, y, igrac)
        if igrac == '@':
            drugi_igrac = 'O'
        else:
            drugi_igrac = '@'

        za_okretanje = []
        xpravac = [-1, -1, 0, 1, 1, 1, 0, -1]
        ypravac = [0, 1, 1, 1, 0, -1, -1, -1]

        for i in range(8):
            x1, y1 = x, y
            x1 += xpravac[i]
            y1 += ypravac[i]
            if (0 <= x1 <= 7 and 0 <= y1 <= 7) and tabla.get_igrac(x1, y1) == drugi_igrac:
                x1 += xpravac[i]
                y1 += ypravac[i]
                if not (0 <= x1 <= 7 and 0 <= y1 <= 7):
                    continue
                while tabla.get_igrac(x1, y1) == drugi_igrac:
                    x1 += xpravac[i]
                    y1 += ypravac[i]
                    if not (0 <= x1 <= 7 and 0 <= y1 <= 7):
                        break
                if not (0 <= x1 <= 7 and 0 <= y1 <= 7):
                    continue
                elif tabla.get_igrac(x1, y1) == igrac:
                    while True:
                        x1 -= xpravac[i]
                        y1 -= ypravac[i]
                        if x1 == x and y1 == y:
                            break
                        za_okretanje.append([x1, y1])

        tabla.set_igrac(x, y, '-')
        if len(za_okretanje) == 0:
            return False
        return za_okretanje
