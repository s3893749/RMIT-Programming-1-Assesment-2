//**** SET PACKAGE ****\\
//Java packages are a way to uniquely name classes in a capacity that removes class naming conflicts, for example this project
//is named under rmit.cosc2135.assessment2 whilst the first assessment was Packaged under rmit.cosc2135.assessment1.
package com.jackgharris.rmit.cosc2135.controllers;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.core.Array;
import com.jackgharris.rmit.cosc2135.core.WhatsAppConsoleEdition;
import com.jackgharris.rmit.cosc2135.models.MessageModel;
import com.jackgharris.rmit.cosc2135.models.UserModel;
import com.jackgharris.rmit.cosc2135.views.DashboardView;


//**** CLASS START ****\\
//Now we have imported our classes and declared our package name space we start our class contents
public class DashboardController{

    //Private Class Variables
    //-------------------------------------------------------------------------------------------
    //MessageModel private variable store our instance of the Messaging Model object, this is used to load, save and view
    //users messages.
    private final MessageModel messageModel;
    //UserModel private variable is used to retrieve all our users usernames and display them in the message user selector view
    private final UserModel userModel;
    //Declare our view
    private final DashboardView view;
    //declare our instance of the main application
    private final WhatsAppConsoleEdition whatsAppConsoleEdition;
    //declare our current view string
    private String currentView;
    //declare our private array request instance variable, stores all data that is sent back from the view
    private Array request;


    //**** DASHBOARD CONTROLLER CONSTRUCTOR METHOD ****\\
    //This method initialises all our protected and private variables that are required in this controller, in the case
    //of models it will load the model from the WhatsAppConsoleEdition, this is what allows us to have a single instance of each model.
    public DashboardController(WhatsAppConsoleEdition whatsAppConsoleEdition, UserModel userModel, MessageModel messageModel) {

        //initialize the request array with a new Custom array set to accepts the String.class and children of it
        this.request = new Array(String.class);
        //initialize the whatsAppConsoleEdition that's been parsed in via the constructor to the class variable of whatsAppConsoleEdition, this allows us
        //to call whatsAppConsoleEdition functions such as update view.
        this.whatsAppConsoleEdition = whatsAppConsoleEdition;

        //initialize the user model to the singleton instance of our user model that we retrieve from our WhatsAppConsoleEdition,
        //Note this also needs to be Cast into a UserModel from the default Model Parent, denoted by (Cast Type)
        this.userModel = userModel;

        //initialize the messageModel to our singleton instance of the message model that is retrieved from the whatsAppConsoleEdition
        //Note just like our UserModel we are casting this into the correct instance type
        this.messageModel = messageModel;

        //initialize our protected view variable to a new instance of our Dashboard view, views do not get loaded from the
        //WhatsAppConsoleEdition as views only display data and return a request including a input string, they do not contain data that
        //might need to be shared across Controller like our models.
        this.view = new DashboardView();

        //finally we set our currentView protected variable for this controller to our home page, this is the view that is load
        //when the controller is first called.
        this.currentView = "home";
    }

    //**** PROCESS INPUT METHOD ****\\
    //this is our main processing method, its job is to take the input provided by the reviews in the main this.request variable
    //and perform any model calls required to for fill the request of that view. We also first extract the user input from the
    //request at the start as well as create our response array ready to be returned back to the view.
    public void processInput() {
        //First we set the input string to the request input string as provided by the View
        String input = (String) this.request.getValue("input");

        //Secondly we create our response array that we will be returning back to the view
        Array response = new Array(String.class);

        //**** HOME VIEW PROCESSING ****\\
        //this if statement process the input logic if our current view is our home view
        if (this.currentView.matches("home")){

            //step 1 we check if the user input matches 1, if so then we proceed
            if (input.matches("1")) {
                //set the current view to all users view for this controller
                this.currentView = "allusers";
                //add the current user to the response to be send back to the view
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(),"currentUser");
                //add the list of all users to the response to send back to the view
                response.add(this.userModel.getAllUsers(),"users");
                //finally rerender our view and parse the response we have created
                this.whatsAppConsoleEdition.updateView(response);

            //step 2 we check if our input matches 2, that would indicate we logout and proceed with the logout logic
            }else if(input.matches("2")){
                //set the current user back to null in the main application whatsAppConsoleEdition
                this.whatsAppConsoleEdition.setCurrentUser(null);
                //set the active controller back to the login controller
                this.whatsAppConsoleEdition.setActiveController("login");
                //parse the successfully logged out message back to the view by adding it to the response with the key "logout-message"
                response.add("Successfully Logged out","logout-message");
                //parse a redirect trigger back to the login controller to cause it to redirect to the view specified
                response.add("welcome","redirect");
                //finally call the whatsAppConsoleEdition update view and parse it this response
                this.whatsAppConsoleEdition.updateView(response);

                //else if we reach this statement then the user has not input either 1 or 2 from the options then they have entered
                //a invalid option, in that case we rerender the view and parse a error
            }else {
                //add the invalid selection error to the response
                response.add("invalid selection", "error");
                //recall our view update and parse our build response
                this.updateView(response);
            }
        }

