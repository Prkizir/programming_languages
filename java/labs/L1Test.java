import java.util.Random;

public class L1Test implements Runnable{

    private static long winnerID;
    private volatile boolean winner = false;

    private static long rb_id;
    private static long tt_id;

    private long track;
    private int rb_sleep;
    private int rb_stop;
    private int tt_speed;

    public L1Test(long track, int rb_sleep, int tt_speed, int rb_stop){
        this.track = track;
        this.rb_sleep = rb_sleep;
        this.tt_speed = tt_speed;
        this.rb_stop = rb_stop;
    }

    @Override
    public void run(){
        long currentID = Thread.currentThread().getId();
        int pace = (Thread.currentThread().getId() == rb_id)? 1 : tt_speed;

        try {
            for (int i = 0; i < track; i += pace) {
                System.out.println(currentID + " is at " + i + " mile");
                if ((currentID == rb_id) && ((i % rb_stop) == 0)){
                    System.out.println(currentID + " rests at " + i + " mile");

                    Thread.sleep(rb_sleep);
                }

                if (i >= (track)) {
                    winnerID = Thread.currentThread().getId();
                    winner = true;
                }

                if (winner == true) {
                    System.out.println("Participant " + currentID + " finishes race at " + i + " mile");
                }
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {

        long track_length = 1000;
        int r_sleep = 200;

        Random random = new Random();

        int rr_stop = random.nextInt(501) + 500;
        int t_speed = random.nextInt(8) + 3;

        System.out.println("Rabbit Stops every " + rr_stop + " ft");
        System.out.println("Turtle Speed " + t_speed);

        L1Test rabbit = new L1Test(track_length,r_sleep,t_speed,rr_stop);
        L1Test turtle = new L1Test(track_length,r_sleep,t_speed,rr_stop);

        Thread t1 = new Thread(rabbit);
        Thread t2 = new Thread(turtle);

        rb_id = t1.getId();
        tt_id = t2.getId();

        System.out.println("Rabbit ID is: " + rb_id);
        System.out.println("Turtle ID is: " + tt_id + "\n");

        t1.start();
        t2.start();
    }
}
