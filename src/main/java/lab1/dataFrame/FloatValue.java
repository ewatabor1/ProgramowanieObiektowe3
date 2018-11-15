package main.java.lab1.dataFrame;

public class FloatValue extends Value {
    private float value;
    public FloatValue(){
        value=0;
    }
    public FloatValue(float value){
        this.value=value;
    }
    public FloatValue(FloatValue o1){
        value=o1.value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public FloatValue(String string) {
        value = Float.valueOf(string);
    }

    @Override
    public FloatValue add(Value o1) {
        if (o1 instanceof FloatValue){
            return new FloatValue(value+(Float) o1.getValue());
        }
        return new FloatValue(value);
        //throw new IllegalArgumentException();
    }

    @Override
    public FloatValue sub(Value o1) {
        if (o1 instanceof FloatValue){
            return new FloatValue(value-(Float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue mul(Value o1) {
        if (o1 instanceof FloatValue){
            return new FloatValue(value*(Float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue div(Value o1) {
        if (o1 instanceof FloatValue || o1 instanceof IntegerValue){
            float temp;
            if (o1 instanceof FloatValue) temp=((FloatValue) o1).getValue();
            else temp=((IntegerValue) o1).getValue();
            if (temp == 0) {
                throw new IllegalArgumentException();
            }
            return new FloatValue(value/temp);
        }

        return new FloatValue(value);
//        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue pow(Value o1) {
        if (o1 instanceof FloatValue) {
            float val = (float) o1.getValue();
            double valDouble = val;
            double thisDouble = (double) this.value;
            return new FloatValue((float) Math.pow(thisDouble, valDouble));
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean eq(Value o1) {
        if (o1 instanceof FloatValue) {
            return value==(Float) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean lte(Value o1) {
        if (o1 instanceof FloatValue) {
            Float val = (Float) o1.getValue();
            return this.value < val;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean gte(Value o1) {
        if (o1 instanceof FloatValue) {
            return value>(Float) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value o1) {
        if (o1 instanceof FloatValue) {
            return value!=(Float) o1.getValue();
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this==other) return true;
        if (other==null || other.getClass()!=this.getClass()) return false;
        FloatValue check = (FloatValue) other;
        return value==(float) check.getValue();
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    @Override
    public Value create(String s) {
        return new FloatValue(Float.parseFloat(s));
    }

    @Override
    public Value clone() {
        return new FloatValue(value);
    }


    @Override
    public Float getValue() {
        return value;
    }
}
