public class HelloThread extends Thread{
    @Override
    public void run(){
        System.out.println("Hello From a Thread!");
    }

    public static void main(String args[]){
        (new HelloThread()).start();
    }
}
