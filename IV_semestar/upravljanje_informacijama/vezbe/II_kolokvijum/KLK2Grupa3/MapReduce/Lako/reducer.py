#!/usr/bin/env python

import sys

current_group = None
current_count = 0
group = None

for line in sys.stdin:
    line = line.strip()
    group, count = line.split('\t', 1)

    try:
        count = int(count)
    except ValueError:
        continue

    if current_group == group:
        current_count += count
    else:
        if current_group:
            print('%s\t%s' % (current_group, current_count))
        current_count = count
        current_group = group

# do not forget to output the last word if needed!
if current_group == group:
    print('%s\t%s' % (current_group, current_count))
