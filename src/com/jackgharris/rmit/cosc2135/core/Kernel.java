package com.jackgharris.rmit.cosc2135.core;

import com.jackgharris.rmit.cosc2135.controllers.LoginController;
import com.jackgharris.rmit.cosc2135.controllers.MessageController;
import com.jackgharris.rmit.cosc2135.controllers.TextController;

public class Kernel {

    public Kernel(){


        Array test = new Array(Controller.class);

        test.add(new LoginController(),"login");
        test.add(new MessageController(),"message");
        test.add(new TextController(), "text");


        Array cars = new Array(String.class);

        cars.add("holden", null);
        cars.add("ford", null);



    }
}
