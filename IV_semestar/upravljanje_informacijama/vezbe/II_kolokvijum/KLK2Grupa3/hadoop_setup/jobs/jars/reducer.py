#!/usr/bin/env python

import sys

current_group = None
current_count = 1
group = None
longest = None
shortest = None
long = None
short = None

for line in sys.stdin:
    line = line.strip()
    group, item = line.split('\t')

    # try:
    #     count = int(count)
    # except ValueError:
    #     continue

    if current_group == group:
        current_count += 1
        if len(item) < len(short):
            short = shortest
            shortest = item

        if len(item) > len(long):
            long = longest
            longest = item

    else:
        if current_group:
            print('%s\t%s\t%s\t%s' % (current_group, current_count, longest, shortest))
        current_count = 1
        current_group = group
        long = item
        longest = item
        short = item
        shortest = item

# do not forget to output the last word if needed!
if current_group == group:
    print('%s\t%s\t%s\t%s' % (current_group, current_count, longest, shortest))
