/*
* Lab 2 - Java
* Garden.java
* Sergio Mercado A01020382
* 22/10/2019
*/

import java.util.Random;

public class Garden implements Runnable{

  //This is the shared resource accessed by all threads
  //Therefore it must be volatile so that each thread
  //may see the change made to the variable by other threads
  public static volatile int people_in_garden = 0;

  //Parameters to define # of doors and # of total people
  //The person_id let's us identify which person goes where
  public int doors;
  public int total_people;
  public int person_id;


  //Constructor
  public Garden(int doors, int total_people, int person_id){
    this.doors = doors;
    this.total_people = total_people;
    this.person_id = person_id;
  }


  //Runnable method
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
          this.in(rdoor);        // Go in through a random door
          Thread.sleep(inTime);  // People stay in the Garden for a random
                                 // amount of time.

          rdoor = random.nextInt((doors - 1) + 1) + 1;
          this.out(rdoor);       // Go out through a random door
        }
      }
    }catch(InterruptedException ie){
      ie.printStackTrace();
    }

  }

  /*
  * Both methods "out" and "in" must be synchronized as to preserve
  * memory consistency across all threads.
  * Note that the prints are also inside the synchronized methods to
  * attempt to display the correct values at a given time
  */

  //This function decrements the people in the garden
  //everytime a person goes out
  public synchronized void out(int door){
    people_in_garden--;
    System.out.printf("Person %d going out through door %d\n",person_id, door);
    System.out.printf("People inside garden: %d\n", people_in_garden);
  }


  //This function increments the people in the garden
  //everytime a person goes in
  public synchronized void in(int door){
    people_in_garden++;
    System.out.printf("Person %d coming in from door %d\n",person_id, door);
    System.out.printf("People inside garden: %d\n", people_in_garden);
  }


  //Main Thread (aka: The Garden)
  public static void main(String[] args) {

    int n = 10; //Total # of people

    //Create 10 people
    for(int i = 1; i <= n; i++){

      //new Garden(# doors, # of people, person_id)
      new Thread(new Garden(2,n,i)).start();
    }
  }
}
