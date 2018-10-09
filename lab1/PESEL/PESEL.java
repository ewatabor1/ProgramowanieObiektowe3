package main.java.lab1.PESEL;

import java.util.Scanner;

public class PESEL {
    private String pesel;
    public PESEL (String _pesel){
        pesel=_pesel;
    }
    public boolean check (){
        if (pesel.length()!=11 || !(pesel.matches("[0-9]*"))) return false;
        int temp1 = Integer.parseInt(pesel.substring(0,2));
        int temp2 = Integer.parseInt(pesel.substring(2,4));
        int temp3 = Integer.parseInt(pesel.substring(4,6));
        if (temp2>12){
            if (!(temp2<32 && temp1<19)) return false;
        }
        if (temp2==1 || temp2==3 || temp2==5 || temp2==7 || temp2==8 || temp2==10 || temp2==12){
            if (temp3>31) return false;
        }
        else if (temp2==2){
            if (temp3>28){
                if (!(temp3==29 && temp1%4==0)) return false;
            }
        }
        else{
            if (temp3>30) return false;
        }
        return true;


    }
}
