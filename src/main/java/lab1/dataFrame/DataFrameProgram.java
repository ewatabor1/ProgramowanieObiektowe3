package main.java.lab1.dataFrame;


import main.java.lab1.myExceptions.DifferentSizedColumns;
import main.java.lab1.myExceptions.WrongTypeInColumn;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DataFrameProgram {
    public static void main(String[] args) throws IOException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, DifferentSizedColumns,WrongTypeInColumn {
        DataFrame dataFrame = new DataFrame("groupby.csv",
                new Class[]{StringValue.class, DateTimeValue.class, FloatValue.class, FloatValue.class},true);
        //System.out.println(dataFrame.iloc(0,10));000
        dataFrame.get(2).mulByValueC(dataFrame.get(2));
        System.out.println(dataFrame.iloc(0,5));
        try{
            dataFrame.get(2).mulByValueC(dataFrame.iloc(0,5).get(2));
        }
        catch (DifferentSizedColumns e){
            e.printMessage();
        }
        Column column = new Column(dataFrame.get(2));
        column.switchElementWC(5,new StringValue("dnacnsadkcndskfndsk"));
        dataFrame.switchColumn(2,column);


        //dataFrame.mulValueInColumn(2,new FloatValue((float) 10.5));
        //System.out.println(dataFrame.iloc(0,10));
        try{
            System.out.println("\nGroupby: ");
            System.out.println(dataFrame.groupBy("id").max());
            //dataFrame.get(2).mulByValueC(column);
        }
        catch (WrongTypeInColumn e){
            e.printMessage();
        }
        //System.out.println(dataFrame.groupBy("id").min());
    }
}