package pkg1;
import java.util.Scanner;

public class A {
    int number;
    protected String name;
    public A (int number, String name){
        this.number=number;
        this.name=name;
    }
    public void callDecrement (){
        decrement();
        System.out.print("Wartość pola po zmianie: "+number+"\n");
    }
    public void callIncrement (){
        increment();
        System.out.print("Wartość pola po zmianie: "+number+'\n');
    }
    public void callChangeName() {
        changeName();
        System.out.print("Nazwa po zmianie: "+name+'\n');
    }

    private void decrement() {
        this.number-=1;
    }

    private void increment() {
        this.number+=1;
    }
    private void changeName() {
        this.name=name+"!";
    }

}
