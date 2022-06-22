class Task(object):
    def __init__(self, name, percent):
        self._name = name
        self._percent = percent
        self._sum_percent = 0

    @property
    def name(self):
        return self._name

    @property
    def percent(self):
        return self._percent

    @property
    def sum_percent(self):
        return self._sum_percent

    @sum_percent.setter
    def sum_percent(self, sum_percent):
        self._sum_percent = sum_percent

    def process(self, sum_percent):
        sum_percent += self._percent
        return sum_percent


class Processor(object):
    def __init__(self):
        self._data = []

    def enqueue(self, e):
        self._data.append(e)

    def dequeue(self):
        element = self._data[0]
        del self._data[0]
        return element

    def first(self):
        return self._data[0]

    def is_empty(self):
        return len(self._data) == 0


def round_robin():
    done = 100
    while not(processor.is_empty()):
        task = processor.first()
        percent = task.percent
        if task.name == 'T1':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T2':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T3':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T4':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T5':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T6':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T7':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T8':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T9':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        elif task.name == 'T10':
            percent, task.sum_percent = working(task, task.sum_percent, percent)
        processor.dequeue()
        if task.sum_percent == done:
            continue
        else:
            processor.enqueue(task)


def working(task, sum_percent, percent):
    done = 100
    if sum_percent > done:
        return
    elif done - sum_percent < percent:
        percent = done - sum_percent
        task._percent = percent
        sum_percent = task.process(sum_percent)
    else:
        task = processor.first()
        percent = task.percent
        sum_percent = task.process(sum_percent)
    if sum_percent <= done:
        print(task.name + ' ' + str(sum_percent) + '%')
    return percent, sum_percent


if __name__ == '__main__':
    task1 = Task('T1', 5)
    task2 = Task('T2', 8)
    task3 = Task('T3', 9)
    task4 = Task('T4', 4)
    task5 = Task('T5', 7)
    task6 = Task('T6', 6)
    task7 = Task('T7', 7)
    task8 = Task('T8', 4)
    task9 = Task('T9', 8)
    task0 = Task('T10', 3)

    processor = Processor()
    processor.enqueue(task1)
    processor.enqueue(task2)
    processor.enqueue(task3)
    processor.enqueue(task4)
    processor.enqueue(task5)
    processor.enqueue(task6)
    processor.enqueue(task7)
    processor.enqueue(task8)
    processor.enqueue(task9)
    processor.enqueue(task0)

    round_robin()
