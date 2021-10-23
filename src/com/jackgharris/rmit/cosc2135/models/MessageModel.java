//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.models;

//**** IMPORT PACKAGES ****\\
//Here we import all the relevant packages that we will be referencing, calling and accessing in this class.
import com.jackgharris.rmit.cosc2135.core.Array;
import java.io.*;

//**** START CLASS ****\\
public class MessageModel{

    //Private Class Variables
    //-------------------------------------------------------------------------------------------
    //Here we declare our class wide instance variables, in this case we have two arrays, messages and errors, both are final
    private final Array messages;
    private final Array errors;

    //**** CONSTRUCTOR ****\\
    public MessageModel(){

        //initialize our messages array to a new Array of the Message class
        this.messages = new Array(Message.class);
        //initialize our errors array to a new array of the Strings class
        this.errors = new Array(String.class);

        //finally call our loadUserMessages function
        this.loadUserMessages();
    }

    //**** NEW MESSAGE METHOD ****\\
    //this method takes the input from the view parsed via the controller to create a new message,
    public void newMessage(String sender, String reciver, String message){
        //add a new message to the Array of messages, and parse it our variables, for the key set it to the {{message}} target
        //plus append the unix time in seconds to avoid conflicts
        this.messages.add(new Message(sender,reciver,message,System.currentTimeMillis()),"{{message}}"+System.currentTimeMillis());
        //finally save the userMessages to file with the private save function
        this.saveUserMessages();
    }

    //**** GET USER MESSAGE ****\\
    //this method accepts the username of the send and the username of the receiver as well as the response array, this method
    //will add all the messages to the response array and then returns it to the controller
    public Array getMessages(String userSender, String userReciver,Array response){

        //create a int counter variable represented by i
        int i =0;
        //create a while loop to loop over all the messages
        while(i < this.messages.length()){
            //check if the message was sent by the user and going to the reciver
            if(((Message)this.messages.getValues()[i]).checkSender(userSender) && ((Message)this.messages.getValues()[i]).checkReciver(userReciver)){
                //if so add it to the response
                response.add(userSender+" : "+((Message)this.messages.getValues()[i]).getMessage(),"{{message}}"+System.currentTimeMillis());
            }//else skip all other message

            //check if the message was send by the reciver and is going to the sender
            if(((Message)this.messages.getValues()[i]).checkReciver(userSender) && ((Message)this.messages.getValues()[i]).checkSender(userReciver)){
                //if so add it to the response
                response.add(userReciver+" : "+((Message)this.messages.getValues()[i]).getMessage(),"{{message}}"+System.currentTimeMillis());
            }//else skip all other messages

            //increase our counter and repeat until we have checked all messages
            i++;
        }

        //finally return the response
        return response;
    }

    //**** LOAD USER MESSAGES ****\\
    //this method loads the users messages from the message.csv file into the class this.messages array
    private void loadUserMessages() {
        //specify the path, in this case simply message.csv
        String filePath = "message.csv";

        //open a try catch loop to catch any errors
        try {
            //create a new instance of the buffered reader and parse it the file reader aimed at that file path
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            //create the String variable line
            String line;

            //process the while loop while the buffered reader is reading line with data
            while((line = br.readLine()) != null){
                //check if the line has // if so then exlucde it
                if(!line.contains("//")){
                    //else split the line by the , into a values array
                    String[] values = line.split(",");
                    //create a new message based on that values array
                    this.messages.add(new Message(values[0],values[1],values[2],Long.parseLong(values[3])),"{{message}}");
                }
            }

            //once we have loaded all that then close the buffered reader
            br.close();

        } catch (IOException e) {
            //finally if we catch a error add it to the errors array and append the e.toString error
            this.errors.add("Cannot load message.csv file, please check you have a csv file in this folder called '" +
                    "message.csv' and restart the program\nJava Error: "+e.toString(),"error");
        }

    }

    //**** SAVE USER MESSAGES ****\\
    //the save user message array is called when a new message is sent and is used to save all the messages to the file
    private void saveUserMessages(){

        //append the tag to the top of the file
        String tag = "Main Message File";

        //open a try catch block to attempt to save the files
        try {
            // Creates a FileWriter
            FileWriter file = new FileWriter("message.csv");

            // Creates a BufferedWriter and parse it the file writer
            BufferedWriter output = new BufferedWriter(file);

            //write the tags to the top of the file
            output.write("//Tags: "+tag);
            //go to a new line
            output.newLine();

            //create a i counting variable
            int i = 0;
            //start a while loop to loop over all the messages
            while(i<this.messages.length()){
                //write all the data to the output using the getters in the message object
                output.write(((Message)this.messages.getValues()[i]).getSender()+","
                +((Message)this.messages.getValues()[i]).getReciver()+","
                +((Message)this.messages.getValues()[i]).getMessage()+","
                +((Message)this.messages.getValues()[i]).getDateTime());
                //after we haave done that go to the next line
                output.newLine();
                //increase our count and write the next message to the file
                i++;
            }

            // Closes the writer
            output.close();

        } catch (IOException e) {
            //if we catch an exception add it to the list of errors and append the e.tostring error
            this.errors.add("Cannot save messages.csv file, please check you have a csv file in this folder called '" +
                    "messages.csv' and restart the program\nJava Error: "+e.toString(),"error");
        }
    }

    //**** GET ERRORS METHOD ****\\
    //returns the array of erros to a calling controller
    public Array getErrors(){
        return this.errors;
    }
}
