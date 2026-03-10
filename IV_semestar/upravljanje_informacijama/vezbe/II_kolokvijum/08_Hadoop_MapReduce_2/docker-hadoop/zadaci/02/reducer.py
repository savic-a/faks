#!/usr/bin/python

import sys

current_word = ""
current_price = 0
item = ""

for line in sys.stdin:
    myline = line.strip()
    item, price = myline.split(',', 1)
    try:
        price = int(price)
    except ValueError:
        continue
    if current_word == item:
        current_price += price
    else:
        if current_word:
            print(current_word + ", ", current_price)
        current_price = price
        current_word = item

if current_word == item:
    print(current_word + ", ", current_price)