        //**** ALL USERS VIEW PROCESSING ****\\
        //process the logic and code if our view is that of allusers
        if(this.currentView.matches("allusers")){

            //check for a user input of 2, if so this indicates they wish to return back to the home page, this is like a back button
            if(input.matches("2")){
                //set the current view to home
                this.currentView = "home";
                //recall the whatsAppConsoleEdition updateview method and parse the blank response
                this.whatsAppConsoleEdition.updateView(response);

            //else we check if they have entered one, if so this indicates they wish to select a user to message
            }else if(input.matches("1")){
                //add the current user to the response
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(),"currentUser");
                //parse all our users list to the response
                response.add(this.userModel.getAllUsers(),"users");
                //set the current view to our select users view
                this.currentView = "selectUser";
                //finally reparse our response to the view and call the whatsAppConsoleEdition update view method
                this.whatsAppConsoleEdition.updateView(response);

            //else if we have reached this stage of the code it indicates the user did you select either of the valid options
            //so we redraw the view as it was and parse a invalid option selected error
            }else {
                //parse our invalid option selected error to the response
                response.add("invalid option selection","error");
                //parse our current user to the response
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(),"currentUser");
                //parse all our users list to the response
                response.add(this.userModel.getAllUsers(),"users");
                //finally recall our whatsAppConsoleEdition updateview method and parse this build response.
                this.whatsAppConsoleEdition.updateView(response);
            }
        }

        //**** SELECT USER VIEW PROCESSING ****\\
        //process the logic for the user selection view, this is the last view shown before we open our message view
        if(this.currentView.matches("selectUser")){
            //check if the user has entered a valid username, if so proccede if not skip.
            if(this.userModel.checkUsername(input)){
                //set the current view to the message view
                this.currentView = "message";
                //add the current user to the response to be send to the view
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), "currentUser");
                //add the message target to the response to be send to the view, this is the username of the user your messaging
                response.add(input, "messageTarget");
                //call our getMessages method in our messagesModel class, this takes in our user, target user and response and
                //adds the messages to the response before returning it.
                response = this.messageModel.getMessages(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), input, response);
                //finally we recall our updateview method in the whatsAppConsoleEdition and parse this build response
                this.whatsAppConsoleEdition.updateView(response);
            }else{
                //else this would indicate we have not selected a valid username and so we parse the invalid username selected error to the respone
                response.add("invaid username selected","error");
                //and recall this same view with that error parsed
                this.whatsAppConsoleEdition.updateView(response);
            }
        }

        //**** MESSAGE VIEW PROCESSING ****\\
        //finally in our views we come to the message view, this is the view that displays messages to the user regarding the
        //current message chain.
        if(this.currentView.matches("message")){
            //check if we have any errors in our message model
            if(this.messageModel.getErrors().arrayKeyExists("error")){
                response.add(this.messageModel.getErrors().getValue("error"),"error");
            }
            //firstly we check if the user wants to return back home by pressing one
            if (input.matches("1")) {
                //if so set the current view to home
                this.currentView = "home";
                //recall our whatsAppConsoleEdition updateView and parse this response
                this.whatsAppConsoleEdition.updateView(response);

            //else we check if they press two, this would indicate they want to send a message to the selected user
            }else if(input.matches("2")){
                //add the send message tag with the value of true to the response, this is used by the view to render differnt text
                //this flag is also needed as it indicates that we when present we need to process a send message not just display the message chain.
                response.add("true","{{send-message}}");
                //add the message target to the view, this was parsed by the select user request and is identified by the key messageTarget
                response.add(request.getValue("messageTarget"), "messageTarget");
                //call our getMessages method in our messagesModel class, this takes in our user, target user and response and
                //adds the messages to the response before returning it.
                response = this.messageModel.getMessages(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), (String) request.getValue("messageTarget"), response);
                //then to finish up we parse this response to our whatsAppConsoleEdition updateView and recall the method
                this.whatsAppConsoleEdition.updateView(response);
            }else {
                //else if they have not option 1 or 2 we check if we have the send message flag send as well, if so then we call the messageModel, sendMessage method
                //and parse our required params
                if (this.request.arrayKeyExists("{{send-message}}")) {
                    //if true call our newMessage method and parse it the current user, targetUser, and message AKA the input
                    this.messageModel.newMessage(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), (String) this.request.getValue("messageTarget"), input);
                }
                //else if we dont have that flag we are going to assume a invalid section has been made and reparse the data to the view
                //with the error invalid section
                //parse the current user again
                response.add(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), "currentUser");
                //parse the target user again
                response.add(request.getValue("messageTarget"), "messageTarget");
                //parse the preexisitng messages from the model
                response = this.messageModel.getMessages(this.whatsAppConsoleEdition.getCurrentUser().getUsername(), (String) request.getValue("messageTarget"), response);
                //recall our updateview method and parse this build response
                this.whatsAppConsoleEdition.updateView(response);
            }
        }
    }

    //**** UPDATE VIEW METHOD ****\\
    //This method is called if this controller is active and any controller has called the this.app.updateView method, this triggers
    //a re rendering of the view in the applicable controller, Update View takes a response array of strings as a input but can also
    //receive a null input if no data needs to be send to the front end.
    public void updateView(Array response) {
        //Step 1:
        //firstly we check if we have a valid response or if no response was parsed
        if(response == null){
            //if no response has been parsed then we create a dummy array for Strings that is sent to the relevant review, this is
            //important as the views may have variable display options that check if a error key exists before displaying it, parsing
            //null with out initializing this blank array will cause a fatal error.
            response = new Array(String.class);
        }
        //Step 2:
        //Next we check if a redirect key has been parsed to this controller via the response, this is marked with the key "redirect"
        //if so we set the current view to the value of the redirect.\
        if(response.arrayKeyExists("redirect")){
            this.currentView = (String) response.getValue("redirect");
        }
        //Step 3:
        //Now we have initialized the response if null and checked for a redirect we can render the current view for this Controller,
        //Views are broken down into subviews with in a view class, for example here our login view has, welcome, login, loginPassword
        //and register subviews. Each view also returns the the input string from the console and sets it to the class wide input variable.

        //Step 3A: render the home view and get user input request
        if(this.currentView.matches("home")){
            //set the current request to the result of the home view and parse the response build by a processor
            this.request = ((DashboardView) this.view).home(response);
            //recall the process input method to calculate any new input from the user
            this.processInput();
        }

        //Step 3B: render the all users view and get input request
        if(this.currentView.matches("allusers")){

            //set the current request to the result of the allusers view and parse the response build by a processor
            this.request = ((DashboardView) this.view).allUsers(response);
            //recall the process input method to calculate any new input from the user
            this.processInput();
        }

        //Step 3C: render the select user view and get user input request
        if(this.currentView.matches("selectUser")){

            //set the current request to the result of the selectUser view and parse the response build by a processor
            this.request = ((DashboardView) this.view).selectUser(response);
            //recall the process input method to calculate any new input from the user
            this.processInput();
        }

        //Step 3D: render the message view and get user input request
        if(this.currentView.matches("message")){

            //set the current request to the result of the message view and parse the response build by a processor
            this.request = (((DashboardView) this.view)).message(response);
            //recall the process input method to calculate any new input from the user
            this.processInput();
        }
    }
}
//END OF CLASS