from nans_lib_2 import *
import numpy as np
import matplotlib.pyplot as plt

ddht = lambda *argv: -argv[1] + np.cos(4/3*argv[0]) + 3
r = 15

# a) h'(x) = 1
ht0 = np.array([0, 1])
t0 = 5
tb = 20
h = (tb - t0)/10000

vrk4, _ = rk4N(t0, tb, h, ht0, ddht)
v = r**2 * np.pi * vrk4[-1]

print('Kolicina vode u 20. sekundi je:', np.round(v, 4))

# b) h'(x) = 1, h(x) = 5, t = ?
ht0 = np.array([5, 1])
t0 = 10
t_max = 20
h = (t_max - t0)/10000

fx, _ = rk4N(t0, t_max, h, ht0, ddht)

x = np.arange(t0, t_max+h, h)
p = least_squares_regression(x, fx, 7)

x = np.linspace(t0, t_max, 100)
px = np.polyval(p, x)
plt.plot(x, px, 'blue')

d = np.polyder(p)
pf = lambda x: np.polyval(d, x)
max, _ = zeroFalsePosition(pf, 15, 17)
f_max = np.polyval(p, max)
print('U trenutku ' + str(np.round(max, 4)) + 's visina vode je ' + str(np.round(f_max, 4)) + 'cm')
plt.scatter(max, f_max, c='red')

plt.show()
