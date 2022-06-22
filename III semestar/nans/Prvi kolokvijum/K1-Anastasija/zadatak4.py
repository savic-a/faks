import numpy as np

def fz4a(A, B):
    row_a, col_a = np.shape(A)
    row_b, col_b = np.shape(B)
    M = np.zeros((row_b, col_b))
    result, counter = 0, 0
    list_result = []

    row_sub = row_b - 1
    col_sub = col_b - 1

    for i in range(row_a-row_sub):
        for j in range(col_a-col_sub):
            M = A[i:i+row_b, j:j+col_b]  # podmatrica M
            
            for k in range(row_b):
                for l in range(row_b):
                    result += M[k, l] * B[k, l]   # jedinicni proizvod     
            list_result.append(result)
            result = 0

    row, col = row_a-row_b+1, col_a-col_b+1
    matrix = np.zeros((row, col))
    for i in range(row):
        for j in range(col):
            matrix[i, j] = list_result[counter]   # ubacivanje proizvoda u krajnju matricu
            counter += 1

    return matrix


def fz4b(C):
    rows, cols = np.shape(C)

    # dijagonale
    diags = [C[::-1,:].diagonal(i) for i in range(-rows+1, cols)]  # sporedne
    diags.extend(C.diagonal(i) for i in range(-rows+1, cols))

    for list in diags:
        if len(list) > 2:
            for i in range(len(list)-2):
                if list[i] == list[i+1] == list[i+2]:
                    return True
        else:
            continue
    
    # redovi
    for i in range(rows):
        elem = C[i, 0]
        for j in range(cols-2):
            if elem == C[i, j+1] == C[i, j+2]:
                return True
            elem = C[i, j+1]

    # kolone
    for i in range(cols):
        elem = C[0, i]
        for j in range(rows-2):
            if elem == C[j+1, i] == C[j+2, i]:
                return True
            elem = C[j+1, i]

    return False   


if __name__ == '__main__':
    A = np.array([[1, 2, 3], [0, 3, 7], [4, 1, 2]])
    B = np.array([[4, 3], [2, 1]])
    print(fz4a(A, B))

    A = np.array([[1, 2, 3, 1, 1], [0, 3, 0, 1, 3], [0, 3, 0, 1, 4], [0, 3, 2, 1, 2], [4, 1, 2, 1, 4]])
    B = np.array([[0, 3, 1], [0, 2, 2], [2, 1, 1]])
    print('\n', fz4a(A, B))

    A = np.array([[1, 2, 3, 1, 1], [0, 3, 0, 1, 3], [0, 3, 0, 1, 4], [0, 3, 2, 1, 2], [4, 1, 2, 1, 4]])
    B = np.array([[0, 3], [2, 2]])
    print('\n', fz4a(A, B))

    A = np.array([[1, 2, 1, 4, 8], 
                  [0, 0, 4, 3, 6], 
                  [1, 2, 1, 6, 8], 
                  [0, 0, 6, 1, 3]])   
    B = np.array([[0, 3], 
                  [2, 2]])
    print('\n', fz4a(A, B))

    A = np.array([[1, 2, 1, 4], 
                  [0, 0, 4, 3], 
                  [1, 2, 1, 6], 
                  [0, 0, 6, 1], 
                  [0, 6, 9, 1]])   
    B = np.array([[0, 3, 1], [0, 2, 2], [2, 1, 1]])
    print('\n', fz4a(A, B))

    C = np.array([[1, 2, 1, 1], [0, 0, 5, 3], [1, 4, 1, 4], [0, 0, 7, 1]])   # False
    print('\n', fz4b(C))

    C = np.array([[1, 2, 1, 4], [0, 0, 5, 3], [1, 4, 1, 4], [0, 0, 0, 1]])   # True
    print('\n', fz4b(C))

    C = np.array([[1, 2, 1, 4], [0, 0, 1, 3], [1, 4, 1, 4], [0, 0, 7, 1]])   # True
    print('\n', fz4b(C))

    C = np.array([[1, 2, 1, 4], [0, 0, 4, 3], [1, 4, 1, 4], [0, 0, 7, 1]])   # True
    print('\n', fz4b(C))

    C = np.array([[1, 2, 1, 4, 8], 
                  [0, 8, 4, 3, 3], 
                  [1, 2, 0, 8, 3], 
                  [0, 8, 6, 1, 3]])   # True
    print('\n', fz4b(C))

    C = np.array([[1, 2, 1, 4], 
                  [0, 0, 4, 3], 
                  [1, 2, 7, 9], 
                  [0, 0, 9, 9], 
                  [5, 9, 5, 9]])   # True
    print('\n', fz4b(C))
