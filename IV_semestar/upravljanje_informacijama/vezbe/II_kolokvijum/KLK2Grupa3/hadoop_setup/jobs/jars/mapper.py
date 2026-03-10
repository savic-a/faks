#!/usr/bin/env python

import sys

file = open("data", "r+")

for line in file.readlines():
    line = line.strip()
    try:
        words = line.split(',')
    except Exception:
        continue

    if words[0] == "NULL" or words[1] == "NULL" or words[1] == "" or words[0] == "":
        continue

    print('%s\t%s' % (words[1], words[0]))   # grupa   ukus


