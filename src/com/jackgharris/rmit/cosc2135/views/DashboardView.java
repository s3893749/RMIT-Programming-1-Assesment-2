//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.views;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.core.CustomArray;
import com.jackgharris.rmit.cosc2135.core.TextColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//**** START CLASS ****\\
public class DashboardView{

    //declare our buffered reader variable
    BufferedReader br;

    public DashboardView(){
        //initialize our buffered reader variable to a new instance of the BufferedReader with a InputSteamReader and system in parsed
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    //**** HOME VIEW ****\\
    //This is the home page once the user is logged in
    public CustomArray home(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the view title of welcome and append the usersname of the current user
        this.showTitle("Welcome: "+response.getValue("username"));

        //show a successfully logged in alert with the color green
        this.showAlert("Successfully Logged In!",TextColors.textGreen);

        //check if we have a import success key
        if(response.arrayKeyExists("importSuccess")){
            this.showAlert((String) response.getValue("importSuccess"), TextColors.textGreen);
        }

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so show the errors and set the color to red for the error alert
            this.showAlert((String) response.getValue("error"), TextColors.textRed);
        }

        //show the main menu
        System.out.println("1) Message user");
        System.out.println("2) Logout");
        if(response.arrayKeyExists("isAdmin")) {
            System.out.println("3) Import from file");
        }
        System.out.println("\n");

        //show the footer
        System.out.println("Whats App Console Edition, Created by Jack Harris");

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in


        //finally return the request back to the controller
        return request;
    }

    //**** ALL USERS VIEW ****\\
    //this view shows you a list of all the users in the application that you can message
    public CustomArray allUsers(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title for this page as all users:
        this.showTitle("All Users:");

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so show the errors and set the color to red for the error alert
            this.showAlert((String) response.getValue("error"), TextColors.textRed);
        }

        //explode the list of users returned by the controller to an array
        String[] users = String.valueOf(response.getValue("users")).split(",");

        //use a while loop to print out all the users from that list excluding the current user who is logged in
        //create i = 0 to act as a loop acount
        int i = 0;
        while(i < users.length){
            //check to see the user is not the logged in user
            if(!users[i].matches((String) response.getValue("currentUser"))){
                //print of the username
                System.out.println("- "+users[i]);
            }
            //increase the loop counter
            i++;
        }

        //show message to the user
        System.out.println(" ");
        //1 to message a user
        System.out.println("1) message user");
        //2 to return to the dashboard
        System.out.println("2) return to dashboard");

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in


        //finally return the request back to the controller
        return request;
    }

    //**** SELECT USER VIEW ****\\
    //this view asks you to select a user to message, returns an error if its not a valid username
    public CustomArray selectUser(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title for this page as all users:
        this.showTitle("Select User to Message: ");

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so show the errors and set the color to red for the error alert
            this.showAlert((String) response.getValue("error"), TextColors.textRed);
        }

        //ask the user to enter a valid username to message
        this.showAlert("Please enter a valid username to message: ", TextColors.textGreen);

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in


        //finally return the request back to the controller
        return request;
    }

    //**** MESSAGE VIEW ****\\
    //this view allows you to view and write messages to the selected user
    public CustomArray message(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //re-add the message target to the request to be used in the next response if needed
        request.add(response.getValue("messageTarget"),"messageTarget");

        //show the title messging and append the target user of your message
        this.showTitle("Messaging @"+response.getValue("messageTarget"));

        //set the default has message boolean to false
        boolean hasMessages = false;

        //create a while loop to see if we have any messages returned from the controller
        //use i as a counter to count upto the response length
        int i = 0;
        while(i < response.length()){
            //if the key contains the message flag, then we set has messages to true and output the message
            if(response.getKeys()[i].contains("{{message}}")){
                //set has message to true
                hasMessages = true;
                //output that message
                System.out.println(response.getValues()[i]);
            }
            //increase our loop counter to the next response entry
            i++;
        }

        //check if we have a message, if not then show the no message alert
        if(!hasMessages){
            //show alert no messages with color orange
            this.showAlert("no messages, please enter your first message!",TextColors.textYellow);
        }//else skip

        //check if the controller response includes send message, if so that indicates its expecting a message from us
        if(!response.arrayKeyExists("{{send-message}}")) {
            //if we are not sending a message then show the default message screen
            System.out.println("\n1) close messages");
            System.out.println("2) new message");
        }else{
            //add the send message flag back for the controller to see
            request.add("true","{{send-message}}");
            //ask the user to type there new message
            System.out.println("\nType message and press enter to send");
        }

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in


        //finally return the request back to the controller
        return request;
    }

    //**** IMPORT MESSAGES FROM EXISTING FILE ****\\
    //This method allows you to import messages from an existing file into this application
    public CustomArray importMessages(CustomArray response){

        //create the new request array, this is returned back from the view to the controller
        CustomArray request = new CustomArray(String.class);

        //show the title messages and append the target user of your message
        this.showTitle("Import Messages: ");

        //ask the user to enter the file path of the message they wish to import
        System.out.println("\nPlease enter the file path of the messages.csv you wish to import");

        //check if we have any errors
        if(response.arrayKeyExists("error")){
            //if so show the errors and set the color to red for the error alert
            this.showAlert((String) response.getValue("error"), TextColors.textRed);
        }

        try {
            //try setting the input to the input key value in the request response
            request.add(this.br.readLine(),"input");
        } catch (IOException e) {
            //if that fails for any reason add the error to the request instead under the error key
            request.add(e.toString(),"error");
        }      //try and get the input from the user via the buffered reader using system.in

        return request;

    }

    //**** SHOW ALERT HELPER METHOD ****\\
    //Accepts a string to display and the textColor of the alert
    private void showAlert(String alert, String textColor){
        //boarder + textcolor appended
        System.out.println(textColor+"_____________________________________\n");
        //display alert
        System.out.println("Alert: "+alert);
        //end boarder and reset colors appended
        System.out.println("_____________________________________\n"+TextColors.reset);
    }

    //**** SHOW TITLE HELPER METHOD ****\\
    //Accepts the view title in string form
    private void showTitle(String title){
        //display the blue border indicating a new view has loaded
        System.out.println(TextColors.backgroundBlue+"\n"+TextColors.reset);
        //print the title as provided by a sting
        System.out.println(TextColors.textBlueBold+title+"\n"+TextColors.reset);
    }
}
