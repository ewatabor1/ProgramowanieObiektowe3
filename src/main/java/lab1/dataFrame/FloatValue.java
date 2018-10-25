package main.java.lab1.dataFrame;

public class FloatValue extends Value {
    private float value;
    public FloatValue(){
        value=0;
    }
    public FloatValue(Float value){
        this.value=value;
    }
    public FloatValue(FloatValue o1){
        value=o1.value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public FloatValue add(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new FloatValue(value+(float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue sub(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new FloatValue(value-(float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue mul(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new FloatValue(value*(float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue div(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new FloatValue(value/(float) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FloatValue pow(Value o1) {
        int b;
        float result=1;
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue) {
            b= (int) o1.getValue();
            if(b>0){
                for (int i=0;i<b;i++){
                    result=result*value;
                }
            }
            return new FloatValue(result);
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
            return value<(Float) o1.getValue();
        }
        return false;
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
    public Object getValue() {
        return value;
    }
}
