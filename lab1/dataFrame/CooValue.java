package main.java.lab1.dataFrame;

public class CooValue {
    private int index;
    private Object value;

    public CooValue(int index, Object value){
        this.index = index;
        this.value = value;
    }

    public int getIndex(){
        return index;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public String toString(){
        return String.format("[%d] - %s", index, value);
    }
}

