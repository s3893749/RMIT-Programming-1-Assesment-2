//**** SET PACKAGE ****\\
//Java packages are a way to uniquely name classes in a capacity that removes class naming conflicts, for example this project
//is named under rmit.cosc2135.assessment2 whilst the first assessment was Packaged under rmit.cosc2135.assessment1.
package com.jackgharris.rmit.cosc2135.controllers;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.core.Array;
import com.jackgharris.rmit.cosc2135.core.Controller;
import com.jackgharris.rmit.cosc2135.core.Kernel;
import com.jackgharris.rmit.cosc2135.models.UserModel;
import com.jackgharris.rmit.cosc2135.views.LoginView;

//**** CLASS START ****\\
//Now we have imported our classes and declared our package name space we start our class contents
public class LoginController extends Controller{

    //Protected variables inherited from parent, these apply to all controllers and are a standard
    //-------------------------------------------------------------------------------------------
    //The Kernel variable stores the instance of core instance of the kernel.
    //**** protected kernel kernel;
    //The protected View instance stores our instance of our View, this needs to be created inside the
    // controllers constructor.
    //**** protected View view;
    //Our protected current view variable string, this variable is our memory and tells the controller and
    // the main system what view to render.
    //**** protected String currentView;
    //Finally we have a request variable, this represents the inbound request from the view and stores the
    // main user input, as well as any additional information that's required by the model or controller.
    //**** protected Array request;

    //Private Class Variables
    //-------------------------------------------------------------------------------------------
    //firstly we declare our Model for this controller, this is our Authentication Model,
    // that retrieves our array of users and validates user logins
    private final UserModel model;

