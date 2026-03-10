#!/bin/bash
#
# Napisati bash skript koji prima N argumenata komandne linije i na
# standardni izlaz ispisuje koliko je argumenata prosledjeno i svaki
# od argumenata u novoj liniji.
#
# Primer poziva:
#   ./03_argumenti

echo "Broj prosledjenih parametara je: $#"

for argument in $@
do
	echo $argument
done 

# TODO implementirati
