//**** SET PACKAGE ****\\
package com.jackgharris.rmit.cosc2135.models;

//**** START CLASS ****\\
//this is the single instance of the user accounts that are created and managed by the UsersModel
public class User {

    //set private final variables, these are instance variables that are final as they are set once by the constructor and never changed
    //String Username
    private final String username;
    //String Password
    private final String password;
    //String Fullname
    private final String fullname;
    //Long Registration Date
    private final Long registrationDate;
    //Boolean isAdmin Status
    private final boolean isAdmin;

    //**** USER CONSTRUCTOR ****\\
    //accepts all the final values that are required when constructing this object, these include fullname, username, password, registrationdate and is admin
    public User(String username, String password, String fullname, long registrationDate, boolean isAdmin){
        //set the class variables to the parsed constructor parameter inputs
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.registrationDate = registrationDate;
        this.isAdmin = isAdmin;
    }

    //**** GET USERNAME GETTER ****\\
    //returns the username of this user
    public String getUsername(){
        return this.username;
    }

    //**** GET PASSWORD GETTER ****\\
    //returns the password of this user
    public String getPassword(){
        return this.password;
    }

    //**** GET FULL NAME GETTER ****\\
    //returns the full name of this user
    public String getFullname(){
        return this.fullname;
    }

    //**** GET REGISTATION DATE GETTER ****\\
    //returns the registationdate of the user account
    public Long getRegistrationDate() {
        return this.registrationDate;
    }

    //**** GET ADMIN STATUS GETTER ****\\
    //returns the admins status of the user
    public boolean getAdminStatus(){
        return this.isAdmin;
    }
}
