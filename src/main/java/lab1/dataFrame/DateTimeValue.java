package main.java.lab1.dataFrame;

import java.util.Date;

public class DateTimeValue extends Value {
    private Date date;
    public DateTimeValue (){
        this.date=new Date();
    }
    public DateTimeValue(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public Value add(Value o1) {
        if(o1 instanceof DateTimeValue) {
            Date valueDate = (Date) o1.getValue();
            return new DateTimeValue(new Date(this.date.getYear(),
                    this.date.getMonth(),
                    this.date.getDay(),
                    this.date.getHours() + valueDate.getHours(),
                    this.date.getMinutes() + valueDate.getMinutes()));
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Value sub(Value o1) {
        if(o1 instanceof DateTimeValue) {
            Date valueDate = (Date) o1.getValue();
            return new DateTimeValue(new Date(this.date.getYear(),
                    this.date.getMonth(),
                    this.date.getDay(),
                    this.date.getHours() + valueDate.getHours(),
                    this.date.getMinutes() + valueDate.getMinutes()));
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Value mul(Value o1) {
        return new DateTimeValue(this.date);
    }

    @Override
    public Value div(Value o1) {
        return new DateTimeValue(this.date);
    }

    @Override
    public Value pow(Value o1) {
        return new DateTimeValue(this.date);
    }

    @Override
    public boolean eq(Value o1) {
        if(o1 instanceof DateTimeValue) {
            return this.date.equals((Date) o1.getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value o1) {
        if(o1 instanceof DateTimeValue) {
            return this.date.compareTo((Date) o1.getValue()) < 0;
        }
        return false;
    }

    @Override
    public boolean gte(Value o1) {
        if(o1 instanceof DateTimeValue) {
            return this.date.compareTo((Date) o1.getValue()) > 0;
        }
        return false;
    }

    @Override
    public boolean neq(Value o1) {
        if(o1 instanceof DateTimeValue) {
            return !this.date.equals((Date) o1.getValue());
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this==other) return true;
        if (other==null || other.getClass()!=this.getClass()) return false;
        DateTimeValue check = (DateTimeValue) other;
        return date.equals((Date) check.getValue());
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public Value create(String s) {
        return new DateTimeValue(new Date(Date.parse(s)));
    }

    @Override
    public Object getValue() {
        return date;
    }
}
