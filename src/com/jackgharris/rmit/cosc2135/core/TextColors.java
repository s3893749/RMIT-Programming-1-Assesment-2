//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.core;

//**** CLASS START ****\\
//this text colors class is a class that only contains static final variables, these can be used by the views to color the
//text before printing it to the console, as these a final they cannot be changed and as they are static they can be access
//with out the need to create a instance of this class
public class TextColors {

    //set the text color to red by appending this to the start of a string
    public static final String textRed = "\033[0;31m";
    //set the text color to bold blue by appending this to the start of a strings
    public static final String textBlueBold = "\033[1;97m";
    //set the text color to green by appending this to the start of a string
    public static final String textGreen = "\033[0;32m";
    //set the text color to yellow by appending this to the start of a string
    public static final String textYellow = "\u001B[33m";
    //set the background color to be by appending this to the start of a string
    public static final String backgroundBlue = "\033[0;104m";

    //reset string, this is appended to the end of colored text blocks to remove and reset the coloring
    public static final String reset = "\033[0m";


}
