#!/bin/bash
#
# Napisati bash skript koji za dva zadata cela broja a i b poredi 
# njihove vrednosti i ispisuje na standardni izlaz u kakvoj su relaciji.
# a i b zadati kao prizvoljne promenljive skripta sa predefinisanim
# vrednostima (npr. a=100 i b=10).
#
# Primer koriscenja:
#   ./02_poredjenje_brojeva.sh

a=100
b=10
if [ $a -gt $b ];
then
	echo "a je vece od b"
elif [ $a -lt $b ];
then 
	echo "a je manje od b"
else
	echo "a je jednako sa b"
fi

# TODO implementirati
