package main.java.lab1.dataFrame;

public class DoubleValue extends Value {
    private double value;
    public DoubleValue(){
        value=0;
    }
    public DoubleValue(double value){
        this.value=value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public DoubleValue add(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value+(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }


    @Override
    public DoubleValue sub(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value-(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public DoubleValue mul(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value*(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public DoubleValue div(Value o1) {
        if (o1 instanceof DoubleValue || o1 instanceof IntegerValue){
            double temp;
            if(o1 instanceof DoubleValue) temp=((DoubleValue) o1).getValue();
            else temp=(double) o1.getValue();
            if (temp == 0) {
                throw new IllegalArgumentException();
            }
            return new DoubleValue(value/temp);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public DoubleValue pow(Value o1) {
        if (o1 instanceof DoubleValue) {
            return new DoubleValue(Math.pow(this.value, (double) o1.getValue()));
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean eq(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value==(Double) o1.getValue();
        }
        return false;
    }
    @Override
    public boolean lte(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value<(Double) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value>(Double) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value!=(Double) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this==other) return true;
        if (other==null || other.getClass()!=this.getClass()) return false;
        DoubleValue check = (DoubleValue) other;
        return value==(double) check.getValue();
    }

    @Override
    public int hashCode() {
        int result = (int) value;
        return  result;
    }

    @Override
    public Value create(String s) {
        return new DoubleValue(Double.parseDouble(s));
    }

    @Override
    public Value clone() {
        return new DoubleValue(value);
    }

    @Override
    public Double getValue() {
        return value;
    }
}
