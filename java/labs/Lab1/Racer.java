import java.util.Random;

public class Racer implements Runnable{
  public static volatile boolean winner = false;
  private String name;
  private int restTime, pace, distance;

  public Racer(String name, int restTime, int pace, int distance){
    this.name = name;
    this.restTime = restTime;
    this.pace = pace;
    this.distance = distance;
  }

  @Override
  public void run(){
    int i = 0;

    for(i = 0; i < distance; i+=pace){
      System.out.println(name + " is at mile # " + i);
      try{
        if( name.equals("Hare") &&
            ((i % pace) == 0) && (i > 0)){
          System.out.println("The Hare is sleeping at mile no. " + i);
          Thread.sleep(restTime);
        }
      }catch(InterruptedException ie){
        ie.printStackTrace();
      }
    }

    System.out.println("Winner is " + name);
  }
}
