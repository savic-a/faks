//============================================================================
// Name        : WindowSort.h
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : WindowSort interface class
//============================================================================

#pragma once

#include "GUI.h"    
#include "Window.h"
#include "Flight.h"

using namespace Graph_lib;

class WindowSort : Window
{
    // all unsort flights
    Out_box f0;
    Out_box f1;
    Out_box f2;
    Out_box f3;
    Out_box f4;
    Out_box f5;
    Out_box f6;
    Out_box f7;
    Out_box f8;
    Out_box f9;

    // all sort flights
    Out_box sf0;
    Out_box sf1;
    Out_box sf2;
    Out_box sf3;
    Out_box sf4;
    Out_box sf5;
    Out_box sf6;
    Out_box sf7;
    Out_box sf8;
    Out_box sf9;

    // inital points
    Circle point0;
    Circle point1;
    Circle point2;
    Circle point3;
    Circle point4;
    Circle point5;
    Circle point6;
    Circle point7;
    Circle point8;
    Circle point9;

    Button next;  // button for next iteration

    bool buttonPushed;  // is the button pressed?

    static void cbShowButton(Address, Address);   // callback

public:
    WindowSort(Point xy, int w, int h, const string& title, 
        std::vector<Flight>& flights, std::string criterion);
    ~WindowSort();

    void showButtonHandler();
    void putStartValuesInBox(std::vector<Flight>& flights, std::string criterion);
    void putSortValuesInBox(std::vector<Flight>& flights, std::string criterion);
    void drawCircles(int numIter, std::vector<Flight>& oldFlights, std::vector<Flight>& newFlights);
    bool waitForButton();
};