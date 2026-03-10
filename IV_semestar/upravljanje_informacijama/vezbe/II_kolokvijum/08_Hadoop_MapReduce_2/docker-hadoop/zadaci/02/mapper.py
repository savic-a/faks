#!/usr/bin/python

import sys

for line in sys.stdin:
    line = line.strip()
    words = line.split(",")
    print(words[3].strip() + "," + words[2].strip())