    //**** LOGIN CONTROLLER CONSTRUCTOR METHOD ****\\
    //Our loginController constructor method accepts our main app object and is run at the creation of the
    //LoginController object, in this method we setup and create our View and Model object instances as well
    //as set our default current view and request array.
    public LoginController(Kernel kernel) {

        //set the parsed Kernel instance from the kernel to the protected this.kernel variable
        this.kernel = kernel;

        //initialize the request array to a new Array instance and set the array object storage type, in this case strings
        // (This is my own custom array class written for this assessment)
        this.request = new Array(String.class);

        //initialize a instance of the the LoginView (Our view for this controller)
        this.view = new LoginView();

        //create the model and set it to the protected model variable inherited from the Parent Controller class
        this.model = (UserModel) this.kernel.getModel("userModel");

        //set our default currentView method, this the subview that is loaded by default when the controller is called with out any
        //redirect variable being parsed. In this case we are setting it to our welcome subview
        this.currentView = "welcome";

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

        //check if the model has throw any error, if so parse the error to the front end and display it
        if(this.model.getErrors().arrayKeyExists("error")){
        response.add(this.model.getErrors().getValue("error"), "error");
        }

        //**** WELCOME VIEW PROCESSING ****\\
        //Now we have created our response and loaded our input from the request we can proceed to process our view code
        //First we check to see if the currentView is the welcome view, if so execute this code
        if (this.currentView.matches("welcome")) {

            //now we have selected our welcome view, we can check for the correct user input, in this case we are checking if
            //the number 1 or 2 has been entered, for our first check if we enter one we will switch our current view to our login page
            //and then recall the this.app.update with null response as we have nothing else to provide back to the user
            if (input.matches("1")) {

                //set our current view to the login view
                this.currentView = "login";
                //update the application to show the new view, and provide it the response
                this.kernel.updateView(response);

            //next we check if our input matches 2, if so that means the user would like to register for a new account instead of logging in.
            } else if (input.matches("2")) {
                //firstly we set the current view to our register sub view
                this.currentView = "register";
                //secondly we recall the app update view method to rerender the program with the default response as the view requires no response data
                this.kernel.updateView(response);

            } else {
                //finally we have our last else check, this catches the input in the even the user has not selected a valid input,
                //now before we rerender the page we add a response error to parse back to the front end by performing response.add
                response.add("invalid selection","error");
                //rerender view with response packaged into the error
                this.kernel.updateView(response);
            }
        }

        //**** LOGIN VIEW PROCESSING ****\\
        //Now if the current view matches the login view we process the inputs and choices for the login page
        if (this.currentView.matches("login")) {

            //Step 1 we parse the input to the model checkUsername() function, this returns true or false to depending on if the input
            //provided was matches a valid user account or not
            if (this.model.checkUsername(input)) {

                //if so we change our view to the login view, and parse the provided username to the response to be packaged with our
                //next view request
                this.currentView = "loginPassword";

                //add validated username to response to be resent with next request including the password
                response.add(input, "username");

            } else {
                //else it means no valid username as been provided that matches a user account, so we add our error message to the response
                //and finally rerender the view to the user.
                response.add("invalid username","error");
            }
            //finally rerender view
            this.kernel.updateView(response);
        }

        //**** LOGIN PASSWORD VIEW PROCESSING ****\\
        //This is the second sub view shown in the login stage, here we get the password entered by the user as well as the validated
        //username.
        if (this.currentView.matches("loginPassword")) {

            //Firstly we call the model check password method, and provide it the input from the new request and the username, saved
            //from the prior request
            if (this.model.checkPassword((String) this.request.getValue("username"), input)) {
                //if this is true we set the current user to that user by requesting the user from the Authentication model
                this.kernel.setCurrentUser(this.model.getUser((String) this.request.getValue("username")));
                //next we set the active controller to the dashboard
                this.kernel.setActiveController("dashboard");
            } else {
                //else we parse the username back to the front end to be send with the next request
                response.add(this.request.getValue("username"),"username");
                //now we throw the error back tot he front end to be rendered, in this case its invalid password
                response.add("invalid password","error");
            }
            //finally we update our view and parse the response we have created
            this.kernel.updateView(response);
        }

        //**** REGISTER USER VIEW PROCESSING ****\\
        //this processing handles the input and connection from the view to the user registration model
        if (this.currentView.matches("register")) {
            //step 1 we set what step of the registration we are currently on
            int registrationStep;
            //check if we have a registration step value parsed from the view, if so we set that to our step
            if(this.request.arrayKeyExists("registrationStep")) {
                //parse the registration step from the via to a interger
                registrationStep = Integer.parseInt((String) this.request.getValue("registrationStep"));
            }else{
                //else if the view has not specified a registration step we assume its 0 and start from there.
                registrationStep = 0;
            }

            //REGISTRATION STEP 0 PROCESSING
           if(registrationStep == 0){
               //firstly on step 0 we are processing the username input, we validate this by calling our model validate username to check we dont have a duplicate
                if(this.model.validateNewUsername(input)){
                    //if that succeeds and we have a valid username we add it to response
                    response.add(input,"username");
                    //then we increase our registration step to the next step
                    registrationStep++;
                    //finally we add the that step to response so the front end can access it and display the correct view.
                    response.add(String.valueOf(registrationStep),"registrationStep");

                }else{
                    //else if our validate user name fails we set the step to the current step and do not increase
                    response.add(String.valueOf(registrationStep),"registrationStep");
                    //finally we add our error to the response, in this case its invalid username provided
                    response.add("invalid username provided!\nUsernames must be unique and not blank","error");
                }
                //lastly we rerender the view and parse it the response we have created.
               this.kernel.updateView(response);
           }

            //REGISTRATION STEP 1 PROCESSING
            //process the logic for step 1 of the registration process
            if(registrationStep == 1){
                //parse our user input to the model validate password method, and if it returns true succeed
                if(this.model.validatePassword(input)){
                    //if its a valid password then parse the username from the request back to the response
                    response.add(request.getValue("username"),"username");
                    //next add the new validated password to the response
                    response.add(input,"password");
                    //next increase our registration step
                    registrationStep++;
                    //finally parse the registration step to the response
                    response.add(String.valueOf(registrationStep),"registrationStep");
                }else{
                    //else if we have a invalid password then add the current registration step with out being increase
                    response.add(String.valueOf(registrationStep),"registrationStep");
                    //next we add the error message to response with the target key error, in this case no blank password
                    response.add("invalid password provided!\nPasswords cannot be blank!","error");
                }
                //finally we recall our update view method and parse it our created response
               this.kernel.updateView(response);
           }

            //REGISTRATION STEP 2 PROCESSING
            //step 2 of the registration process involves us getting the fullname from the user
            if(registrationStep == 2){
                //firstly we validate the input from the user to see if it meets the criteria of the full name validation, returns true if so
               if(this.model.validateFullname(input)){
                   //if its valid we reparse our username back to the response to be used later
                   response.add(request.getValue("username"),"username");
                   //then reparse the password back to the response to be used later
                   response.add(request.getValue("password"),"password");
                   //next we add our new validated fullname into the response
                   response.add(input,"fullname");
                   //finally we increase our registration step
                   registrationStep++;
                   //and parse it to a string to be added to our response
                   response.add(String.valueOf(registrationStep),"registrationStep");
               }else{
                   //else if our fullname validation failed we parse the step we are currently on with our increasing
                   response.add(String.valueOf(registrationStep),"registrationStep");
                   //next we add the error, in this case invalid fullnames cannot be blank
                   response.add("invalid fullname provided!\nFullnames cannot be blank!","error");
               }
               //lastly for this step we recall our updateView method and parse our creasted response
               this.kernel.updateView(response);
           }

            //REGISTRATION STEP 3 PROCESSING --FINAL STEP--
            //our final step of the registation shows the users all the info they have entered upto this point, from here they
            //can press 1 to accept and create the user or press 2 to cancel and go back to the welcome screen/
            if(registrationStep == 3){
                //firstly check for a cancel, 2 in this case
                if(input.matches("2")){
                    //if 2 was entered set the current view to welcome
                    this.currentView = "welcome";
                    //then we recall our update with method and parse our blank response
                    this.kernel.updateView(response);

                //else if they have entered 1 we will process the saving feature of the user
                }else if(input.matches("1")){
                    //set the current view back to welcome for a redirect
                    this.currentView = "welcome";
                    //create a array of values that we will use to generate the new user account
                    Array values = new Array(String.class);
                    //set the values of each array to the corresponding values we have in the last request
                    values.add(request.getValue("username"),"username");
                    values.add(request.getValue("password"),"password");
                    values.add(request.getValue("fullname"),"fullname");
                    //generate the unix time stamp for the user registration
                    values.add(String.valueOf(System.currentTimeMillis()/1000L),"registrationDate");
                    //set the admin status, false always underless changed in csv
                    values.add(String.valueOf(false),"isAdmin");
                    //finally call the model createUserAccount and parse it the values
                    this.model.createUserAccount(values);
                    //then add the signup message to the response with the correct key for the front end to show
                    response.add("new user created!","signup-message");
                    //then recall our kernel update view method and parse that response
                    this.kernel.updateView(response);
                }else{
                    //else this means we have not entered a valid selection option and we should rerender the view with
                    //the error invalid selection with the values parsed back to the view as well
                    //if its valid we reparse our username back to the response to be used later
                    response.add(request.getValue("username"),"username");
                    //then reparse the password back to the response to be used later
                    response.add(request.getValue("password"),"password");
                    //parse the fullname that was entered in the last request
                    response.add(request.getValue("fullname"),"fullname");
                    //else if our selection parse the same registion step back to the frontend
                    response.add(String.valueOf(registrationStep),"registrationStep");
                    //finally recall our update method and parse the response
                    this.kernel.updateView(response);
                }

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
        //if so we set the current view to the value of the redirect.
        if(response.arrayKeyExists("redirect")){
            this.currentView = (String) response.getValue("redirect");
        }
        //Step 3:
        //Now we have initialized the response if null and checked for a redirect we can render the current view for this Controller,
        //Views are broken down into subviews with in a view class, for example here our login view has, welcome, login, loginPassword
        //and register subviews. Each view also returns the the input string from the console and sets it to the class wide input variable.

        //Step 3 A: Render and get Input from the welcome view
        if(this.currentView.matches("welcome")) {
            //set the input to the response of the view and parse the view method the response
            this.request = ((LoginView) this.view).welcomeScreen(response);
            //call this input process for this method to process and validate the input from the user
            this.processInput();

        //Step 3 B: Render and get Input from the login view (username)
        }else if(this.currentView.matches("login")){
            //set the input to the response of the view and parse the view method the response
            this.request = ((LoginView) this.view).loginScreenUsername(response);
            //call this input process for this method to process and validate the input from the user
            this.processInput();

        //Step 3 C: Render and get Input from the login view (password)
        }else if(this.currentView.matches("loginPassword")){
            //set the input to the response of the view and parse the view method the response
            this.request = ((LoginView) this.view).loginScreenPassword(response);
            //call this input process for this method to process and validate the input from the user
            this.processInput();

        //Step 3 D: Render and get Input from the registration view
        }else if(this.currentView.matches("register")){
            //set the input to the response of the view and parse the view method the response
            this.request = ((LoginView) this.view).register(response);
            //call this input process for this method to process and validate the input from the user
            this.processInput();
        }
    }
}
