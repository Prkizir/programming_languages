/*
* Sergio Mercado A01020382
* Java: Battle.java Main thread
*/


import java.io.*;
import java.awt.*;
import java.util.*;

public class Battle{

  public static void main(String[] args) {
    int dnum = 5;
    int rnum = 10;

    Drone drones[] = new Drone[dnum];
    Rider riders[] = new Rider[rnum];

    Thread d_th[] = new Thread[dnum];
    Thread r_th[] = new Thread[rnum];

    for(int i = 0; i < rnum; i++){
      riders[i] = new Rider(i);
      r_th[i] = new Thread(riders[i]);
    }

    for(int i = 0; i < dnum; i++){
      drones[i] = new Drone(i);
      d_th[i] = new Thread(drones[i]);
    }

    for(int i = 0; i < rnum; i++){
      riders[i].setKillList(drones);
    }

    for(int i = 0; i < dnum; i++){
      drones[i].setDatabase(riders);
    }

    try{

      for(int i = 0; i < 5; i++){
        d_th[i].start();
      }

      for(int i = 0; i < 10; i++){
        r_th[i].start();
      }

      boolean war = true;

      while(war){
        for(int i = 0; i < 5; i++){
          d_th[i].join();
        }

        for(int i = 0; i < 10; i++){
          r_th[i].join();
        }

      }
    }catch(InterruptedException ie){
      ie.printStackTrace();
    }
  }
}
