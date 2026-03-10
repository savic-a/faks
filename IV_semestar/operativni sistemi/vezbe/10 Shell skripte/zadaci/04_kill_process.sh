#!/bin/bash
#
# Napisati bash skript koji omogucava korisniku da pozove jedan od cetiri signala
# nad procesom zadatim sa pid-om. Signal koji se salje procesu i pid procesa se 
# prosledjuju kao argumenti komandne linije. Signali koje treba podrzati su
# SIGSTOP (kod 19), SIGKILL (kod 9), SIGTERM (kod 15) i SIGCONT (kod 18).
# 
# Argumenti:
#   $1 - signal
#   $2 - PID procesa kojem se salje signal
#
# Primer poziva:
#   ./04_kill_process.sh STOP <pid-procesa>


opcija=$1
pid=$2

case $opcija in
	STOP)
		kill -19 $pid;;
	KILL)
		kill -9 $pid;;
	TERM)
		kill -15 $pid;;
	CONT)
		kill -18 $pid;;
*) echo "Nepoznata opcija $opcija.";;
esac

# TODO implementirati

