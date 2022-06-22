class StackError(Exception):
    pass


class UnexpectedInputError(StackError):
    pass


class TooManyCharactersError(StackError):
    pass


class Stack(object):
    def __init__(self):
        self._data = []

    def __len__(self):
        return len(self._data)

    def is_empty(self):
        return len(self._data) == 0

    def push(self, element):
        self._data.append(element)

    def pop(self):
        if self.is_empty():
            raise StackError('Stek je prazan.')
        return self._data.pop()


OPERATORS = '+-*/^'
CHARACTERS = '~`!@#$%&(|)_=[{}]?"";:<>'


def is_too_much(tokens):
    length = len(tokens)
    operators = 0
    for token in tokens:
        if token in OPERATORS:
            operators += 1
    operands = length - operators
    if operands == 0 and operators == 0:
        raise UnexpectedInputError('Uneli ste prazan izraz.')
    elif operands - operators > 1 or (operands == 1 and operators == 0):
        raise TooManyCharactersError('Uneli ste previše operanada.')
    elif operands - operators < 1:
        raise TooManyCharactersError('Uneli ste previše operatora.')


def is_operand(token):
    number = list(token)
    for num in number:
        if 'z' >= num >= 'a' or 'Z' >= num >= 'A' or num in CHARACTERS:
            raise UnexpectedInputError('Uneli ste nevalidne podatke.')
        elif token in OPERATORS:
            return False
    else:
        token = eval(token)
        return token


def get_precedence(token):
    result = 0
    for char in OPERATORS:
        result += 1
        if char == token:
            if char in '-/':
                result -= 1
            break
    return result


def get_lowest_precedence(operand):
    lowest = 100

    precedence_count = 0
    for char in operand:
        if precedence_count == 0 and char in OPERATORS:
            op_precedence = get_precedence(char)
            if op_precedence < lowest:
                lowest = op_precedence
        elif char == '(':
            precedence_count += 1
        elif char == ')':
            precedence_count -= 1

    return lowest


def evaluate(postfix):
    stack = Stack()

    tokens = postfix.split()
    is_too_much(tokens)

    for token in tokens:
        if is_operand(token):
            stack.push(token)
        else:
            first = stack.pop()
            second = stack.pop()
            priority = get_precedence(token)
            lpfirst = get_lowest_precedence(first)
            lpsecond = get_lowest_precedence(second)

            if lpfirst < priority:
                first = '(' + first + ')'

            if lpsecond < priority:
                second = '(' + second + ')'

            new_expression = second + ' ' + token + ' ' + first
            stack.push(new_expression)

    return stack.pop()


if __name__ == '__main__':
    expression = input('Unesite izraz (operande i operatore odvojiti space-om): ')
    print(evaluate(expression))
