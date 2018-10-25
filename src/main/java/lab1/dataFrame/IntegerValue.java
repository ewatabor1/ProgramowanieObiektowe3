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
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new IntegerValue(value+(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue sub(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new IntegerValue(value-(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue mul(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new IntegerValue(value*(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue div(Value o1) {
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue){
            return new IntegerValue(value/(Integer) o1.getValue());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public IntegerValue pow(Value o1) {
        int b,result=1;
        if (o1 instanceof IntegerValue || o1 instanceof DoubleValue || o1 instanceof FloatValue) {
            b= (int) o1.getValue();
            if(b>0){
                for (int i=0;i<b;i++){
                    result=result*value;
                }
            }
            return new IntegerValue(result);
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
    public Object getValue() {
        return value;
    }


}
