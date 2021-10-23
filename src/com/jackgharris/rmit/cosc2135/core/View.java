//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.core;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import java.io.BufferedReader;
import java.io.InputStreamReader;

//**** CLASS START ****\\
//Parent class justification, I am using a view parent class to allow me to store each view in the same protected view
//variable in the controller for simplicity, this further allows me to create and store a instance of the buffered reader
//to get system input, i can declare this once here and use it in any view.
public class View {

    //protected final buffered reader instance
    protected final BufferedReader br;

    //Constructor, creates new instance of the bufferedReader class and parses the inputStreamReader form system in as input
    //this gets the input from the terminal or console window from the user.
    public View() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }
}
