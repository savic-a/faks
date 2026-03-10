#!/usr/bin/python

import sys

current_key = ""
key = ""
current_count = 0
current_max = 0

# Input takes from standard input
for myline in sys.stdin:
    # Remove whitespace either side
    myline = myline.strip()
    # Split the input
    key, price = myline.split('\t', 1)  # drzava_kartica
    # Convert count variable to integer
    try:
        price = int(price)
    except ValueError:
        # Count was not a number, so silently ignore this line
        continue
    if current_key == key:
        current_max = max(current_count, price)
    else:
        if current_key:
            print('%s\t%s' % (current_key, max(current_max, current_count)))
        current_key = key
        current_count = price

# Do not forget to output the last word if needed!
if current_key == key:
    print('%s\t%s' % (current_key, current_max))
