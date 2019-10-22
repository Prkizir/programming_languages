import java.util.Random;

public class Garden implements Runnable{
  public static volatile int people_in_garden = 0;
  public int doors;
  public int total_people;
  public int person_id;

  public Garden(int doors, int total_people, int person_id){
    this.doors = doors;
    this.total_people = total_people;
    this.person_id = person_id;
  }

  @Override
  public void run(){

    Random random = new Random();
    int rdoor,bin,inTime;

    try{
      for(int i = 0; i < 20; i++){
        rdoor = random.nextInt((doors - 1) + 1) + 1;
        bin = random.nextInt(2);
        inTime = random.nextInt(101);
        if(bin == 0){
          System.out.println("Person " + person_id + " coming in from door " + rdoor);
          this.in();
          Thread.sleep(inTime);
          rdoor = random.nextInt((doors - 1) + 1) + 1;
          System.out.println("Person " + person_id + " going out the door " + rdoor);
          this.out();
        }

        System.out.println("People inside garden: " + people_in_garden);
      }
    }catch(InterruptedException ie){
      ie.printStackTrace();
    }

  }

  public synchronized void out(){
    people_in_garden--;
  }

  public synchronized void in(){
    people_in_garden++;
  }

  public static void main(String[] args) {

    int people = 10;

    for(int i = 1; i <= people; i++){
      new Thread(new Garden(2,people,i)).start();
    }
  }
}
