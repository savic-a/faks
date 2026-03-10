#!/usr/bin/python

import sys

current_pair = ""
current_friend = ""
friends = []
pair = ""

for line in sys.stdin:
    line = line.strip()
    pair, friend = line.split(':')
    if current_pair == pair:
        friends.append(current_friend)
    else:
        if current_pair:
            print(current_pair + ":", friends)
        current_pair = pair
        current_friend = friend
        friends = []

if current_pair == pair:
    print(current_pair + ":", friends)


