//============================================================================
// Name        : Flight.h
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Flight interface class
//============================================================================
#pragma once

#include <string>
#include <iostream>

class Flight
{
	std::string destination;
	std::string departure;
	std::string flightNo;
	std::string gateNo;

public:
	Flight();
	Flight(std::string destination, std::string departure, std::string flightNo, std::string gateNo);
	~Flight();

	std::string getDestination();
	std::string getDeparture();
	std::string getFlightNo();
	std::string getGateNo();
	void setFlightNo(std::string flightNo);
	void setDestination(std::string destination);
	void setDeparture(std::string departure);
	void setGateNo(std::string gateNo);

	friend bool operator< (Flight& f1, Flight& f2);
	friend bool operator== (Flight& f1, Flight& f2);
	friend std::ostream& operator<< (std::ostream& out, const Flight& f);
};