"""
Modul sadrži implementaciju stabla.
"""
from tree_search.queue import Queue


class TreeNode(object):
    """
    Klasa modeluje čvor stabla.
    """
    __slots__ = 'parent', 'children', 'data'

    def __init__(self, data):
        """
        Konstruktor.

        Argument:
        - `data`: podatak koji se upisuje u čvor
        """
        self.parent = None
        self.children = []
        self.data = data

    def is_root(self):
        """
        Metoda proverava da li je čvor koren stabla.
        """
        return self.parent is None

    def is_leaf(self):
        """
        Metoda proverava da li je čvor list stabla.
        """
        return len(self.children) == 0

    def add_parent(self, x):
        """
        Metoda dodaje roditelja čvoru.

        Argument:
        - `x`: čvor roditelja
        """
        # kreiranje dvosmerne veze između čvorova
        self.parent = x
        x.children.append(self)

    def add_child(self, x):
        x.parent = self
        self.children.append(x)


class Tree(object):
    """
    Klasa modeluje stablo.
    """
    def __init__(self):
        self.root = None
        self.nodes = []

    def is_empty(self):
        """
        Metoda proverava da li stablo ima elemenata.
        """
        return self.root is None

    def depth(self, x):
        """
        Metoda izračunava dubinu zadatog čvora.

        Argument:
        - `x`: čvor čija dubina se računa
        """
        if x.is_root():
            return 0
        else:
            return 1 + self.depth(x.parent)

    def _height(self, x):
        """
        Metoda izračunava visinu podstabla sa zadatim korenom.

        Argument:
        - `x`: koren posmatranog podstabla
        """
        if x.is_leaf():
            return 0
        else:
            return 1 + max(self._height(c) for c in x.children)

    def height(self):
        return self._height(self.root)

    def all_nodes(self, x):
        # if not self.is_empty():
        #     self.nodes.append(x)
        #     for c in x.children:
        #         self.all_nodes(c)
        to_visit = Queue()
        to_visit.enqueue(self.root)
        while not to_visit.is_empty():
            e = to_visit.dequeue()
            self.nodes.append(e)
            for c in e.children:
                to_visit.enqueue(c)

    def preorder(self, x):
        """
        Preorder obilazak po dubini

        Najpre se vrši obilazak roditelja a zatim svih njegovih potomaka.

        Argument:
        - `x`: čvor od koga počinje obilazak
        """
        if not self.is_empty():
            print(x.data)
            for c in x.children:
                self.preorder(c)

    def postorder(self, x):
        """
        Postorder obilazak po dubini

        Najpre se vrši obilazak potomaka a zatim i roditelja

        Argument:
        - `x`: čvor od koga počinje obilazak
        """
        if not self.is_empty():
            for c in x.children:
                self.postorder(c)
            print(x.data)

    def breadth_first(self):
        """
        Metoda vrši obilazak stabla po širini.
        """
        to_visit = Queue()
        to_visit.enqueue(self.root)
        while not to_visit.is_empty():
            e = to_visit.dequeue()
            print(e.data)

            for c in e.children:
                to_visit.enqueue(c)





if __name__ == '__main__':
    # instanca stabla
    t = Tree()
    # t.root = TreeNode(0)

    # kreiranje relacija između novih čvorova
    a = TreeNode(1)   # koren
    b = TreeNode(2)   # list
    c = TreeNode(3)   # list
    d = TreeNode(4)
    e = TreeNode(5)
    f = TreeNode(6)
    h = TreeNode(0)

    b.add_parent(a)
    c.add_parent(a)
    e.add_parent(d)
    f.add_parent(d)
    a.add_parent(h)
    d.add_parent(h)
    t.root = h

    # a.add_child(b)
    # t.root.add_child(a)
    # t.root.add_child(c)

    # visina stabla
    print('Visina = %d' % t.height())

    # dubina čvora
    print('Dubina(a) = %d' % t.depth(a))

    # obilazak po dubini - preorder
    print('PREORDER')
    t.preorder(t.root)

    # obilazak po dubini - postorder
    print('POSTORDER')
    t.postorder(t.root)

    # obilazak po širini
    print('BREADTH FIRST')
    t.breadth_first()
