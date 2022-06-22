import numpy as np
from NANS_lib import *

if __name__ == '__main__':
    x2 = 1.7
    x1 = x2 - 1.2
    A = np.array([[1, x1, x1**2, x1**3], [1, 4*x1-2*x2, (4*x1-2*x2)**2, (4*x1-2*x2)**3], [1, 2*x1, (2*x1)**2, (2*x1)**3], [1, x2, x2**2, x2**3]])
    b = np.array([5.625, -0.512, x2 + 14.3, 44.469])
    a0, a1, a2, a3 = gauss(A, b)
    if round(np.sqrt(a0) - 2/5*a1 - a2 + a3**2) == 1:
        print('Tacno!!!')
    else:
        print('Nije tacno :(')