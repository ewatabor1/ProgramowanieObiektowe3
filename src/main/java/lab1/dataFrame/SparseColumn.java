package main.java.lab1.dataFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SparseColumn extends Column{
    private int size;
    private Value hidden;
    private List<CooValue> lista;
    public SparseColumn(String name, Class<? extends Value> type, Value hidden){
        super(name, type);
        lista = new ArrayList<>();
        this.hidden = hidden;
        size=0;
    }

    public SparseColumn(SparseColumn column){
        super(column.getName(), column.getType());
        this.lista = new ArrayList<>(column.lista);
        this.hidden = column.hidden;
    }
    public SparseColumn(Column column, Value hidden){
        super(column);
        this.hidden = hidden;
        this.size=column.size();
    }
    public Value getHidden(){
        return hidden;
    }

    @Override
    public boolean addElement(Value o){

        if(o.equals(hidden)) {
            size++;
        }
        else{
            lista.add(new CooValue(size, o));
            size++;
        }
        return true;
    }

    @Override
    public Value elementAtIndex (int index){
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
        return String.format("Nazwa kolumny: "+name+", Typ: "+type+", Hidden: "+ hidden) +"\n"+" Zawartośc: "+ result.toString();
    }
    public int realSize(){

        return lista.size();
    }
    @Override
    public SparseColumn clone() {
        SparseColumn sparseColumn = new SparseColumn(getName(), getType(), getHidden());
        sparseColumn.lista = new ArrayList<>(lista);
        return sparseColumn;
    }
}


