package pkg1;

public class Main {
    public static void main(String [] argv){
        A zmienna = new A(5,"Lala");
        B zmienna2 = new B(10,"Juhu");
        zmienna.callIncrement();
        zmienna2.decrement();


        zmienna.callDecrement();
        zmienna2.callDecrement();
        zmienna.callChangeName();
        zmienna2.changeName();
        zmienna2.callChangeName();
    }
}
