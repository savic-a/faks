#!/usr/bin/python

import sys

for line in sys.stdin:
    line = line.strip()
    words = line.split(',')
    print(words[0].strip() + "," + words[1].strip())
        