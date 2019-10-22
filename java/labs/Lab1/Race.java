/*
* Lab 1 - Java Concurrency
* Race.java
* Sergio Mercado A01020382
* 14/10/2019
*/

import java.util.Random;

public class Race{
  public static void main(String[] args) {
    int tortoisePace, harePace;
    int track_length = 800;
    int hareRestTime = 100;

    //Random-type object creation

    Random random = new Random();

    tortoisePace = random.nextInt((10 - 3) + 1) + 3;
    harePace = random.nextInt((1000 - 500) + 1) + 500;

    //Racer object creation with modifiable parameters

    Racer hare = new Racer("Hare",hareRestTime,harePace,track_length);
    Racer tortoise = new Racer("Tortoise",0,tortoisePace,track_length);

    //Thread object creation

    Thread hr = new Thread(hare);
    Thread tr = new Thread(tortoise);

    try{
      hr.start(); //Start Hare thread
      tr.start(); //Start Tortoise thread

      while(hare.getWinner() == false && tortoise.getWinner() == false){
        hr.join(100);   //These two joins wait for all processes to start
        tr.join(100);   // at the same time.

        if(hare.getWinner()){ //This checks if there is a winner already if so:
          System.out.println("Hare won the race");
          tr.interrupt();      //Interrupt the other (Tortoise) thread.
          tr.join();           //Wait for process to be killed.
          break;
        }

        if(tortoise.getWinner()){ //This checks if there is a winner already if so:
          System.out.println("Tortoise won the race");
          hr.interrupt();       //Interrupt the other (Hare) thread.
          hr.join();            //Wait for process to be killed.
          break;
        }
      }
    }catch(InterruptedException ie){
      ie.printStackTrace();
    }
  }
}
