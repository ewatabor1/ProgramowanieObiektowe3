package main.java.lab1.dataFrame;

public class IntegerValue extends Value{
    protected int value;
    public IntegerValue(){
        this.value=0;
    }
    public IntegerValue(int value){
        this.value=value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IntegerValue add(Value o1) {
        if (o1 instanceof IntegerValue){
            return new IntegerValue(value+(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue sub(Value o1) {
        if (o1 instanceof IntegerValue){
            return new IntegerValue(value-(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue mul(Value o1) {
        if (o1 instanceof IntegerValue){
            return new IntegerValue(value*(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue div(Value o1) {
        if (o1 instanceof IntegerValue){
            if (((IntegerValue) o1).getValue() == 0) {
                throw new IllegalArgumentException();
            }
            return new IntegerValue(value/(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue pow(Value o1) {
        if (o1 instanceof IntegerValue) {
            Integer val = (Integer) o1.getValue();
            Integer output = 1;
            for (int i = 0; i < val; i++) {
                output *= this.value;
            }
            return new IntegerValue(output);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean eq(Value o1) {
        if (o1 instanceof IntegerValue) {
            return value==(Integer) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean lte(Value o1) {
        if (o1 instanceof IntegerValue) {
            return value<(Integer) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value o1) {
        if (o1 instanceof IntegerValue) {
            return value>(Integer) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value o1) {
        if (o1 instanceof IntegerValue) {
            return value!=(Integer) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this==other) return true;
        if (other==null || other.getClass()!=this.getClass()) return false;
        IntegerValue check = (IntegerValue) other;
        return value==(int) check.getValue();
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public Value create(String s) {
        return new IntegerValue(Integer.parseInt(s));
    }

    @Override
    public Value clone() {
        return new IntegerValue(value);
    }
    @Override
    public Integer getValue() {
        return value;
    }


}
