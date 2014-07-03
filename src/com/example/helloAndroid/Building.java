package com.example.helloAndroid;

public class Building extends BaseActivity{
    public int x, y;
    public Double scale;
    public String name;

    public Building(String _name, int _x, int _y, double _scale){
        x = _x;
        y = _y;
        name = _name;
        scale = _scale;
    }

    public String toString(){
        return(name + " (" + x + ", " + y + ")");
    }
}
