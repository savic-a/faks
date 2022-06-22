import numpy as np
import matplotlib.pyplot as plt
import sympy as sym
from NANS_lib import *


if __name__ == '__main__':
    f = lambda x: np.sin(x**2-2)
    g = lambda x: x*np.cos(x)

    # a) crtanje funkcija
    a = -3
    b = 1
    plt.plot([a, b], [0, 0], 'black')
    x = np.linspace(a, b, 100)
    fx = f(x)
    gx = g(x)
    plt.plot(x, fx, 'red', x, gx, 'yellow')

    # b) nule funkcije f
    zero_f1, _ = zeroFalsePosition(f, -3, -2.5)
    plt.scatter(zero_f1, f(zero_f1), c='g')
    zero_f2, _ = zeroFalsePosition(f, -2.5, -2)
    plt.scatter(zero_f2, f(zero_f2), c='g')
    zero_f3, _ = zeroFalsePosition(f, -1.5, -1)
    plt.scatter(zero_f3, f(zero_f3), c='g')

    # c) preseci funkicija f i g
    h = lambda x: f(x) - g(x)
    zero_h1, _ = zeroFalsePosition(h, -2.5, -1.5)
    plt.scatter(zero_h1, f(zero_h1), c='b')
    zero_h2, _ = zeroFalsePosition(h, -1.5, -1)
    plt.scatter(zero_h2, f(zero_h2), c='b')

    # d) max funkcije f i min funkcije g
    x_sym = sym.Symbol('x')

    df_f_sym = sym.diff(sym.sin(x_sym**2-2))
    df_f = sym.lambdify(x_sym, df_f_sym, 'numpy')
    max, _ = zeroFalsePosition(df_f, -2, -1.5)
    plt.scatter(max, f(max), c='m')
    print('(x_max, y_max) = (' + str(np.round(max, 4)) + ', ' + str(np.round(f(max), 4)) + ')')
    

    df_g_sym = sym.diff(x_sym*sym.cos(x_sym))
    df_g = sym.lambdify(x_sym, df_g_sym, 'numpy')
    min, _ = zeroFalsePosition(df_g, -1, -0.5)
    plt.scatter(min, g(min), c='m')
    print('(x_min, y_min) = (' + str(np.round(min, 4)) + ', ' + str(np.round(g(min), 4)) + ')')

    # e) presek funkcija sa pravom x=pi/6
    plt.plot([np.pi/6, np.pi/6], [a, b], 'black')
    
    plt.scatter(np.pi/6, f(np.pi/6), c='c')
    print('(x1, y1) = (' + str(np.round(np.pi/6, 4)) + ', ' + str(np.round(f(np.pi/6), 4)) + ')')

    plt.scatter(np.pi/6, g(np.pi/6), c='c')
    print('(x1, y1) = (' + str(np.round(np.pi/6, 4)) + ', ' + str(np.round(g(np.pi/6), 4)) + ')')
    plt.show()