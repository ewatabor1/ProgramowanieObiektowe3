package main.java.lab1.myExceptions;

public class DifferentSizedColumns extends Exception {
    private String[] names = new String[2];
    private int[] sizes = new int[2];
    public DifferentSizedColumns(){
        super();
    }
    public DifferentSizedColumns(String name1, String name2, int size1, int size2){
        names[0]=name1;
        names[1]=name2;
        sizes[0]=size1;
        sizes[1]=size2;

    }
    public void printMessage (){
        System.out.println("Podano kolumny o różnych długościach. Kolumna \""+names[0]+"\" ma rozmiar: "+sizes[0]+" Kolumna \""+names[1]+"\" ma rozmiar: "+sizes[1]);
    }


}
