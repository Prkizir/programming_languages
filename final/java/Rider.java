/*
* Sergio Mercado A01020382
* Java: Rider.java Rider class
*/


import java.io.*;
import java.awt.*;
import java.util.*;

public class Rider implements Runnable{
  public volatile boolean alive = true;
  public volatile int numberofdeaths = 0;
  int id;
  Drone drones[];

  public Rider(int id){
    this.id = id;
  }

  public void setKillList(Drone drones[]){
    this.drones = drones;
  }

  public synchronized void getshot(){
    if(alive){
      System.out.println("Poor fella, didn't see it coming: RIDER " + id + " IS DEAD!!!!");
      alive = false;
      numberofdeaths++;
    }else{
      System.out.println("He already dead!!!" + id);
    }
  }

  public synchronized void shoot(int who){
    System.out.println("Yeehaw! Rider " + id + " shot drone number " + who);
    drones[who].getshot();
  }

  public boolean isAlive(){
    return alive;
  }

  public int getRiderDeaths(){
    return numberofdeaths;
  }

  @Override
  public void run(){
    Random random = new Random();

    while(!Thread.currentThread().isInterrupted()){
      int chance = random.nextInt(3);

      if(chance < 2){
        int shoot_at = random.nextInt(drones.length);
        shoot(shoot_at);
      }else{
        System.out.println("Darnit! Rider " + id + " missed!");
      }
    }
  }

}
