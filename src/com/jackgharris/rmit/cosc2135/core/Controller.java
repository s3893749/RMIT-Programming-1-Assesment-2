//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.core;

//**** CLASS START ****\\

//Abstract class justification:
//abstract classes simple indicate that a abstract method must be implement or exist in a child class, this means any
//controllers that are children of this must have both a processInput() method and a updateView() method

public abstract class Controller{

    //Create our protected variables

    //Protected variables justification:
    //Protected variables are simply private variables that are inherited by a child class, so each controller
    //will receive access to its own version of these named variables to manipulate and use.

    //protected Kernel variable stores the kernel instance that's parsed in the constructor
    protected Kernel kernel;
    //protected view instance stores the controller view thats created with in the constructor
    protected View view;
    //protected String current view, stores the created view object that is created by the child controller
    protected String currentView;
    //protected Request object, stores the request data as parsed back from the view to the controller
    protected Array request;

    //Abstract processInput() method, forces all other controller to have this method, this method is justified and
    //explained in both the comments of the LoginController and the DashboardController.
    public abstract void processInput();

    //Abstract updateView() method, forces all other controller to have this method, this method is justified and
    //explained in both the comments of the LoginController and the DashboardController. accepts the response from the
    //controller as a parameter.
    public abstract void updateView(Array response);

}
