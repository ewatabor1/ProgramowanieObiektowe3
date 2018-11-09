package main.java.lab1.dataFrame;


import main.java.lab1.groupBy.Operation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Column implements Cloneable{
    protected String name;
    protected Class<? extends Value> type;
    protected List<Value> lista;

    /**
     * Konstruktor
     * @param _name - nazwa kolumny
     * @param _type - typ kolumny
     */
    public Column(String _name, Class<? extends Value> _type){
        name=_name;
        type=_type;
        lista = new ArrayList<>();
    }

    /**
     * Konstruktor tworzący kolumnę na podstawie innej kolumny
     * @param column
     */
    public Column(Column column){
        this.name = column.name;
        this.type = column.type;
        this.lista = new ArrayList<>(column.lista);
    }

    /**
     * Dodaje element do kolumny
     * @param element
     * @return true jeśli element został dodany
     */
    public boolean addElement(Value element){
        if(this.type.isInstance(element)){
            lista.add(element);
            return true;
        }
        return false;
    }
    public boolean checkElement (Value element){
        if (type.isInstance(element)) return true;
        return false;
    }

    /**
     * Zwraca aktualny rozmiar kolumny
     * @return
     */
    public int size (){
        return lista.size();
    }

    /**
     * Zwraca nazwę kolumny
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Zwraca typ który przechowuje kolumna
     * @return
     */
    public Class<? extends Value> getType (){
        return type;
    }

    /**
     * Zwraca wartość elementu o danym indeksie
     * @param index
     * @return
     */
    public Value elementAtIndex(int index) {
        return lista.get(index);
    }

    /**
     * Zwraca tekstową reprezentację kolumny
     * @return
     */
    @Override
    public String toString()
    {
        return "Nazwa kolumny: " + name +
                ", Typ: " + type +
                "\n Zawartość: " + lista;
    }

    /**
     * Zwraca kopię kolumny
     * @return
     */
    @Override
    public Column clone(){
        Column column = new Column(name,type);
        column.lista = new ArrayList<>(lista);
        return column;
    }
    public Value calculate(Operation operation) {
        switch (operation) {
            case MAX:
                return getMax();
            case MIN:
                return getMin();
            case SUM:
                return getSum();
            case MEAN:
                return getMean();
            case STD:
                return getStd();
            case VAR:
                return getVar();

        }
        return new IntegerValue(0);
    }
    public Value getMax() {
        if(lista.isEmpty()) {
            return null;
        }

        Value max = lista.get(0);
        for (var value : lista) {
            if(value.gte(max)) {
                max = value;
            }
        }
        return max;
    }
    public Value getMin() {
        if(lista.isEmpty()) {
            return null;
        }

        Value min = lista.get(0);
        for (var value : lista) {
            if(value.lte(min)) {
                min = value;
            }
        }
        return min;
    }
    public Value getMean() {
        if(lista.isEmpty()) {
            return null;
        }
        Value sum = getSum();
        return sum.div(new IntegerValue(lista.size()));
    }
    public Value getSum() {
        if (lista.isEmpty()) {
            return null;
        }

        Value firstValue = (Value) lista.get(0).clone();
        Value sum = lista.get(0);
        for (var value : lista) {
            sum = sum.add(value);
        }
        return sum.sub(firstValue);
    }
    public Value getStd() {
        try{
            Constructor<? extends Value> constructor = type.getConstructor(String.class);
            return constructor.newInstance(Double.toString(Math.sqrt(Double.parseDouble(getVar().toString()))));
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Bad Column type: " + type);
    }
    public Value getVar() {
        Value mean = getMean();

        try {
            Constructor<? extends Value> constructor = type.getConstructor(String.class);
            Value output = constructor.newInstance("0");
            Value powVal = constructor.newInstance("2");
            for (var value : lista) {
                output = output.add(value.sub(mean).pow(powVal));
            }
            return output.div(new IntegerValue(lista.size()));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Bad Column type " + type);
    }


}
