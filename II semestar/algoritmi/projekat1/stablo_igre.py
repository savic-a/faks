class QueueError(Exception):
    pass


class Queue(object):
    def __init__(self, capacity=10):
        self._size = 0
        self._first = 0
        self._capacity = capacity
        self._data = [None] * self._capacity

    def __len__(self):
        return self._size

    def is_empty(self):
        return self._size == 0

    def first(self):
        if self.is_empty():
            raise QueueError('Red je prazan.')
        return self._data[self._first]

    def dequeue(self):
        if self.is_empty():
            raise QueueError('Red je prazan.')

        element = self._data[self._first]
        self._data[self._first] = None
        self._first = (self._first + 1) % self._capacity
        self._size -= 1
        if 0 < self._size < self._capacity // 4:
            self._resize(self._capacity // 2)
        return element

    def enqueue(self, e):
        if self._size == self._capacity:
            self._resize(2 * self._capacity)

        index = (self._first + self._size) % self._capacity
        self._data[index] = e
        self._size += 1

    def _resize(self, capacity):
        current_data = self._data
        current_first = self._first
        self._data = [None] * capacity

        for k in range(self._size):
            self._data[k] = current_data[current_first]
            current_first = (current_first + 1) % len(current_data)

        self._first = 0
        self._capacity = capacity


class TreeNode(object):
    __slots__ = 'parent', 'children', 'data', '_move', 'x', 'y'

    def __init__(self, data, x, y):
        self.parent = None
        self.children = []
        self.data = data
        self._move = None
        self.x = x
        self.y = y

    def set_value(self, value):
        self.data = value

    def set_move(self, move, i, j):
        self._move = move[i][j]

    def get_move(self):
        return self._move

    def is_root(self):
        return self.parent is None

    def is_list(self):
        return len(self.children) == 0

    def add_child(self, x):
        x.parent = self
        self.children.append(x)

    def __str__(self):
        return str(self.data)


class Tree(object):
    def __init__(self):
        self.root = None
        self._counter = 0

    def __len__(self):
        self._recursion(self.root)
        brojac = self._counter
        self._counter = 0
        return brojac

    def _recursion(self, node):
        for child in node.children:
            if child.is_list():
                self._counter += 1
            else:
                self._recursion(child)

    def is_empty(self):
        return self.root is None

    def depth(self, x):
        if x.is_root():
            return 0
        else:
            return 1 + self.depth(x.parent)

    def height(self, x):
        if x.is_list():
            return 0
        else:
            return 1 + max(self.height(c) for c in x.children)

    def preorder(self, x):
        if not self.is_empty():
            print(x.data)
            for c in x.children:
                self.preorder(c)

    def postorder(self, x):
        if not self.is_empty():
            for c in x.children:
                self.postorder(c)
            print(x.data)
