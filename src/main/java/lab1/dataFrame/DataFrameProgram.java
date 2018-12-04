package main.java.lab1.dataFrame;


import main.java.lab1.dataFrameDB.DataFrameDB;
import main.java.lab1.myExceptions.WrongTypeInColumn;

public class DataFrameProgram {
    public static void main(String[] args) {
        /*DataFrameDB dataFDB = new DataFrameDB("mysql.agh.edu.pl/agatatab",
                "agatatab", "DMQZxAQGWaQJvHWk");
        try{
            DataFrame dataF = dataFDB.getDataFrameFromQuery(
                    "SELECT count(*) FROM books WHERE author LIKE '%Hemingway%'");
            System.out.println(dataF);
            System.out.println(dataFDB.getDataFrame("books"));
            System.out.println(dataF.get(0).getType().toString());
        }
        catch (Exception e){
            e.printStackTrace();;
        }*/
        Class<? extends Value>[] types = new Class[4];
        types[0] = StringValue.class;
        types[1] =DateTimeValue.class;
        types[2] = FloatValue.class;
        types[3]=FloatValue.class;
        String[] names=new String[4];
        names[0]="lalal";
        names[1]="FKS";
        names[2]="FKOE";
        names[3]="FFFFFEEEEEE";
        try{
            DataFrame dataFrame = new DataFrame("groupby.csv",types,names);
            /*DataFrameDB dataFrameDB = new DataFrameDB("mysql.agh.edu.pl/agatatab",
                    "agatatab", "DMQZxAQGWaQJvHWk");*/
            DataFrame dataFrameDB2 = new DataFrameDB(dataFrame,"laa2","mysql.agh.edu.pl/agatatab",
                    "agatatab", "DMQZxAQGWaQJvHWk");
            System.out.println(dataFrameDB2);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}