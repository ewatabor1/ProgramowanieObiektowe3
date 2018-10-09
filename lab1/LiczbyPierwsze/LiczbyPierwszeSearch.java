package main.java.lab1.dataFrame;

import java.util.ArrayList;

public class LiczbyPierwszeSearch {
    private int[] tab;
    private int sqrt;

    public LiczbyPierwszeSearch(int b){
        sqrt = (int) Math.sqrt(b);
//wypelnianie tablicy liczbami z przedzialu [a,b]
        tab = new int[b-2+1];
        for (int i = 0; i<tab.length; i++){
            tab[i] = 2+i;
        }
        odsiew();
        drukuj();
    }

    //selekcja liczb pierwszych zgodnie z algorytmem Erastotenesa
    public void odsiew (){
        int index = 0;
        while (tab[index] <= sqrt ){
            for (int i = index+1; i<tab.length; i++){
                if (tab[i]%tab[index] == 0) tab[i]=0;
            }
            do{
                index++;
            }while(tab[index]==0);
        }
    }

    //wydruk wynikow
    public void drukuj(){
        for (int i = 0; i<tab.length; i++){
            if (tab[i] != 0){
                System.out.println(tab[i]);
            }
        }
    }
}
