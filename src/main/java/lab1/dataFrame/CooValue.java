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
    @Override
    public Value getValue(){
        return value;
    }
    @Override
    public String toString(){
        return value.toString();
    }
    @Override
    public Value add(Value o1) {
        return value.add(o1);
    }
    @Override
    public Value sub(Value o1) {
        return value.sub(o1);
    }

    @Override
    public Value mul(Value o1) {
        return value.mul(o1);
    }

    @Override
    public Value div(Value o1) {
        return value.div(o1);
    }

    @Override
    public Value pow(Value o1) {
        return value.pow(o1);
    }

    @Override
    public boolean eq(Value o1) {
        return value.eq(o1);
    }

    @Override
    public boolean lte(Value o1) {
        return value.lte(o1);
    }

    @Override
    public boolean gte(Value o1) {
        return value.gte(o1);
    }

    @Override
    public boolean neq(Value o1) {
        return value.neq(o1);
    }

    @Override
    public boolean equals(Object other) {
        return value.equals(other);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public Value create(String s) {
        return value.create(s);
    }

    @Override
    public Value clone() {
        return new CooValue(index, value.clone());
    }
}

