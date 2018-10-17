package main.java.lab1.dataFrame;

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

    public DataFrame toDense () {
        String[] types = getColumnsTypeNames();
        String[] names = getColumnsNames();
        DataFrame result = new DataFrame(names,types);
        Object[] lista= new Object[sparseDF.size()];
        int i=0,a=0;
        for (; i<sparseDF.size(); i++){
            for (SparseColumn b: sparseDF){
                lista[i]=b.elementAtIndex(i);
                sparseDF.get(a).addElement(b.elementAtIndex(i));
                a++;
            }
            a=0;
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


    }

