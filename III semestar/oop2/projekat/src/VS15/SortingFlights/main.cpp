//============================================================================
// Name        : main.cpp
// Author      : Savic Anastasija
// Date        : 13.01.2021
// Description : Implementation of free functions
//============================================================================
#include "Flight.h"
#include "Sort.h"
#include "MyWindow.h"
#include "WindowSort.h" 

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <sstream>
#include <cstdlib>


/// <summary> Reading flights for sorting from a file </summary>
/// <param name="fileName"> file name to read </param>
/// <returns> vector of read flights </returns>

std::vector<Flight> readFile(std::string fileName) 
{
    std::vector<Flight> flights;
    Flight f = Flight();
    int i = 0;

    std::ifstream data(fileName);
    if (!data.is_open())
    {
        std::exit(EXIT_FAILURE);   // unsuccessful execution of a program
    }
    std::string str;
    std::getline(data, str); // skip the first line
    
    while (std::getline(data, str))
    {
        std::istringstream iss(str);
        std::string token;
        while (std::getline(iss, token, ';'))
        {
            if (i == 0) 
            {
                f.setDestination(token);
            }
            else if (i == 1) 
            {
                token.erase(0, 1);  // remove first character (space)
                f.setDeparture(token);
            }

            else if (i == 2) 
            {
                token.erase(0, 1);
                f.setFlightNo(token);
            }

            else if (i == 3) 
            {
                token.erase(0, 1);
                f.setGateNo(token);
            }
            i++;
        }
        flights.push_back(f);
        i = 0;
    }
    return flights;
}


/// <summary> Window for receiving input parameters </summary>
/// <returns> vector of input parameters </returns>
std::vector<std::string> workWithCanvas()
{
    std::vector<std::string> inputs;

    MyWindow win(Point(200, 200), 600, 400, "Pocetni prozor");
    win.waitForButton();

    std::string input = win.getInputText();
    std::ifstream infile(input);
    if (infile.good()) // if the file exist
    {  
        inputs.push_back(input);
    }

    std::string output = win.getOutputText();
    std::ifstream ifile(output);
    if (!ifile) // if the file does not exist
    {  
        inputs.push_back(output);
    }

    std::string criterion = win.getCriterionText();
    if (criterion == "0" || criterion == "1" || criterion == "2" || criterion == "3") 
    {
        inputs.push_back(criterion);
    }

    std::string alg = win.getAlgText();
    if (alg == "0" || alg == "1") 
    {  
        inputs.push_back(alg);
    }

    if (inputs.size() != 4) 
    {
        win.hide();
        MessageBox(NULL, (LPCWSTR)L"Nesto ste uneli pogresno!", (LPCWSTR)L"Greska", MB_OK);
    }
    return inputs;
}


/// <summary> Display flight sorting </summary>
/// <param name="f"> the initial flight vector from the input file </param>
/// <param name="outputFile"> the name of the output file </param>
/// <param name="crit"> the criterion by which flights are sorted </param>
void workWithSortCanvas(std::vector<Flight> f, std::string outputFile, std::string crit)
{
    QuickSort qs = QuickSort(); 
    qs.vec.push_back(f);              // append initial positions of flights
    qs.sort(f, crit, 0, f.size() - 1);  // sort flights

    WindowSort ws(Point(200, 200), 550, 400, "Sortiranje", qs.vec[0], crit);
    ws.waitForButton();

    for (int i = 1; i < qs.vec.size(); i++) 
    {
        ws.drawCircles(i, qs.vec[i-1], qs.vec[i]);  // display change elements
    }
    ws.putSortValuesInBox(qs.vec[qs.vec.size()-1], crit);   // display of sorted flights

    qs.writeInFile(outputFile, qs.getNumIter(), qs.getCurrArrange(), 
        qs.getNumCmps(), qs.getNumSwap());  // write results in output file

    while (true)   // do not turn off the window
    {
        ws.waitForButton();
    }
}


void main()
{
    std::vector<std::string> criterions = workWithCanvas();

    if (criterions.size() != 4) {
        return;
    }

    std::vector<Flight> f = readFile(criterions[0]);
    if (criterions[3] == "1") {
        workWithSortCanvas(f, criterions[1], criterions[2]);
    }
    else {
        SelectionSort ss = SelectionSort();
        ss.sort(criterions[1], f, criterions[2]);
        
    }
}
