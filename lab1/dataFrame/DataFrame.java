package main.java.lab1.dataFrame;

import java.util.ArrayList;
import java.util.List;

public class DataFrame {
    private List<Column> dataF;
    private int dataFLength;

    private DataFrame(){
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
    public int size(){
        return dataF.get(0).size();
    }
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

    public DataFrame iloc (int i){
        DataFrame result = new DataFrame();
        for (Column a: dataF){
            Column column = new Column(a.getName(),a.getType());
            if (i>=0 && this.size()>i){
                column.addElement(a.elementAtIndex(i));
            }
            result.dataF.add(column);
        }
        return result;

    }

    public DataFrame iloc(int from, int to){
        DataFrame result = new DataFrame();
        if (from<0) from=0;
        if (to>=this.size()) to=this.size()-1;
        for (Column a: dataF){
            Column column = new Column(a.getName(),a.getType());
            for (int i=from;i<=to;i++){
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
