package main.java.lab1.dataFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SparseColumn extends Column{
    private int size;
    private Object hidden;
    private List<CooValue> lista;
    public SparseColumn(String name, String type, Object hidden){
        super(name, type);
        this.lista = new ArrayList<>();
        this.hidden = hidden;
        size=0;
    }

    public SparseColumn(SparseColumn column){
        super(column.getName(), column.getType());
        this.lista = new ArrayList<>(column.lista);
        this.hidden = column.hidden;
    }
    public SparseColumn(Column column, String hidden){
        super(column);
        this.hidden = hidden;
        this.size=column.size();
    }
    public Object getHidden(){
        return hidden;
    }

    @Override
    public void addElement(Object o){
        if(o.toString().equals(hidden))
            size++;
        else{
            lista.add(new CooValue(size, o));
            size++;
        }
    }

    @Override
    public Object elementAtIndex (int index){
        for(CooValue element : lista){
            if(element.getIndex() == index)
                return element.getValue();
        }
        return hidden;
    }
    @Override
    public int size(){
        return size;
    }
    public void increaseSize(){
        size++;
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append('[');
        for(int i=0; i<this.size; i++){
            boolean found = false;
            for(CooValue entry : this.lista){
                if(entry.getIndex() == i){
                    result.append(entry.getValue().toString());
                    found = true;
                }
            }
            if(!found)
                result.append(this.hidden);
            if(i < size - 1)
                result.append(", ");
        }
        result.append(']');
        return String.format("%s (%s) [%s]: ", name, fix(type), hidden) + result.toString();
    }
    @Override
    public SparseColumn clone() {
        SparseColumn column = new SparseColumn(name, type,hidden);
        Method method = null;

        if(lista.isEmpty()) return column;
        else {
            try {
                method = lista.get(0).getClass().getMethod("clone");
            }
            catch (NoSuchMethodException e) {
                System.out.println("Klasa: " + type + " nie ma zadeklarowanej metody clone");
            }
        }

        for (Object a: lista) {
            try {
                if (method==null) column.addElement(a);
                else column.addElement(method.invoke(a));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return column;
    }
}


