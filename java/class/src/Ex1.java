public class Ex1 implements Runnable{

    public static long id1;
    public static long id2;

    public Ex1(){

    }

    public void run() {

        long printID = (Thread.currentThread().getId() == id1)? id2 : id1;
        System.out.println("My ID is " + Thread.currentThread().getId() + " and my Brother's ID is " + printID);
    }

    public static void main(String args[]){

        Ex1 f1 = new Ex1();
        Ex1 f2 = new Ex1();

        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);

        id1 = t1.getId();
        id2 = t2.getId();

        t1.start();
        t2.start();
    }
}
