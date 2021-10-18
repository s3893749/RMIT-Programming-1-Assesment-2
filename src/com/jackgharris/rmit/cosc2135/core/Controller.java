package com.jackgharris.rmit.cosc2135.core;

public abstract class Controller{

    protected boolean booted = false;


    public abstract void boot();

    public void setBooted(boolean value){
        this.booted = true;
    }

    public boolean getBootedStatus(){
        return this.booted;
    }

}
