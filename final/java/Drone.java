/*
* Sergio Mercado A01020382
* Java: Drone.java Drone class
*/


import java.io.*;
import java.awt.*;
import java.util.*;

public class Drone implements Runnable{
  public volatile boolean alive = true;
  public volatile int numberofdeaths = 0;
  int id;
  Rider riders[];

  public Drone(int id){
    this.id = id;
  }

  public void setDatabase(Rider riders[]){
    this.riders = riders;
  }

  public synchronized void getshot(){
    if(alive){
      System.out.println("BEEP BOOP BOOP BUUUUUUUU... ERROR: " + id + " >> MAIN CORE DAMAGED");
      alive = false;
      numberofdeaths++;
    }else{
      System.out.println("BZZT BZZT");
    }
  }

  public synchronized void shoot(int who){
    System.out.println("HIT! DRONE " + id + " SHOT RIDER " + who + ". EXTEEEEEEERMINAAATE");
    riders[who].getshot();
  }

  public boolean isAlive(){
    return alive;
  }

  public int getDroneDeaths(){
    return numberofdeaths;
  }

  @Override
  public void run(){
    Random random = new Random();
    while(!Thread.currentThread().isInterrupted()){
      int chance = random.nextInt(3);

      if(chance < 2){
        int shoot_at = random.nextInt(riders.length);
        shoot(shoot_at);
      }else{
        System.out.println("ERROR! DRONE " + id + " MISSED: ADJUSTING VARIABLES...");
      }
    }
  }
}
