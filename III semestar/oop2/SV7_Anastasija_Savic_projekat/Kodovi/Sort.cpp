//============================================================================
// Name        : Sort.cpp
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Implementation of the sort classes
//============================================================================

#include "Sort.h"

#include <vector>
#include <iostream>
#include <fstream>

Sort::Sort() : numIter(0), currArrange(), numCmps(0), numSwap(0) {};

Sort::~Sort() {};

/// <summary> Write the obtained values to a file </summary>
/// <param name="fileName"> the name of the output file </param>
/// <param name="iter"> number of iterations during sorting </param>
/// <param name="flights"> vector of sorted flights </param>
/// <param name="cmps"> number of comparisons during sorting </param>
/// <param name="swap"> number of swaps during sorting </param>
void Sort::writeInFile
(std::string fileName, int iter, std::vector<Flight> flights, int cmps, int swap)
{
	std::ofstream ofs;
	size_t found = fileName.find(".txt");
	if (found == std::string::npos)
	{
		fileName = fileName + ".txt";
	}
	ofs.open(fileName);
	if (ofs.is_open())
	{
		ofs << "Ukupan broj iteracija: " << iter << std::endl;
		ofs << "Ukupan broj poredjenja: " << cmps << std::endl;
		ofs << "Ukupan broj pomeranja elemenata: " << swap << std::endl;
		ofs << "SORTIRANI ELEMENTI" << std::endl;
		ofs << "========================" << std::endl;
		for (Flight f : flights)
		{
			ofs << f;
		}
		ofs.close();
	}
	else
	{
		std::exit(EXIT_FAILURE);
	}
}

void Sort::resetNumCmps()
{
	this->numCmps = 0;
}

void Sort::resetNumSwap()
{
	this->numSwap = 0;
}

// getters
int Sort::getNumIter()
{
	return numIter;
}

std::vector<Flight> Sort::getCurrArrange()
{
	return currArrange;
}

int Sort::getNumCmps()
{
	return numCmps;
}

int Sort::getNumSwap()
{
	return numSwap;
}