package main.java.lab1.dataFrame;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SparseDataFrame {
    private List<SparseColumn> sparseDF;
    public SparseDataFrame(){
        this.sparseDF = new ArrayList<>();
    }

    public SparseDataFrame(String[] columnNames, String typeName, Object hidden){
        sparseDF = new ArrayList<>(columnNames.length);
        for(String columnName : columnNames){
            sparseDF.add(new SparseColumn(columnName, typeName, hidden));
        }
    }

    public SparseDataFrame (DataFrame dataFrame, String hidden){
        if(dataFrame.width() > 0){
            String[] types = dataFrame.getColumnsTypes();
            String type = types[0];
            for(String s : types)
                if(!s.equals(type))
                    throw new UnsupportedOperationException("Can't convert a DataFrame with different typeNames in it.");


            sparseDF = new ArrayList<>();
            String[] names = dataFrame.getColumnsNames();
            for(String name : names){
                sparseDF.add(new SparseColumn(name, type, hidden));
            }


            for(int i = 0; i < dataFrame.size(); i++)
                addRowS(dataFrame.getRowData(i));
        }
    }
    public SparseDataFrame(String address, String[] types, Object hidden, boolean header) throws IOException {

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
        //while ((strLine = br.readLine()) != null){
        for (int a=0; a<20;a++){
            if(a>0 || (a==0 && header)){
                strLine=br.readLine();
                if(strLine==null) break;
                separated=strLine.split(",");
            }

            if(sparseDF.size()!=separated.length){
                System.out.print("Nie podano wszystkich argumentów!");
                continue;
            }
            /*for (int i=0;i<separated.length;i++) {
                if (!dataF.get(i).isValid(separated[i])) {
                    System.out.println(separated.getClass().toString());
                    continue;
                }
            }*/
            for (int i=0;i<separated.length;i++){
                //Konwersja typów?
                sparseDF.get(i).addElement(separated[i]);
            }
        }
        br.close();


    }

    public boolean addRowS(Object...objects){
        if(objects.length != this.width()) {
            System.out.println("Nie podano wszystkich argumentów");
            return false;
        }
        for (int i=0;i<objects.length;i++) {
            if (!sparseDF.get(i).isValid(objects[i])) {
                return false;
            }
        }

        for(int i = 0; i < objects.length; i++){
            sparseDF.get(i).addElement(objects[i]);
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
    public String[] getColumnsTypeNames(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = sparseDF.get(i).getType();
        }
        return result;
    }

    //@Override
    public SparseDataFrame get(String[] cols, boolean copy) {
        SparseDataFrame result = new SparseDataFrame();

        for (String s: cols) {
            for (SparseColumn c: sparseDF) {
                if(s.equals(c.getName())) {
                    if (copy) result.sparseDF.add(c.clone());
                    else result.sparseDF.add(c);
                    break;
                }
            }
        }
        return result;
    }
    public DataFrame toDense () {
        String[] types = getColumnsTypeNames();
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

