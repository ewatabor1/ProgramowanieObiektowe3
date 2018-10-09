package main.java.lab1.dataFrame;

public class DataFrameProgram {
    public static void main(String[] argv){
        DataFrame df = new DataFrame(new String[] {"kol1", "kol2", "kol3"}, new String[]{"double","int","String"});
        df.addRow(1.5, 2, "AAA");
        df.addRow(352.2, 22, "AOS");
        df.addRow(341.2,32,"A01");
        df.addRow(3.5,1,"A32");
        df.addRow(25.512,55,"BKRT");
        df.addRow(12.2,13,"akoko");

        DataFrame df2 = df.get(new String[]{"kol1","kol2", "kol3"}, true);
        DataFrame df3 = df.get(new String[]{"kol1","kol3", "kol1"}, false);

        System.out.println(df2);
        System.out.println(df.iloc(1));
        System.out.println(df.iloc(3, 7));
    }
}
