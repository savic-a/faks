#!/usr/bin/python

from operator import itemgetter
import sys

current_pair = ""
list_of_mutual_friends = []

for myline in sys.stdin:

    myline = myline.strip()
    pair, mutual_friend = myline.split('\t', 1)

    if current_pair == pair:
        list_of_mutual_friends.append(mutual_friend)
    else:
        if current_pair:
            # Write result to standard output
            print('%s\t%s' % (current_pair, ','.join(list_of_mutual_friends)))
        current_pair = pair
        list_of_mutual_friends = [mutual_friend]

# Do not forget to output the last word if needed!
if current_pair == pair:
    print('%s\t%s' % (current_pair, ','.join(list_of_mutual_friends)))
