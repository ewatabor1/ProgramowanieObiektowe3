package main.java.lab1.dataFrame;

public class CooValue extends Value {
    private int index;
    private Value value;

    public CooValue(int index, Value value){
        this.index = index;
        this.value = value;
    }

    public int getIndex(){
        return index;
    }
    public Value getValue(){
        return value;
    }

    @Override
    public boolean getSet() {
        return value.getSet();
    }

    public String toString(){
        return value.toString();
    }
    public Value add(Value o1) {
        return value.add(o1);
    }
    public Value sub(Value o1) {
        return value.sub(o1);
    }

    public Value mul(Value o1) {
        return value.mul(o1);
    }

    public Value div(Value o1) {
        return value.div(o1);
    }

    public Value pow(Value o1) {
        return value.pow(o1);
    }

    public boolean eq(Value o1) {
        return value.eq(o1);
    }

    public boolean lte(Value o1) {
        return value.lte(o1);
    }

    public boolean gte(Value o1) {
        return value.gte(o1);
    }

    public boolean neq(Value o1) {
        return value.neq(o1);
    }

    public boolean equals(Object other) {
        return value.equals(other);
    }

    public int hashCode() {
        return value.hashCode();
    }

    public Value create(String s) {
        return value.create(s);
    }

    public Value clone() {
        return new CooValue(index, value.clone());
    }
}

