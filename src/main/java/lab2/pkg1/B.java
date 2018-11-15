package pkg1;

public class B extends A{
    protected void decrement(){
        this.number-=2;
    };
    void changeName(){
        this.name=this.name+".";
    };
    private void increemnt(){
        this.number+=2;
    };
    public B(int number,String name){
        super(number,name);
    }
}
