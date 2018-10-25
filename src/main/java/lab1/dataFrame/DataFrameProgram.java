package main.java.lab1.dataFrame;

import java.io.IOException;

public class DataFrameProgram {
    public static void main(String[] argv){
        /*DataFrame dataFrame = new DataFrame(new String[]{"col1","col2"}, new Class[]{IntegerValue.class, IntegerValue.class});
        dataFrame.addRow(new IntegerValue(1), new IntegerValue(2));
        dataFrame.addRow(new IntegerValue(3), new IntegerValue(4));
        dataFrame.addRow(new IntegerValue(12), new IntegerValue(212));
        dataFrame.addRow(new IntegerValue(12), new IntegerValue(2123));
        System.out.println(dataFrame);
        System.out.println(dataFrame.iloc(1));
        System.out.println(dataFrame.iloc(1,4));
        System.out.println(dataFrame.get(new String[]{"col1","col2"}, true));
/*
        try {
            DataFrame dataFrame1 = new DataFrame("data.csv", new Class[]{DoubleValue.class, DoubleValue.class, DoubleValue.class},true);
            System.out.println(dataFrame1.iloc(0,2));
        }
        catch(IOException e) {
            e.printStackTrace();
        }*/
        SparseDataFrame sparseDataFrame = new SparseDataFrame(new String[]{"col1","col2"},
                new Class[]{DoubleValue.class, DoubleValue.class}, new DoubleValue(0.0));
        sparseDataFrame.addRow(new DoubleValue(2.5), new DoubleValue(2.2));
        sparseDataFrame.addRow(new DoubleValue(0.0), new DoubleValue(0.0));
        sparseDataFrame.addRow(new DoubleValue(2.5), new DoubleValue(0.0));
        sparseDataFrame.addRow(new DoubleValue(2.215), new DoubleValue(212312.2));
        sparseDataFrame.addRow(new DoubleValue(2.5), new DoubleValue(2.2123));
        sparseDataFrame.addRow(new DoubleValue(-333.3), new DoubleValue(0.0));
        //System.out.println(sparseDataFrame);
        System.out.println(sparseDataFrame.iloc(2));
        //System.out.println(sparseDataFrame.iloc(1,4));
        System.out.println(sparseDataFrame.get(new String[]{"Kolumna","Kolumna2"}, true));
        //System.out.println(new SparseDataFrame(dataFrame,new IntegerValue(12)));
    }
}
