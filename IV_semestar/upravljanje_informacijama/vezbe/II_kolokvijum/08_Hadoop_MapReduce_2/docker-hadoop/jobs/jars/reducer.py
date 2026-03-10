#!/usr/bin/env python

import sys

current_word = None
current_count = 1
word = None

item = None
long = None
longest = None
short = None
shortest = None

# input comes from STDIN
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()

    # parse the input we got from mapper.py
    word, item = line.split('\t', 1)

    if current_word == word:
        current_count += 1
        if len(short) < len(item):
            shortest = short
        else:
            short = item
            shortest = item

        if len(long) > len(item):
            longest = long
        else:
            long = item
            longest = item
    else:
        if current_word:
            print('%s\t%s\t%s\t%s' % (current_word, current_count, longest, shortest))
        current_count = 1
        current_word = word
        short = item
        long = item
        shortest = item
        longest = item

# do not forget to output the last word if needed!
if current_word == word:
    print('%s\t%s\t%s\t%s' % (current_word, current_count, longest, shortest))