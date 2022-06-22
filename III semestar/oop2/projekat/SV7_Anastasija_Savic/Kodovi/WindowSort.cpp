//============================================================================
// Name        : WindowSort.h
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Implementation of the WindowSort class
//============================================================================

#pragma once

#include "WindowSort.h"

#include <vector>

WindowSort::WindowSort
(Point xy, int w, int h, const string& title, std::vector<Flight>& flights, std::string criterion) :
    Window(xy, w, h, title),
    f0(Point(0, 20), 100, 25, ""),
    f1(Point(0, 50), 100, 25, ""),
    f2(Point(0, 80), 100, 25, ""),
    f3(Point(0, 110), 100, 25, ""),
    f4(Point(0, 140), 100, 25, ""),
    f5(Point(0, 170), 100, 25, ""),
    f6(Point(0, 200), 100, 25, ""),
    f7(Point(0, 230), 100, 25, ""),
    f8(Point(0, 260), 100, 25, ""),
    f9(Point(0, 290), 100, 25, ""),

    sf0(Point(x_max() - 105, 20), 100, 25, ""),
    sf1(Point(x_max() - 105, 50), 100, 25, ""),
    sf2(Point(x_max() - 105, 80), 100, 25, ""),
    sf3(Point(x_max() - 105, 110), 100, 25, ""),
    sf4(Point(x_max() - 105, 140), 100, 25, ""),
    sf5(Point(x_max() - 105, 170), 100, 25, ""),
    sf6(Point(x_max() - 105, 200), 100, 25, ""),
    sf7(Point(x_max() - 105, 230), 100, 25, ""),
    sf8(Point(x_max() - 105, 260), 100, 25, ""),
    sf9(Point(x_max() - 105, 290), 100, 25, ""),

    point0(Point(120, 30), 5),
    point1(Point(120, 60), 5),
    point2(Point(120, 90), 5),
    point3(Point(120, 120), 5),
    point4(Point(120, 150), 5),
    point5(Point(120, 180), 5),
    point6(Point(120, 210), 5),
    point7(Point(120, 240), 5),
    point8(Point(120, 270), 5),
    point9(Point(120, 300), 5),

    next(Point(200, y_max() - 50), 150, 25, "Sledeca iteracija", cbShowButton),

    buttonPushed(false)
{
    attach(f0);
    attach(f1);
    attach(f2);
    attach(f3);
    attach(f4);
    attach(f5);
    attach(f6);
    attach(f7);
    attach(f8);
    attach(f9);

    putStartValuesInBox(flights, criterion),

    attach(sf0);
    attach(sf1);
    attach(sf2);
    attach(sf3);
    attach(sf4);
    attach(sf5);
    attach(sf6);
    attach(sf7);
    attach(sf8);
    attach(sf9);

    attach(point0);
    attach(point1);
    attach(point2);
    attach(point3);
    attach(point4);
    attach(point5);
    attach(point6);
    attach(point7);
    attach(point8);
    attach(point9);

    attach(next);
}


WindowSort::~WindowSort() {}

// callback function
void WindowSort::cbShowButton(Address, Address pw)
{
    reference_to<WindowSort>(pw).showButtonHandler();
}

// do when the next button is pressed
void WindowSort::showButtonHandler()  
{
    buttonPushed = true;
}


bool WindowSort::waitForButton()
// modified event loop:
// handle all events (as per default), quit when button_pushed becomes true
// this allows graphics without control inversion
{
    show();
    buttonPushed = false;
#if 1
    // Simpler handler
    while (!buttonPushed) Fl::wait();
    Fl::redraw();
#else
    // To handle the case where the user presses the X button in the window frame
    // to kill the application, change the condition to 0 to enable this branch.
    Fl::run();
#endif
    return buttonPushed;
}

/// <summary> Graphical representation of unsorted elements </summary>
/// <param name="flights"> list of unsorted flights </param>
/// <param name="criterion"> sorting criterion </param>
void WindowSort::putStartValuesInBox(std::vector<Flight>& flights, std::string criterion)
{
    if (criterion == "0") {
        f0.put(flights[0].getDestination());
        f1.put(flights[1].getDestination());
        f2.put(flights[2].getDestination());
        f3.put(flights[3].getDestination());
        f4.put(flights[4].getDestination());
        f5.put(flights[5].getDestination());
        f6.put(flights[6].getDestination());
        f7.put(flights[7].getDestination());
        f8.put(flights[8].getDestination());
        f9.put(flights[9].getDestination());
    }
    else if (criterion == "1") {
        f0.put(flights[0].getDeparture());
        f1.put(flights[1].getDeparture());
        f2.put(flights[2].getDeparture());
        f3.put(flights[3].getDeparture());
        f4.put(flights[4].getDeparture());
        f5.put(flights[5].getDeparture());
        f6.put(flights[6].getDeparture());
        f7.put(flights[7].getDeparture());
        f8.put(flights[8].getDeparture());
        f9.put(flights[9].getDeparture());
    }
    else if (criterion == "2") {
        f0.put(flights[0].getFlightNo());
        f1.put(flights[1].getFlightNo());
        f2.put(flights[2].getFlightNo());
        f3.put(flights[3].getFlightNo());
        f4.put(flights[4].getFlightNo());
        f5.put(flights[5].getFlightNo());
        f6.put(flights[6].getFlightNo());
        f7.put(flights[7].getFlightNo());
        f8.put(flights[8].getFlightNo());
        f9.put(flights[9].getFlightNo());
    }
    else if (criterion == "3") {
        f0.put(flights[0].getGateNo());
        f1.put(flights[1].getGateNo());
        f2.put(flights[2].getGateNo());
        f3.put(flights[3].getGateNo());
        f4.put(flights[4].getGateNo());
        f5.put(flights[5].getGateNo());
        f6.put(flights[6].getGateNo());
        f7.put(flights[7].getGateNo());
        f8.put(flights[8].getGateNo());
        f9.put(flights[9].getGateNo());
    }
}

