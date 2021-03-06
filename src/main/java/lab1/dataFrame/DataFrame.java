package main.java.lab1.dataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DataFrame {
    protected List<Column> dataF;

    protected DataFrame(){
        dataF=new ArrayList<>();
    }
    public DataFrame(String[] names, String[] types) {

        dataF = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {

            if((names.length <= i)) {
                break;
            }

            if(isUnique(names[i])) {
                dataF.add(new Column(names[i], types[i]));
            }
        }
    }
    public DataFrame(String address, String[] types, boolean header) throws IOException {

        dataF = new ArrayList<>();
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
            dataF.add(new Column(names[i], types[i]));
        }
        int a=0;
        while ((strLine = br.readLine()) != null){
        //for (int a=0; a<20;a++){
            if(a>0 || (a==0 && header)){
                strLine=br.readLine();
                if(strLine==null) break;
                separated=strLine.split(",");
            }

            if(dataF.size()!=separated.length){
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
                dataF.get(i).addElementChecked(separated[i]);
            }
        }
        br.close();


    }
    private boolean isUnique (String name) {
        for(Column c : dataF) {
            if(c.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public Column get(String colname){
        for (Column a : dataF){
            if (a.getName().equals(colname)){
                return a;
            }
        }
        return null;
    }
    public Object[] getRowData(int n){
        if(n >= size())
            throw new IllegalArgumentException("Index of wanted row bigger than current size of the column.");
        Object[] result = new Object[width()];
        for(int i = 0; i < width(); i++){
            result[i] = dataF.get(i).elementAtIndex(n);
        }
        return result;
    }
    public int size(){
        return dataF.get(0).size();
    }
    public int width(){ return dataF.size();}
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Column c : dataF) {
            out.append(c.toString()).append("\n");
        }
        return out.toString();
    }

    public DataFrame get(String[] cols, boolean copy) {
        DataFrame result = new DataFrame();

        for (String s: cols) {
            for (Column c: dataF) {
                if(s.equals(c.getName())) {
                    if (copy) result.dataF.add(c.clone());
                    else result.dataF.add(c);
                    break;
                }
            }
        }
        return result;
    }
    public String[] getColumnsNames(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = dataF.get(i).getName();
        }
        return result;
    }
    public String[] getColumnsTypes(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = dataF.get(i).getType();
        }
        return result;
    }

    public DataFrame iloc(int i) {
        DataFrame output = new DataFrame(getColumnsNames(), getColumnsTypes());

        int k = 0;
        if(i >= 0 && i < size()) {
            for (Column c: output.dataF) {
                c.addElement(dataF.get(i).elementAtIndex(k++));
            }
        }
        return output;
    }

    public DataFrame iloc(int from, int to){
        DataFrame result = new DataFrame();
        if (from<0) from=0;
        if (to>=this.size()) to=this.size()-1;
        for (Column a: dataF){
            Column column = new Column(a.getName(),a.getType());
            for (int i=from;i<to;i++){
                column.addElement(a.elementAtIndex(i));
            }
            result.dataF.add(column);
        }
        return result;
    }



    public boolean addRow(Object...objects){
        if(dataF.size()!=objects.length){
            System.out.print("Nie podano wszystkich argumentów!");
            return false;
        }
        for (int i=0;i<objects.length;i++) {
            if (!dataF.get(i).isValid(objects[i])) {
                return false;
            }
        }
        for (int i=0;i<objects.length;i++){
            dataF.get(i).addElement(objects[i]);
        }
        return true;
    }



}
