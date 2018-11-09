package main.java.lab1.dataFrame;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DataFrameProgram {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DataFrame dataFrame = new DataFrame("groupby.csv",
                new Class[]{StringValue.class, DateTimeValue.class, FloatValue.class, FloatValue.class},true);
        System.out.println(dataFrame.iloc(0,10));
        System.out.println(dataFrame.groupBy("id").std());
        //System.out.println(dataFrame.groupBy("id").min());
    }
}