/// <summary> Graphical representation of sorted elements </summary>
/// <param name="flights"> list of sorted flights </param>
/// <param name="criterion"> sorting criterion </param>
void WindowSort::putSortValuesInBox(std::vector<Flight>& flights, std::string criterion)
{
    if (criterion == "0") {
        sf0.put(flights[0].getDestination());
        sf1.put(flights[1].getDestination());
        sf2.put(flights[2].getDestination());
        sf3.put(flights[3].getDestination());
        sf4.put(flights[4].getDestination());
        sf5.put(flights[5].getDestination());
        sf6.put(flights[6].getDestination());
        sf7.put(flights[7].getDestination());
        sf8.put(flights[8].getDestination());
        sf9.put(flights[9].getDestination());
    }
    else if (criterion == "1") {
        sf0.put(flights[0].getDeparture());
        sf1.put(flights[1].getDeparture());
        sf2.put(flights[2].getDeparture());
        sf3.put(flights[3].getDeparture());
        sf4.put(flights[4].getDeparture());
        sf5.put(flights[5].getDeparture());
        sf6.put(flights[6].getDeparture());
        sf7.put(flights[7].getDeparture());
        sf8.put(flights[8].getDeparture());
        sf9.put(flights[9].getDeparture());
    }
    else if (criterion == "2") {
        sf0.put(flights[0].getFlightNo());
        sf1.put(flights[1].getFlightNo());
        sf2.put(flights[2].getFlightNo());
        sf3.put(flights[3].getFlightNo());
        sf4.put(flights[4].getFlightNo());
        sf5.put(flights[5].getFlightNo());
        sf6.put(flights[6].getFlightNo());
        sf7.put(flights[7].getFlightNo());
        sf8.put(flights[8].getFlightNo());
        sf9.put(flights[9].getFlightNo());
    }
    else if (criterion == "3") {
        sf0.put(flights[0].getGateNo());
        sf1.put(flights[1].getGateNo());
        sf2.put(flights[2].getGateNo());
        sf3.put(flights[3].getGateNo());
        sf4.put(flights[4].getGateNo());
        sf5.put(flights[5].getGateNo());
        sf6.put(flights[6].getGateNo());
        sf7.put(flights[7].getGateNo());
        sf8.put(flights[8].getGateNo());
        sf9.put(flights[9].getGateNo());
    }
}


/// <summary> Graphical representation of iterations in sort algorithm </summary>
/// <param name="iter"> the number of the current iteration </param>
/// <param name="oldFlights"> vector of flights in old iteration </param>
/// <param name="newFlights"> vector of flights in current iteration </param>
void WindowSort::drawCircles(int iter, std::vector<Flight>& oldFlights, std::vector<Flight>& newFlights)
{
    int x = 120;  // x coordinate of the point of the first circle
    int y = 30;   // y coordinate of the point of the first circle

    // first draw new circles
    int x1 = x + 30 * iter;
    Circle* point0 = new Circle(Point(x1, y), 5);
    Circle* point1 = new Circle(Point(x1, y+30), 5);
    Circle* point2 = new Circle(Point(x1, y+60), 5);
    Circle* point3 = new Circle(Point(x1, y+90), 5);
    Circle* point4 = new Circle(Point(x1, y+120), 5);
    Circle* point5 = new Circle(Point(x1, y+150), 5);
    Circle* point6 = new Circle(Point(x1, y+180), 5);
    Circle* point7 = new Circle(Point(x1, y+210), 5);
    Circle* point8 = new Circle(Point(x1, y+240), 5);
    Circle* point9 = new Circle(Point(x1, y+270), 5);

    attach(*point0);
    attach(*point1);
    attach(*point2);
    attach(*point3);
    attach(*point4);
    attach(*point5);
    attach(*point6);
    attach(*point7);
    attach(*point8);
    attach(*point9);

    //second draw lines
    for (int i = 0; i < oldFlights.size(); i++) {
        if (oldFlights[i] == newFlights[i]) {             // straight line
            Line* line = new Line(Point(x1-30, y+i*30), Point(x1, y + i * 30));
            line->set_color(Color::black);
            attach(*line);
        }
        else {
            for (int j = 0; j < newFlights.size(); j++) {   // curve line
                if (oldFlights[i] == newFlights[j]) {
                    Line* line = new Line(Point(x1 - 30, y + i * 30), Point(x1, y + j * 30));
                    line->set_color(Color::black);
                    attach(*line);
                }
            }
        }
    }

    waitForButton();
}