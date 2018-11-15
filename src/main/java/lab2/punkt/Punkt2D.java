package main.java.lab2.punkt;

import static java.lang.Math.*;

public class Punkt2D {
    protected double x;
    protected double y;
    public Punkt2D(double x, double y){
        this.x=x;
        this.y=y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double a){
        x=a;
    }
    public void setY(double a){
        y=a;
    }
    public double distance(Punkt2D o1){
        double result;
        result=sqrt(pow((x-o1.x),2)+pow((y-o1.y),2));
        return result;
    }
}
