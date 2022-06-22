import numpy as np
import matplotlib.pyplot as plt
from NANS_lib import *

if __name__ == '__main__':
    f = lambda x: np.sqrt(2)*np.sin(2*x) + np.cos(-x/3)
    a = -np.pi/2
    b = 4*np.pi/3

    # a) crtanje funkcije
    x = np.linspace(a, b, 100)
    fx = f(x)
    plt.plot(x, fx, 'orange')

    # b) interpolacija poliomom 4. stepena
    x1 = np.linspace(a, b, 5) 
    plt.scatter(x1, f(x1), c='y')

    # c) aproksimacija funkcije polinomom 4. stepena - Lagranz
    x1 = np.linspace(a, b, 5)
    p1 = lagrange_interpolation(x1, f(x1))
    px = np.polyval(p1, x)
    plt.plot(x, px, 'red')
    print('p1(x) =', ' '.join('{0:+5.2f}x^{1:1d}'.format(coef[1], p1.shape[0]-coef[0]-1) for coef in enumerate(p1)))

    # d) aproksimacija funkcije polinomom 3. stepena
    p2 = least_squares_regression(x, fx, 3)
    px = np.polyval(p2, x)
    plt.plot(x, px, 'brown')
    print('p2(x) =', ' '.join('{0:+5.2f}x^{1:1d}'.format(coef[1], p2.shape[0]-coef[0]-1) for coef in enumerate(p2)))

    # e) presečne tačke polinoma 4. stepena i polinoma 3. stepena
    h = lambda x: np.polyval(p1, x) - np.polyval(p2, x)
    zero1, _ = zeroFalsePosition(h, -1.5, -1)
    f_zero1 = np.polyval(p2, zero1)
    plt.scatter(zero1, f_zero1, c='g')
    print('(x1, y1) = (' + str(np.round(zero1, 4)) + ', ' + str(np.round(f_zero1, 4)) + ')')

    zero2, _ = zeroFalsePosition(h, 0, 1)
    f_zero2 = np.polyval(p2, zero2)
    plt.scatter(zero2, f_zero2, c='g')
    print('(x2, y2) = (' + str(np.round(zero2, 4)) + ', ' + str(np.round(f_zero2, 4)) + ')')

    zero3, _ = zeroFalsePosition(h, 2, 3)
    f_zero3 = np.polyval(p2, zero3)
    plt.scatter(zero3, f_zero3, c='g')
    print('(x3, y3) = (' + str(np.round(zero3, 4)) + ', ' + str(np.round(f_zero3, 4)) + ')')
    
    plt.show()