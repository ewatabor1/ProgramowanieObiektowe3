package main.java.lab1.groupBy;

import main.java.lab1.dataFrame.DataFrame;
import main.java.lab1.myExceptions.WrongTypeInColumn;

public interface GroupBy {
    DataFrame max() throws WrongTypeInColumn;
    DataFrame min() throws WrongTypeInColumn;
    DataFrame mean() throws WrongTypeInColumn;
    DataFrame std() throws WrongTypeInColumn;
    DataFrame sum() throws WrongTypeInColumn;
    DataFrame var() throws WrongTypeInColumn;
    DataFrame apply(Applyable applyable) throws WrongTypeInColumn;
}
