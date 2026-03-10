#!/usr/bin/env python

import sys

for line in sys.stdin:
    line = line.strip()
    word = line.split(',')
    print('%s\t%s' % (word[2], 1))  # grupa   naziv
