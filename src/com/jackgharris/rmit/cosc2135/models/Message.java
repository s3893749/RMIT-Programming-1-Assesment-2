//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.models;

//**** START CLASS ****\\
//this is the message object class, these are created based on entries in the CSV and stored in the MessageModel class
public class Message {

    //set private final variables, these are instance variables that are final as they are set once by the constructor and never changed
    //String from variable, this is the username of the sender
    private final String from;
    //String to variable, this is the username the message to send to
    private final String to;
    //String message, this is the message content
    private final String message;
    //Long dataTime this is the timestamp the message was sent at
    private final long dateTime;

    //**** MESSAGE CONSTRUCTOR ****\\
    //requires all the parameters of from, to, message and dateTime to be parsed on creation
    public Message(String from, String to, String message,long dateTime){
        //set the class variables to the parsed constructor parameter inputs
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateTime = dateTime;
    }

    //**** GET MESSAGE GETTER ****\\
    //returns the message String
    public String getMessage(){
        return this.message;
    }

    //**** GET SENDER GETTER ****\\
    //returns the message sender username
    public String getSender(){
        return this.from;
    }

    //**** GET RECIVER GETTER ****\\
    //returns the message reciver username
    public String getReciver(){
        return this.to;
    }

    //**** GET DATA TIME GETTER ****\\
    //returns the datetime that the message was sent
    public Long getDateTime(){
        return this.dateTime;
    }

    //**** CHECK SENDER METHOD ****\\
    //accepts the username of a send and checks to see if this message was sent by that user
    public boolean checkSender(String user){
        //set the outcome to false
        boolean outcome = false;
        //check if the from matchs the provided user
        if(this.from.matches(user)){
            //if so set the outcome to true
            outcome = true;
        }
        //finally return the comeout to the caller
        return outcome;
    }

    //**** CHECK RECIVER METHOD ****\\
    //accepts a username and checks if that user was the reciver of this message
    public boolean checkReciver(String user){
        //create the outcome and set it to false
        boolean outcome = false;
        //check if the to variable matchs the provided user
        if(this.to.matches(user)){
            //if so set the outcome to true
            outcome = true;
        }
        //finally return the outcome to the sender
        return outcome;
    }

}
