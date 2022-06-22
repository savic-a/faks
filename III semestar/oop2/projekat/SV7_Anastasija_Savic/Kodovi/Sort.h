//============================================================================
// Name        : Sort.h
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Sort interface class and various sorting algorithms in C++
//============================================================================
#include "Flight.h"
#include <vector>


// Sort class
class Sort
{
protected:
	int numIter;   // number of iteration in sort function
	std::vector<Flight> currArrange;  // current arrangement of flights in sort function
	int numCmps;  // number of comparisons performed in sort function
	int numSwap;  // number of swaps elements in sort function
public:
	Sort();
	~Sort();

	int getNumIter();  // returns number of iterations
	std::vector<Flight> getCurrArrange();  // returns current arrangement
	int getNumCmps();  // returns number of comparisons
	int getNumSwap();  // returns number of swaps

	void resetNumCmps();   // resets the number of comparisons
	void resetNumSwap();   // resets the number of swaps

	void writeInFile(std::string fileName, int iter, std::vector<Flight> f, int cmps, int swap);
};

// SelectionSort class
class SelectionSort : public Sort
{
public:
	SelectionSort();
	~SelectionSort();
	// main entry point
	void sort(std::string fileName, std::vector<Flight>& data, std::string i);
};

// QuickSort class
class QuickSort : public Sort
{
public:
	std::vector<std::vector<Flight>> vec;

	QuickSort();
	~QuickSort();
	// main entry point
	void sort(std::vector<Flight>& data, std::string i, int left, int right);
};

