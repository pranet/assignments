package com.cnu2016.assignment02;

public class Appliance {
    public enum Type {
        WATERHEATER, 
        AIRCONDITIONER, 
        COOKINGOVEN  
    };
    private Type type;
    private boolean state;
    private int ID;
    static int cnt = 0;
    public Appliance(Type type) {
        this.state = false;
        this.ID = cnt++;
        this.type = type;
    }
    public boolean getState() {
        return state;
    } 
    public void toggle() {
        state ^= true;
    }
    public int getID() {
        return ID;
    }
    public Type getType() {
        return type;
    }
    @Override
    public String toString() {
        return ID + " " + state;
    }
}