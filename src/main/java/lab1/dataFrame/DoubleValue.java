package main.java.lab1.dataFrame;

public class DoubleValue extends Value {
    private double value;
    boolean set;
    public DoubleValue(){
        set=true;
        value=0;
    }
    public DoubleValue(double value){
        set=true;
        this.value=value;
    }
    public DoubleValue(String string) {
        try{
            value = Double.valueOf(string);
            set=true;
        }
        catch (Exception e){
            set=false;
        }
    }
    public String toString() {
        return String.valueOf(value);
    }

    public DoubleValue add(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value+(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }


    public DoubleValue sub(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value-(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    public DoubleValue mul(Value o1) {
        if (o1 instanceof DoubleValue){
            return new DoubleValue(value*(Double) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

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

    public DoubleValue pow(Value o1) {
        if (o1 instanceof DoubleValue) {
            return new DoubleValue(Math.pow(this.value, (double) o1.getValue()));
        }
        throw new IllegalArgumentException();
    }

    public boolean eq(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value==(Double) o1.getValue();
        }
        return false;
    }
    public boolean lte(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value<(Double) o1.getValue();
        }
        return false;
    }

    public boolean gte(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value>(Double) o1.getValue();
        }
        return false;
    }

    public boolean neq(Value o1) {
        if (o1 instanceof DoubleValue) {
            return value!=(Double) o1.getValue();
        }
        return false;
    }

    public boolean equals(Object other) {
        if (this==other) return true;
        if (other==null || other.getClass()!=this.getClass()) return false;
        DoubleValue check = (DoubleValue) other;
        return value==(double) check.getValue();
    }

    public int hashCode() {
        int result = (int) value;
        return  result;
    }

    public Value create(String s) {
        return new DoubleValue(Double.parseDouble(s));
    }

    public Value clone() {
        return new DoubleValue(value);
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean getSet() {
        return set;
    }
}
