#!/bin/bash
#
# Napisati bash script koji testira znanje tablice mnozenja korisnika.
#
# Skripta treba da ispisuje dva nasumicna broja izmedju 1 i 10, i da
# zatim trazi od korisnika da unese proizvod ta dva broja. Nasumicni
# broj od 1 do 10 u bash-u dobijamo izrazom: $((1 + RANDOM % 10)).
#
# Ucitavanje se radi pomocu komande `read' koja prima kao parametar
# naziv promenljive u koju treba ucitati jedan red.  Ukoliko korisnik
# ne unese pravilan broj izmedju 1 i 100, pitanje treba ponoviti.
#
# Kada korisnik unese broj izmedju 1 i 100, potrebno je proveriti
# ispravnost odgovora.  Proizvod promenljive A i B u bash-u dobijamo
# pomocu izraza $((A * B)).  Potrebno je obavestiti korisnika da li je
# odgovor tacan ili nije.
#
# Izlazak iz programa se vrsi kombinacijom tastera Ctrl+D.  Ova
# kombinacija tastera salje signal zavrsetka datoteke, i izaziva da
# komanda `read' vrati negativnu povratnu vrednost.
#
# U promenljivoj okruzenja K2_LOGFILE je data putanja do datoteke u
# koju treba upisati evidenciju odgovora.  Svaki red treba da sadrzi
# pitanje (brojeve A i B), odgovor koji je korisnik uneo, i rec
# `tacno', odnosno `netacno', u zavisnosti od toga da li je uneti
# odgovor tacan ili netacan.
#
# Ukoliko nije data promenljiva okruzenja K2_LOGFILE, potrebno je
# traziti od korisnika da unese putanju do datoteke za evidenciju
# pomocu vec objasnjene komande `read'.
#
# Skripta mora da proveri da li ima odgovarajuca potrebna prava za
# upis u evidenciju, i ukoliko to nije slucaj, treba da obavesti
# korisnika i prekine svoj rad.
#

[[ -z "$K2_LOGFILE" ]] && read -p "Unesite putanju do datoteke: " K2_LOGFILE

if ! [[ -w "$K2_LOGFILE" ]]
then 
	echo "Nemate pravo pristupa datoteci"
	exit 1
fi

a=$((1 + RANDOM % 10))
b=$((1 + RANDOM % 10))

while true
do
	a=$((1 + RANDOM % 10))
	b=$((1 + RANDOM % 10))
	c=0

	read -p "$a*$b=" c || break 2

	if [[ "$c" -gt 0 && "$c" -lt 101 ]]
	then
		if [[ "$((a * b))" -eq "$c" ]]
		then
			echo "Tacno ste odgovorili"
			echo "$a*$b=$c tacno" >> $K2_LOGFILE
		else
			echo "Netacno ste odgovorili"
			echo "$a*$b=$c netacno" >> $K2_LOGFILE
		fi
	fi
done