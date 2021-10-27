//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.core;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.controllers.*;
import com.jackgharris.rmit.cosc2135.models.*;

//**** START CLASS ****\\
public class WhatsAppConsoleEdition {

    //Private Class Variables
    //-------------------------------------------------------------------------------------------
    //Declare the active controller string variable, this is used to keep track of what controller is currently active
    private String activeController;
    //Declare our user variable, this is used to keep track of what user is currently logged in
    private User user;
    //declare final login controller variable, this is created by a controller and is not changed so it is declared as final
    private final LoginController loginController;
    //declare final dashboard controller variable, this is created by a controller and is not changed so it is declared as final
    private final DashboardController dashboardController;

    //**** CONSTRUCTOR ****\\
    public WhatsAppConsoleEdition(){
        //Models are done this way so we only have single instances of a model in the event two controllers both need the same model
        //create our single instances of our models these are parsed to the controllers if needed in there constructors
        UserModel userModel = new UserModel();
        MessageModel messageModelModel = new MessageModel();

        //initialize our controllers to new objects and parse them the main application (this) and the model or models they need
        this.loginController = new LoginController(this,userModel);
        this.dashboardController = new DashboardController(this, userModel,messageModelModel);

        //set the active controller for this application to the login screen. (default start controller)
        this.setActiveController("login");
        this.updateView(null);
    }

    //**** SET ACTIVE CONTROLLER ****\\
    //sets the active controller based on a string
    public void setActiveController(String key){
        //set this class variable to the key provided
        this.activeController = key;
    }

    //**** UPDATE VIEW ****\\
    //calls the update method of the view that is currently active with in the program
    public void updateView(CustomArray response){
       //check if the active view matches the login view
       if(this.activeController.matches("login")){
           //call the updateView and parse the response
            this.loginController.updateView(response);

       //check if the active controller matches the dashboard
       }else if(this.activeController.matches("dashboard")){
           //call the update view method and parse the response
            this.dashboardController.updateView(response);
       }else{
           //else echo valid controller selection
           System.out.println("ERROR: invalid controller selected");
       }
    }

    //**** SET CURRENT USER ****\\
    //this method sets the current user to the parsed user
    public void setCurrentUser(User user){
        this.user = user;
    }

    //**** GET CURRENT USER ****\\
    //this method returns the current user from the this.user private variable
    public User getCurrentUser(){
        return this.user;
    }

    //**** MAIN STATIC VOID ****\\
    //tells java to start the program here and creates a new instance of the WhatsAppConsoleEdition main class
    public static void main(String[] args){
        new WhatsAppConsoleEdition();
    }

}
