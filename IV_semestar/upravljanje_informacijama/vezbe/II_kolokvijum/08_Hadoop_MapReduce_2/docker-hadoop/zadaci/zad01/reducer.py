#!/usr/bin/python

import sys

current_word = ""
current_temp = 0.0
count = 1
word = ""

for line in sys.stdin:
    line = line.strip()
    word, temp = line.split(',', 1)
    try:
        temp = float(temp)
    except ValueError:
        continue

    if current_word == word:
        current_temp += temp
        count += 1
    else:
        if current_word:
            print(current_word + ", ", current_temp/count)
        current_temp = temp
        current_word = word
        count = 1

# do not forget to output the last word if needed!
if current_word == word:
    print(current_word + ", ", current_temp/count)
