package main.java.lab1.myExceptions;

import main.java.lab1.dataFrame.DataFrame;
import main.java.lab1.dataFrame.Value;

public class WrongTypeInColumn extends Exception {
    private String name;
    private int index;
    public WrongTypeInColumn(){
        super();
    }
    public WrongTypeInColumn(String name, int i){
        this.name=name;
        index=i;
    }
    public void printMessage (){
        System.out.println("Zły typ w kolumnie o nazwie \""+name+"\", indeks: "+index);
    }
}
