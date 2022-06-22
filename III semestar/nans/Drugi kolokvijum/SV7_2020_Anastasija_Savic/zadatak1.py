from nans_lib_2 import *
import numpy as np
import matplotlib.pyplot as plt

f = lambda x: np.sqrt(1 - (np.abs(x) - 1)**2) + np.pi
g = lambda x: np.arccos(1 - np.abs(x))

# a) prikaz funkcija f i g
a = -2
b = 2

x = np.linspace(a, b, 100)
fx = f(x)
gx = g(x)
plt.plot(x, fx, 'red')
plt.plot(x, gx, 'red')

# b) prikaz funkcije h
h = lambda x: x + 2
hx = h(x)
plt.plot(x, hx, 'black')

# c) racunanje povrsina p1 i p2
section1 = lambda x: g(x) - h(x)
x1, _ = zeroBisection(section1, -1, 0)

section2 = lambda x: f(x) - h(x)
x2, _ = zeroBisection(section2, 1, 2)

p11 = lambda x: np.abs(g(x) - f(x))
P11 = integrate_simpson(p11, a, x1, 100)
p12 = lambda x: np.abs(h(x) - f(x))
P12 = integrate_simpson(p12, x1, x2, 100)

p21 = lambda x: np.abs(g(x) - h(x))
P21 = integrate_simpson(p21, x1, x2, 100)
p22 = lambda x: np.abs(g(x) - f(x))
P22 = integrate_simpson(p22, x2, b, 100)

p1 = P11 + P12
p2 = P21 + P22

print('p1 =', np.round(p1, 4))
print('p2 =', np.round(p2, 4))

# d) racunanje zapremine
plt.plot([a, b], [np.abs(p1-p2), np.abs(p1-p2)], 'blue')

y = lambda x: np.abs(p1-p2)
fy_sqr = lambda x: np.abs(f(x) - y(x)) ** 2

V = np.pi * integrate_simpson(fy_sqr, a, b, 100)

print('V =', np.round(V, 4))

plt.show()
