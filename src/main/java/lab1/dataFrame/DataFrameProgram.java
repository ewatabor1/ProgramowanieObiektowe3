package main.java.lab1.dataFrame;

import java.io.IOException;

public class DataFrameProgram {
    public static void main(String[] argv){
        SparseDataFrame df = new SparseDataFrame(new String[] {"kol1", "kol2", "kol3"}, "int",0);
        df.addRowS(1,2,0);
        df.addRowS(0,0,0);
        df.addRowS(1,5,2);
        df.addRowS(0,0,7);
        df.addRowS(1,34,0);
        df.addRowS(0,1,0);
        Object[] lala = {0,1,5};
        df.addRowS(lala);
        String[] types=new String[] {"double","double","double"};
        df.realColumnSize();
        System.out.println(df);
        try {
            SparseDataFrame df1 = new SparseDataFrame("sparse.csv",types,"0.0",true);
            System.out.println(df1);
            df1.realColumnSize();
            DataFrame df2 = new DataFrame("data.csv",types,true);
            System.out.println(df2);
            SparseDataFrame df3= new SparseDataFrame("Lala.csv",types,"0.0",false);
            System.out.println(df3);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        //SparseDataFrame df2 = df.getColums(new String[]{"kol1","kol2", "kol3"}, true);
        //DataFrame df3 = df.get(new String[]{"kol1","kol3", "kol1"}, false);
        //DataFrame df4 = new DataFrame("myData.csv", new String[]{"int","int","int","int"});
        //System.out.println(df4);

        //System.out.println(df1);
        /*DataFrame df3 = df1.get(new String[]{"kol1","kol3", "kol1"}, false);
        System.out.println(df3);

        System.out.println(df.iloc(0));
        System.out.println(df.iloc(2, 7));*/
    }
}
