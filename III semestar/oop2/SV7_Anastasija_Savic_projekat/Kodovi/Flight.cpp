//============================================================================
// Name        : Flight.cpp
// Author      : Savic Anastasija
// Date        :
// Copyright   : 
// Description : Implementation of the Flight class
//============================================================================

#include "Flight.h"

#include <string>
#include <vector>
#include <iostream>
#include <sstream>


Flight::Flight() : destination(""), departure(""), flightNo(""), gateNo("") {};

Flight::Flight(std::string dest, std::string depa, std::string flight, std::string gate) :
	destination(dest), departure(depa), flightNo(flight), gateNo(gate) {};

Flight::~Flight() {};

// getters
std::string Flight::getFlightNo()
{
	return flightNo;
}

std::string Flight::getDestination()
{
	return destination;
}

std::string Flight::getDeparture()
{
	return departure;
}

std::string Flight::getGateNo()
{
	return gateNo;
}


// setters
void Flight::setFlightNo(std::string flight)
{
	flightNo = flight;
}

void Flight::setDestination(std::string dest)
{
	destination = dest;
}

void Flight::setDeparture(std::string depa)
{
	departure = depa;
}

void Flight::setGateNo(std::string gate)
{
	gateNo = gate;
}

bool operator< (Flight f1, Flight f2) {
	std::string dep1 = f1.departure;
	std::string dep2 = f2.departure;

	std::vector<int> result1;
	std::vector<int> result2;
	std::stringstream ss1(dep1);
	std::stringstream ss2(dep2);
	std::string item;

	while (getline(ss1, item, ':')) {
		result1.push_back(stoi(item));
	}

	while (getline(ss2, item, ':')) {
		result2.push_back(stoi(item));
	}

	if (result1[0] < result2[0]) {
		return true;
	}

	else if (result1[0] > result2[0]) {
		return false;
	}

	else {
		if (result1[1] <= result2[1]) {
			return true;
		}
		return false;
	}
}

bool operator== (Flight f1, Flight f2)
{
	if (f1.destination == f2.destination && f1.departure == f2.departure &&
		f1.flightNo == f2.flightNo && f1.gateNo == f2.gateNo) {
		return true;
	}
	return false;
}

std::ostream& operator<< (std::ostream& out, const Flight& f) 
{
	out << f.destination << "|" << f.departure << "|" << f.flightNo << "|"
		<< f.gateNo << std::endl;
	return out;
}
