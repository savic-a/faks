#!/usr/bin/python

import sys

i=0

for myline in sys.stdin:
    # Ovo nam je potrebno zbog prve linije u csv-u, koja ne sadrzi podatke
    if i == 0:
        i+=1 
        continue
    myline = myline.strip()
    row = myline.split(',')
    print('%s_%s\t%s' % (row[7], row[3], row[2]))
