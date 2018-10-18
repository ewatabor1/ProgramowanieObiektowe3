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
        String[] types=new String[] {"int","int","int"};
        try {
            DataFrame df1 = new DataFrame("data.csv",types);
            System.out.println(df1);
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
