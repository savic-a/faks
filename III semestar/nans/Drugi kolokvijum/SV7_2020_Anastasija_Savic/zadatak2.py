from nans_lib_2 import *
import numpy as np
import matplotlib.pyplot as plt


left = lambda *argv: -np.cos(argv[0] - 2) * ddf(argv[1]) + f(4/3*argv[1])
right = lambda *argv: np.log(5/4*argv[0]) - np.sin(13)

# a) grafik kretanja cene, f(1)=137, f(5)=432
x1 = 1
fx1 = 137
x2 = 5
fx2 = 432
h = (x2 - x1) / 100

x = np.arange(x1, x2+h, h)
fx = finiteDifference(left, right, x1, fx1, x2, fx2, h)
plt.plot(x, fx, 'cyan')

# b) racunanje prometa za petak
p = least_squares_regression(x, fx, 7)
x = np.linspace(x1, x2, 100)
px = np.polyval(p, x)
plt.plot(x, px, 'pink')

x4 = 4
fx4 = np.polyval(p, x4)
friday_price = fx4

V = np.cos(friday_price)/3 * friday_price**3 * np.sin(4+2)
print('Promet za petak je:', np.round(V, 4))

# c) momenti u kojima je cena 700$
plt.plot([1, 5], [700, 700], 'black')

pf = lambda x: np.polyval(p, x) - 700

x1, _ = zeroFalsePosition(pf, 3, 4)
print('moment1 =', np.round(x1, 4))
plt.scatter(x1, np.polyval(p, x1), c='red')
x2, _ = zeroFalsePosition(pf, 4, 5)
print('moment2 =', np.round(x2, 4))
plt.scatter(x2, np.polyval(p, x2), c='red')

plt.show()
