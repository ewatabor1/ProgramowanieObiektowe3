package main.java.lab1.dataFrame;

import java.util.*;
import java.lang.reflect.*;

public class Column {
    private ArrayList kolumna;
    private String name;
    private String type;
    private List<Object> lista;

    public Column(String _name, String _type){
        name=_name;
        type=fix(_type);
        lista = new ArrayList<>();
    }
    @Override
    public String toString() {
        return "Nazwa kolumny: " + name + ", Typ: " + type + "\n Zawartość: " + lista;
    }
    public String fix (String type){
        if (type.equals("int")) return "Integer";
        if (type.equals("double")) return "Double";
        if (type.equals("short")) return "Short";
        if (type.equals("float")) return "Float";
        if (type.equals("long")) return "Long";
        if (type.equals("byte")) return "Byte";
        if (type.equals("boolean")) return "Boolean";
        if (type.equals("char")) return "Character";
        return type;
    }
    public boolean isValid(Object element) {
        return element.getClass().toString().contains(type);
    }

    public void addElement (Object element){
        if (isValid(element)){
            lista.add(element);
        }
    }
    public Object atIndex(int a){
        return lista.get(a);
    }
    public int size (){
        return lista.size();
    }
    public String getName(){
        return name;
    }
    public String getType (){
        return type;
    }

    public Object elementAtIndex(int index) {
        return lista.get(index);
    }
    @Override
    public Column clone() {
        Column column = new Column(name, type);
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
