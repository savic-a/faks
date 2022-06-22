class TrieNode(object):
    __slots__ = 'char', 'children', 'row', 'is_leaf'

    def __init__(self, char):
        self.char = char
        self.children = {}
        self.row = []
        self.is_leaf = False

    def get_char(self):
        return self.char

    def set_char(self, new_char):
        self.char = new_char

    def get_children(self):
        return self.children

    def get_row(self):
        return self.row

    def set_row(self, new_row):
        self.char = new_row


class Trie(object):

    __slots__ = '_root', '_all'

    def __init__(self):
        self._root = TrieNode('+')
        self._all = []

    def find(self, word):
        current = self._root
        for char in word:
            if char not in current.children:
                return None
            current = current.children[char]
        if current.is_leaf:
            return current.row

    def insert(self, word, hint1, hint2):
        current = self._root
        for i, char in enumerate(word):
            if char not in current.children:
                prefix = word[0:i+1]
                current.children[char] = TrieNode(prefix)
            current = current.children[char]
        current.row.extend([[word, hint1, hint2]])
        current.is_leaf = True
        self._all.append(current)

    def starts_with(self, prefix):
        words = []
        current = self._root
        for char in prefix:
            if char not in current.children:
                return False
            current = current.children[char]
        self._child_words(current, words)
        return words

    def _child_words(self, node, words):
        if node.is_leaf:
            words.append(node.row)
        for letter in node.children:
            self._child_words(node.children[letter], words)

    def iteration(self):
        for node in self._all:
            yield node
