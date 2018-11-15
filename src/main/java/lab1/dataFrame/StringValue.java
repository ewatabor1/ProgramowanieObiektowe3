package main.java.lab1.dataFrame;

public class StringValue extends Value {
    private String value;
    public StringValue (){
        value="";
    }
    public StringValue (String value){
        this.value=value;
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value add(Value o1) {
        return new StringValue(this.value + value.toString());
    }

    @Override
    public Value sub(Value o1) {
        return new StringValue(this.value.replace(value.toString(), ""));
    }

    @Override
    public Value mul(Value o1) {
        if(o1 instanceof IntegerValue) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (int)o1.getValue(); i++) {
               stringBuilder.append(this.value);
            }
            return new StringValue(stringBuilder.toString());
        }
        return new StringValue(this.value);
    }


    @Override
    public Value div(Value o1) {
        return new StringValue(this.value);
    }

    @Override
    public Value pow(Value o1) {
        if(o1 instanceof IntegerValue) {
            return mul(o1);
        }
        return new StringValue(this.value);
    }

    @Override
    public boolean eq(Value o1) {
        return this.value.equals(o1.toString());
    }

    @Override
    public boolean lte(Value o1) {
        return this.value.compareTo(o1.toString()) < 0;
    }

    @Override
    public boolean gte(Value o1) {
        return this.value.compareTo(o1.toString()) > 0;
    }

    @Override
    public boolean neq(Value o1) {
        return !this.value.equals(o1.toString());
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof StringValue) {
            return this.value.equals(other.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Value create(String s) {
        return new StringValue(s);
    }

    @Override
    public Value clone() {
        return new StringValue(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
