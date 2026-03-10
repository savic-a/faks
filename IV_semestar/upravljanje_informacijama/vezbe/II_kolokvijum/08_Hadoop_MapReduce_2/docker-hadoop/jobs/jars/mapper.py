#!/usr/bin/env python

import sys

for line in sys.stdin:
    line = line.strip()

    try:
        words = line.split(',')
    except Exception:
        continue

    if words[1] == "NULL" or words[0] == "NULL" or words[1] == "":
        continue

    print('%s\t%s' % (words[1], words[0]))
