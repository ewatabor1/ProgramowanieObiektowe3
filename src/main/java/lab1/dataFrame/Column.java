package main.java.lab1.dataFrame;


import main.java.lab1.groupBy.Operation;
import main.java.lab1.myExceptions.DifferentSizedColumns;
import main.java.lab1.myExceptions.WrongTypeInColumn;

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
    public void addElementWC (Value element){
        lista.add(element);
    }
    public void switchElementWC (int index, Value element){
        if (lista.size()>index && index>=0) {
            lista.set(index, element);
        }
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
    public void setElement(int index,Value o1){
        if (type.isInstance(o1)) lista.set(index,o1);
        else throw new IllegalArgumentException();
    }
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
    public Column clone(){
        Column column = new Column(name,type);
        column.lista = new ArrayList<>(lista);
        return column;
    }
    public Value calculate(Operation operation) throws WrongTypeInColumn{
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

    public Value getMax() throws WrongTypeInColumn{
        if(lista.isEmpty()) {
            return null;
        }

        Value max = lista.get(0);
        int i=0;
        for (var value : lista) {
            if (!type.isInstance(value)) throw new WrongTypeInColumn(getName(),i);
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

    public void addValue (Value o1)throws WrongTypeInColumn,IllegalArgumentException{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1)) throw new IllegalArgumentException();
            lista.set(i,lista.get(i).add(o1));
        }
    }
    public void subValue (Value o1) throws WrongTypeInColumn,IllegalArgumentException{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1)) throw new IllegalArgumentException();
            lista.set(i,lista.get(i).sub(o1));
        }
    }
    public void divValue (Value o1) throws WrongTypeInColumn, IllegalArgumentException{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1)) throw new IllegalArgumentException();
            lista.set(i,lista.get(i).div(o1));
        }
    }
    public void mulByValue (Value o1) throws WrongTypeInColumn,IllegalArgumentException{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1)) throw new IllegalArgumentException();
            lista.set(i,lista.get(i).mul(o1));
        }
    }
    public void addValueC (Column o1) throws WrongTypeInColumn{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1.elementAtIndex(i))) throw new WrongTypeInColumn(o1.name,i);
            lista.set(i,lista.get(i).add(o1.elementAtIndex(i)));
        }
    }
    public void subValueC (Column o1) throws WrongTypeInColumn{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1.elementAtIndex(i))) throw new WrongTypeInColumn(o1.name,i);
            lista.set(i,lista.get(i).sub(o1.elementAtIndex('i')));
        }
    }
    public void divValueC (Column o1) throws WrongTypeInColumn{
        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1.elementAtIndex(i))) throw new WrongTypeInColumn(o1.name,i);
            lista.set(i,lista.get(i).div(o1.elementAtIndex(i)));
        }
    }
    public void mulByValueC (Column o1) throws DifferentSizedColumns, WrongTypeInColumn {
        if(lista.size()!=o1.size()) throw new DifferentSizedColumns(getName(),o1.getName(),size(),o1.size());

        for (int i = 0; i < size(); i++) {
            if(!type.isInstance(lista.get(i))) throw new WrongTypeInColumn(name,i);
            if(!type.isInstance(o1.elementAtIndex(i))) throw new WrongTypeInColumn(o1.name,i);
            lista.set(i,lista.get(i).mul(o1.elementAtIndex(i)));
        }
    }
}
