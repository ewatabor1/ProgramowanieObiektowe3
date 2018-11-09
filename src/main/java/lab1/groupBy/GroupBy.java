package main.java.lab1.groupBy;

import main.java.lab1.dataFrame.DataFrame;

public interface GroupBy {
    DataFrame max();
    DataFrame min();
    DataFrame mean();
    DataFrame std();
    DataFrame sum();
    DataFrame var();
    DataFrame apply(Applyable applyable);
}
