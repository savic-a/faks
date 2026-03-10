#!/usr/bin/env python

import time
import threading
import os
import socket
import sys


from subprocess import * 

from datetime import datetime

counter = 0
		
def threadsock(c):
	# kada se neko spoji na nas program preko socketa
	# procitamo maks 1KB od klijenta
	text = c.recv(1024)
	if text == b'':
		return
	# ocistimo whitespace-ove
	text = text.strip()
	# razbijemo po redovima (moze da bude vise komandi, razdvojenih novim redom)
	lines = text.split()
	for line in lines:
		print (line)
	# pristupicemo globalnoj promenljivoj previous da bismo je menjali
	global counter
	counter = counter + 1
	c.send(("counter: " + str(counter) + "\n").encode()) 
	c.close()

s = socket.socket()         # Create a socket object
host = 'localhost'
print ('Waiting for connections on host: ' + host)
port = 9000                # Reserve a port for your service.
s.bind((host, port))        # Bind to the port


s.listen(5)                 # Now wait for client connection.
while True:
	c, addr = s.accept()      # Establish connection with client.
	print ('Got connection from ', addr)
	threading.Thread(target=threadsock, args=[c]).start()

