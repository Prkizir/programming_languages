import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloRunnable implements Runnable{
    int id;

    public HelloRunnable(int nid){
        id = nid;
    }

    public void run(){
        System.out.println("Hello from a thread! " + id);
        System.out.println(Thread.currentThread().getId());
    }

    public static void main(String args[]){
        //for(int i = 0; i < 5; i++){
        //    (new Thread(new HelloRunnable(i))).start();
        //}

        HelloRunnable h1 = new HelloRunnable(100);
        HelloRunnable h2 = new HelloRunnable(200);
        HelloRunnable h3 = new HelloRunnable(300);
        HelloRunnable h4 = new HelloRunnable(400);
        HelloRunnable h5 = new HelloRunnable(500);

        Thread t1 = new Thread(h1);
        Thread t2 = new Thread(h2);
        Thread t3 = new Thread(h3);
        Thread t4 = new Thread(h4);
        Thread t5 = new Thread(h5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            System.out.println("CPUs \n"+ Runtime.getRuntime().availableProcessors());
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }


    }
}
