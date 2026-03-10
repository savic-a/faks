#!/usr/bin/python

import sys

for myline in sys.stdin:
    myline = myline.strip()
    current_person, friends = myline.split(':')
    friends = friends.split(',')
    # Iterate the words list
    for i in range(len(friends)):
        for j in range(i+1,len(friends)):
            print('%s-%s\t%s' % (friends[i],friends[j], current_person))
