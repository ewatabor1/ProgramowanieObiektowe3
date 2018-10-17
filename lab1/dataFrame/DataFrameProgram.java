package main.java.lab1.dataFrame;

public class DataFrameProgram {
    public static void main(String[] argv){
        SparseDataFrame df = new SparseDataFrame(new String[] {"kol1", "kol2", "kol3"}, "int",0);
        df.addRowS(1,2,0);
        df.addRowS(0,0,0);
        df.addRowS(1,5,2);
        df.addRowS(0,0,7);
        df.addRowS(1,34,0);
        df.addRowS(0,1,0);

        //SparseDataFrame df2 = df.getColums(new String[]{"kol1","kol2", "kol3"}, true);
        //DataFrame df3 = df.get(new String[]{"kol1","kol3", "kol1"}, false);

        System.out.println(df);
        //System.out.println(df.iloc(1));
        //System.out.println(df.iloc(3, 7));
    }
}
