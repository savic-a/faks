//============================================================================
// Name        : QuickSort.cpp
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Implementation of the QuickSort class
//============================================================================

#include "Sort.h"
#include "WindowSort.h"

#include <string>


QuickSort::QuickSort() {};

QuickSort::~QuickSort() {};


/// <summary> Implementation of quicksort algorithm </summary>
/// <param name="flights"> initial vector of flights </param>
/// <param name="criterion"> sorting criterion </param>
/// <param name="low"> the index of the first partition element </param>
/// <param name="high"> the index of the last partition element </param>
void QuickSort::sort(std::vector<Flight>& flights, std::string criterion, int low, int high)
{    
    if (low < high) 
    {
        this->numIter++;
        //resetNumCmps();
        //resetNumSwap();
        
        int storeIndex = low - 1;   // storage of the first major number than pivot       

        // sort by destination
        if (criterion == "0") 
        {  // rightmost element is pivot
            std::string pivot = flights[high].getDestination();   
            // each element from the unsorted partition compere with pivot
            for (int j = low; j < high; j++) 
            {         
                this->numCmps++;
                if (flights[j].getDestination() <= pivot)  
                {
                    storeIndex++;
                    // swapping of the first number which is bigger and  
                    //the next one which is smaller than the pivot
                    if (storeIndex != j)
                    {
                        std::swap(flights[storeIndex], flights[j]);
                        this->numSwap++;
                    }
                    
                }
            }
        }
        // sort by departure
        else if (criterion == "1") 
        {  
            Flight pivot = flights[high];       
            for (int j = low; j < high; j++) 
            {
                this->numCmps++;
                if (flights[j] < pivot) 
                {
                    storeIndex++;
                    if (storeIndex != j)
                    {
                        std::swap(flights[storeIndex], flights[j]);
                        this->numSwap++;
                    }
                }
            }
        }
        // sort by flight number
        else if (criterion == "2") 
        {  
            std::string pivot = flights[high].getFlightNo();  
            for (int j = low; j < high; j++) 
            {
                this->numCmps++;
                if (flights[j].getFlightNo() < pivot) 
                {
                    storeIndex++;
                    if (storeIndex != j)
                    {
                        std::swap(flights[storeIndex], flights[j]);
                        this->numSwap++;
                    }
                }
            }
        }
        // sort by gate number
        else if (criterion == "3") 
        {   
            std::string pivot = flights[high].getGateNo();
            for (int j = low; j < high; j++) 
            {
                this->numCmps++;
                if (flights[j].getGateNo() <= pivot) 
                {
                    storeIndex++;
                    if (storeIndex != j)
                    {
                        std::swap(flights[storeIndex], flights[j]);
                        this->numSwap++;
                    }
                }
            }
        }
        // swapping pivot and the last flight less than a pivot 
        if (storeIndex != high - 1) 
        {
            std::swap(flights[high], flights[storeIndex + 1]);
            this->numSwap++;
        }

        this->currArrange = flights;
        this->vec.push_back(flights);
        
        sort(flights, criterion, low, storeIndex);  // recursive call on the left of pivot

        sort(flights, criterion, storeIndex + 2, high);  // recursive call on the right of pivot   
    } 
}