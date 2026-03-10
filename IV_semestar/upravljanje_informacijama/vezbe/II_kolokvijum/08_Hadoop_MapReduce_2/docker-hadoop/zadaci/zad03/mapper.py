#!/usr/bin/python

import sys

for line in sys.stdin:
    line = line.strip()
    person = line.split(':')[0]
    friends = line.split(':')[1].split(',')
    for i in range(len(friends)):
        for j in range(i+1, len(friends)):
            print(friends[i] + "-" + friends[j] + ":" + person)
