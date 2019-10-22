/*
* Lab 1 - Java Concurrency
* Racer.java
* Sergio Mercado A01020382
* 14/10/2019
*/

import java.util.Random;

public class Racer implements Runnable{
  public volatile boolean winner = false; //Volatile boolean variable to check for winner
  private String name;  //Racer name
  private int restTime, pace, distance; //Other parameters for each racer

  //Constructor
  public Racer(String name, int restTime, int pace, int distance){
    this.name = name;
    this.restTime = restTime;
    this.pace = pace;
    this.distance = distance;
  }


  //Run method for the actual race
  @Override
  public void run(){
    int i = 0;

    //Runs as long as it's not interrupted and the current distance ran is less than
    // the total distance of the race
    while(!Thread.currentThread().isInterrupted() && i < distance){
      System.out.println(name + " is at mile # " + i);
      i += pace; //Pace increments

      //Once a racer gets to the race distance exit the loop
      if(i >= distance){
        break;
      }

      try{
        if(name.equals("Hare") && ((i % pace) == 0) && (i != 0)){  //Checks for the Hare pace so that it rests every N miles it runs
          System.out.println("The Hare is sleeping at mile no. " + i);
          Thread.sleep(restTime);  //Sleep the thread
        }
      }catch(InterruptedException ie){ //Catch an external interruption
        System.out.println(name + " finished the race at " + i + " mile"); //Print the current state of any racer
        Thread.currentThread().interrupt();
      }
    }

    //Set the winner variable to true because the Racer finished the race.
    winner = true;
  }

  //winner boolean variable getter
  public boolean getWinner(){
    return winner;
  }

}
