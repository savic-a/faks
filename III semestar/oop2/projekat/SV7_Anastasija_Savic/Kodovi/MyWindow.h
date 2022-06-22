//============================================================================
// Name        : MyWindow.h
// Author      : Savic Anastasija
// Date        : 13.01.2022.
// Description : 
//============================================================================

#pragma once

#include "GUI.h"    
#include "Window.h"
#include "Flight.h"

using namespace Graph_lib;

struct MyWindow: Window
{
    In_box inputFile;
    In_box outputFile;
    In_box critSort;
    In_box algorithm;

    Text inputText;
    Text outputText;
    Text criterionText;
    Text algText;

    Text t1;
    Text t2;

    Button start;

public:
    MyWindow(Point xy, int w, int h, const string& title);
    ~MyWindow();

    bool waitForButton(); // simple event loop
    void showButtonHandler();
    std::string getInputText();
    std::string getOutputText();
    std::string getCriterionText();
    std::string getAlgText();

    static void cbShowButton(Address, Address);  // callback for start button
    
    //Button next_button;     // the "next" button
    //bool button_pushed;     // implementation detail

    //static void cb_next(Address, Address); // callback for next_button
    //void next();            // action to be done when next_button is pressed
};
