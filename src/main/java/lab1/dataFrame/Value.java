package main.java.lab1.dataFrame;

public abstract class Value {
    public abstract String toString();
    public abstract Value add(Value o1);
    public abstract Value sub(Value o1);
    public abstract Value mul(Value o1);
    public abstract Value div(Value o1);
    public abstract Value pow(Value o1);
    public abstract boolean eq(Value o1);
    public abstract boolean lte(Value o1);
    public abstract boolean gte(Value o1);
    public abstract boolean neq(Value o1);
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract Value create(String s);
    public abstract Value clone();
    public abstract Object getValue();
}
