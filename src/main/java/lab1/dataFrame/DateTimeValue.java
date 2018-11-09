package main.java.lab1.dataFrame;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeValue extends Value {
    private Date date;
    public DateTimeValue (){
        this.date=new Date();
    }
    public DateTimeValue(Date date) {
        this.date = date;
    }

    public DateTimeValue (String s) throws ParseException {
        date =new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    @Override
    public String toString() {
        DateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String requiredDate = dt1.format(date).toString();
        return requiredDate;
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
        return o1;
        //throw new IllegalArgumentException();
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
    public Value clone() {
        return new DateTimeValue(date);
    }
    @Override
    public Date getValue() {
        return date;
    }
}
