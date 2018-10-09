package main.java.lab1.dataFrame;

import java.util.Scanner;

public class LiczbyPierwsze {
    public static void main(String[] argv){
        System.out.print("Wpisz liczę: ");
        Scanner odczyt = new Scanner(System.in);
        int a = odczyt.nextInt();
        LiczbyPierwszeSearch liczbyP = new LiczbyPierwszeSearch(a);



    }
}
