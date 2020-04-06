package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;

import java.util.HashMap;

public enum Color {

    RED, YELLOW, BLUE;


    static HashMap<Color, Boolean > availability = new HashMap<>();

    //Before starting the game you need to initialize the Hashmap by doing:
    //for(Color color: Color.values())
    //    color.init();
    public void init() {
        availability.put(this, true);

    }

    //Once a player choose a color, use this method to set its availability to 'false'
    public void booked(){
        availability.put(this, false);
    }

    public Boolean isAvailiable(){
        Boolean x = false;
        try {
            x = availability.get(this);
        }catch(ClassCastException|NullPointerException e){
            System.err.println("Exception: " + e.getMessage());
        }
            //if(availability.get(this))

            //else
                //System.out.println("color:" + this + " is NOT availiable");


    return x;
    }
}
