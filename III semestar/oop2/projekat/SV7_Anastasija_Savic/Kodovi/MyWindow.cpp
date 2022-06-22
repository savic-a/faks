//============================================================================
// Name        : MyWindow.cpp
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : Implementation of the MyWindow class
//============================================================================

#include "MyWindow.h"


MyWindow::MyWindow(Point xy, int w, int h, const string& title) :
    Window(xy, w, h, title),
    inputFile(Point(300, 30), 170, 25, ""),
    outputFile(Point(300, 70), 170, 25, ""),
    critSort(Point(300, 110), 170, 25, ""),
    algorithm(Point(300, 150), 170, 25, ""),

    inputText(Point(70, 45), "Putanja ulazne datoteke: "),
    outputText(Point(70, 85), "Putanja izlazne datoteke: "),
    criterionText(Point(100, 125), "Kriterijum sortiranja: "),
    algText(Point(100, 165), "Algoritam sortiranja: "),

    t1(Point(50, 250), "KRITERIJUM  destinacija -- > 0, polazak -- > 1, broj leta -- > 2, broj izlaza -- > 3"),
    t2(Point(130, 290), "ALGORITAM  selection sort -- > 0, quick sort -- > 1"),

    start(Point(250, y_max()-50), 100, 25, "Start", cbShowButton)
    //showButtonPushed(false)
{

    attach(inputFile);
    attach(outputFile);
    attach(critSort);
    attach(algorithm);

    inputText.set_color(Color::black);
    outputText.set_color(Color::black);
    criterionText.set_color(Color::black);
    algText.set_color(Color::black);
    t1.set_color(Color::black);
    t2.set_color(Color::black);
    t1.set_font_size(15);
    t2.set_font_size(15);

    attach(inputText);
    attach(outputText);
    attach(criterionText);
    attach(algText);
    attach(t1);
    attach(t2);

    attach(start);
}

MyWindow::~MyWindow() {}

bool MyWindow::waitForButton()
{
    show();
    Fl::run();
    return true;
}


// callback function
void MyWindow::cbShowButton(Address, Address pw)
{
    reference_to<MyWindow>(pw).showButtonHandler();
}

// do when the start button is pressed
void MyWindow::showButtonHandler()  
{
    hide();
}

// getters
std::string MyWindow::getInputText()
{
    return inputFile.get_string();
}

std::string MyWindow::getOutputText()
{
    return outputFile.get_string();
}

std::string MyWindow::getCriterionText()
{
    return critSort.get_string();
}

std::string MyWindow::getAlgText()
{
    return algorithm.get_string();
}