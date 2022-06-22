//============================================================================
// Name        : SelectionSort.cpp
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Implementation of the SelectionSort class
//============================================================================
#include "Flight.h"
#include "Sort.h"
#include "WindowSort.h"

#include <vector>


SelectionSort::SelectionSort() {};

SelectionSort::~SelectionSort() {};


/// <summary> Implementation of selectionsort algorithm </summary>
/// <param name="fileName"> the name of the output file </param>
/// <param name="flights"> initial vector of flights </param>
/// <param name="criterion"> sorting criterion </param>
void SelectionSort::sort(std::string fileName, std::vector<Flight>& flights, std::string criterion)
{
	WindowSort ws(Point(200, 200), 600, 400, "Sortiranje", flights, criterion);
	ws.waitForButton();

	for (int i = 0; i < flights.size() - 1; i++) {
		this->numIter++;
		int min = i;
		//resetNumCmps();
		//resetNumSwap();

		for (int j = i + 1; j < flights.size(); j++) {    // for each of the unsorted elements
			if (criterion == "0") {                       // sort by destination
				this->numCmps++;
				if (flights[j].getDestination() < flights[min].getDestination()) {
					min = j;
				}
			}
			else if (criterion == "1") {                       // sort by departure
				this->numCmps++;
				if (flights[j] < flights[min]) {
					min = j;
						
				}
			}
			else if (criterion == "2") {                       // sort by flight number
				this->numCmps++;
				if (flights[j].getFlightNo() < flights[min].getFlightNo()) {
					min = j;
				}
			}
			else if (criterion == "3") {                       // sort by gate number
				this->numCmps++;
				if (flights[j].getGateNo() < flights[min].getGateNo()) {
					min = j;
				}
			}
		}

		std::vector<Flight> old = flights;

		if (i != min) {
			std::swap(flights[i], flights[min]);
			this->numSwap++;
		}
		this->currArrange = flights;
		
		// graphical representation of the iteration
		ws.drawCircles(numIter, old, currArrange);
	}
	// print sorted values
	ws.putSortValuesInBox(flights, criterion);   

	// write the collected data to a file
	writeInFile(fileName, numIter, currArrange, numCmps, numSwap);

	while (true)
	{
		ws.waitForButton();
	}
}
