package main.java.lab1.dataFrame;

import java.util.*;

public class Column {
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

}
