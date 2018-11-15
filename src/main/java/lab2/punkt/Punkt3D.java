package main.java.lab2.punkt;

import static java.lang.Math.*;

public class Punkt3D extends Punkt2D {
    protected double z;
    public Punkt3D (double x, double y, double z){
        super(x,y);
        this.z=z;
    }
    public double getZ(){
        return z;
    }
    public void setZ(double z){
        this.z=z;
    }
    public double distance(Punkt3D o1){
        double result;
        result=sqrt(pow(x-o1.x,2)+pow(y-o1.y,2)+pow(z-o1.z,2));
        return result;
    }


}
