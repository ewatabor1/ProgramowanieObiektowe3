package main.java.lab1.dataFrame;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DataTruncation;
import java.util.*;

public class SparseDataFrame {
    private List<SparseColumn> sparseDF;
    public SparseDataFrame(){
        this.sparseDF = new ArrayList<>();
    }

    public SparseDataFrame(String[] names,  Class<? extends Value>[] types, Value hidden){
        sparseDF = new ArrayList<>(names.length);
        for(String columnName : names){
            sparseDF.add(new SparseColumn(columnName, types[0], hidden));
        }
    }

    public SparseDataFrame (DataFrame dataFrame, Value hidden){
        if(dataFrame.width() > 0){
            Class<? extends Value> type = dataFrame.get(0).getType();
            sparseDF = new ArrayList<>();
            String[] names = dataFrame.getColumnsNames();
            for(String name : names){
                sparseDF.add(new SparseColumn(name, type, hidden));
            }


            for(int i = 0; i < dataFrame.size(); i++)
                addRow(dataFrame.getRowData(i));
        }
    }
    public SparseDataFrame(String address, Class<? extends Value>[] types, Value hidden, boolean header) throws IOException {

        sparseDF = new ArrayList<>();
        FileInputStream fstream;
        BufferedReader br;

        fstream = new FileInputStream(address);


        if (fstream == null)
            throw new IOException("File not found!");
        else
            br = new BufferedReader(new InputStreamReader(fstream));

        String strLine=br.readLine();
        String[] separated=strLine.split(",");
        String[] names= new String[types.length];
        if (!header){
            Scanner odczyt = new Scanner(System.in);
            for (int l=0;l<types.length;l++){
                System.out.print("Podaj nazwę kolumny: ");
                names[l] = odczyt.nextLine();
            }
        }
        if (header){
            for (int m=0;m<types.length;m++){
                names[m]=separated[m];
            }
        }

        for (int i = 0; i < types.length; i++) {
            if((separated.length <= i)) {
                break;
            }
            sparseDF.add(new SparseColumn(names[i], types[i],hidden));
        }
        Value[] values=new Value[sparseDF.size()];
        int a=0;
        while ((strLine = br.readLine()) != null){
            if(a>0 || (a==0 && header)){
                strLine=br.readLine();
                if(strLine==null) break;
                separated=strLine.split(",");
            }
            for (int i = 0; i < separated.length; i++) {
                DoubleValue value1 = new DoubleValue();
                values[i] = value1.create(separated[i]);
            }
            if(sparseDF.size()!=separated.length){
                System.out.print("Nie podano wszystkich argumentów!");
                continue;
            }

            for (int i=0;i<separated.length;i++){
                sparseDF.get(i).addElement(values[i]);
            }
        }
        br.close();
    }

    public boolean addRow(Value...values){
        if(values.length != this.width()) {
            System.out.println("Nie podano wszystkich argumentów");
            return false;
        }
        for(int i = 0; i < values.length; i++){
            sparseDF.get(i).addElement(values[i]);
        }
        return true;
    }
    public SparseDataFrame(SparseColumn[] o1){
        this.sparseDF = new ArrayList<>(Arrays.asList(o1));
    }
    public int width(){
        return sparseDF.size();
    }
    public void realColumnSize(){
        for (SparseColumn a: sparseDF){
            System.out.println(a.realSize());
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Column column : sparseDF){
            sb.append(column.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
    public String[] getColumnsNames(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = sparseDF.get(i).getName();
        }
        return result;
    }
    public Class<? extends Value>[] getColumnsTypeNames(){
        Class[] classes = new Class[sparseDF.size()];
        for (int i = 0; i < classes.length ; i++) {
            classes[i] = sparseDF.get(i).getType();
        }
        return classes;
    }

    //@Override
    public SparseDataFrame get(String[] colNames, boolean copy){
        SparseDataFrame result = new SparseDataFrame();
        if(copy){
            for (String c : colNames){
                for (SparseColumn col : sparseDF){
                    if (col.getName().equals(c)){
                        SparseColumn addColumn = new SparseColumn(col.getName(), col.getType(), col
                                .getHidden());
                        for (int i = 0; i < col.size(); i++){
                            addColumn.addElement(col.elementAtIndex(i));
                        }
                        result.sparseDF.add(addColumn);
                        break;
                    }
                }
            }
            return result;
        }
        else {
            for (String c : colNames){
                for (SparseColumn col : sparseDF){
                    if (col.getName().equals(c)){
                        result.sparseDF.add(col);
                    }
                }
            }
            return result;
        }
    }

    public DataFrame toDense () {
        Class<? extends Value>[] types = getColumnsTypeNames();
        String[] names = getColumnsNames();
        DataFrame result = new DataFrame();
        Object[] lista= new Object[sparseDF.size()];

        for (SparseColumn a : sparseDF) {
            Column column = new Column(a.getName(), a.getType());
            for (int i = 0; i < a.size(); i++) {
                column.addElement(a.elementAtIndex(i));
            }
            result.dataF.add(column);
        }
        return result;
    }


    //@Override
    public SparseDataFrame iloc (int i){
        SparseDataFrame result = new SparseDataFrame();
        for (SparseColumn a: sparseDF){
            SparseColumn column = new SparseColumn(a.getName(),a.getType(),a.getHidden());
            if (i>=0 && a.size()>i){
                if (a.elementAtIndex(i)!=a.getHidden()) {
                    column.addElement(a.elementAtIndex(i));
                }

                else {
                    column.increaseSize();
                }
            }
            result.sparseDF.add(column);
        }
        return result;

    }
    public SparseDataFrame iloc(int from, int to){
        SparseDataFrame result = new SparseDataFrame();
        if (from<0) from=0;
        if(sparseDF.size()>0) {
            if (to > sparseDF.get(0).size()) to = sparseDF.get(0).size();
        }

        for (SparseColumn a: sparseDF){
            SparseColumn column = new SparseColumn(a.getName(), a.getType(),a.getHidden());
            for (int i=from;i<to;i++){
                if (a.elementAtIndex(i)!=a.getHidden()) {
                    column.addElement(a.elementAtIndex(i));
                }
                else column.increaseSize();
            }
            result.sparseDF.add(column);
        }
        return result;
    }


}

