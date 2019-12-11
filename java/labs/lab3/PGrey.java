/*
* Sergio Mercado
* Java : Lab 3 - Recursive Action
* 25/10/2019
*/

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PGrey extends RecursiveAction{
  private static final int MIN = 1_000; //Grain size

  private int start, end, numCols;
  private BufferedImage img;

  //Constructor
  public PGrey(int start, int end, int numCols, BufferedImage img){
    this.start = start;
    this.end = end;
    this.numCols = numCols;
    this.img = img;
  }

  //This is the sequential method: We use this part to apply the filter to the whole image sequentially
  //    as well as in the grain part of the parallel version (this is our base case where we can do something
  //    sequentially without losing efficiency)

  protected void computeDirectly(){
    for (int i = start; i < end; i++) {
        for (int j = 0; j < numCols; j++) {
            int rgb = img.getRGB(j, i);  // rgb contian all number coded within a single integer concatenaed as red/green/blue

            //use this separation to explore with different filters and effects  you need to do the invers process to encode red green blue into a single value again
            //separation of each number
            int red = rgb & 0xFF;  // & uses  0000000111 with the rgb number to eliminate all the bits but the las 3 (which represent 8 position which are used for 0 to 255 values)
            int green = (rgb >> 8) & 0xFF;  // >> Bitwise shifts 8 positions  & makes  uses  0000000111 with the number and eliminates the rest
            int blue = (rgb >> 16) & 0xFF; // >> Bitwise shifts 16 positions  & makes  uses  0000000111 with the number and eliminates the rest

            //rgb = ~rgb; // ~ negation of the complete pixel value

            float L = (float) (0.2126 * (float) red + 0.7152 * (float) green + 0.0722 * (float) blue);
            // (234, 176, 3) // yellow

            int color;
            color = 234 * (int) L / 255;
            color = (color << 8) | 176 * (int) L / 255;
            color = (color << 8) | 3 * (int) L / 255;

            img.setRGB(j, i, color); // sets the pixeles to specified color  (negative image)
        }
    }
  }

  //This compute method is the one in charge of splitting the work
  //    between threads
  @Override
  protected void compute(){
    if((end - start) <= MIN){  //Check if the size of rows is smaller or equal to
      computeDirectly();       //  the grain size. If so, do the work sequentially
                               //  aka: our base case
    }else{                     // Otherwise split the work and join it
      int mid = start + ((end-start)/2);  //half location
      invokeAll(new PGrey(start, mid, numCols, img),  //"Left" side
                new PGrey(mid, end,numCols, img)  //"Right" side
      );
    }
  }

  //Main thread
  public static void main(String[] args) throws IOException{
    BufferedImage img = ImageIO.read(new File("download3.jpeg")); //Read image file
    int numRows = img.getHeight();  //get height of image
    int numCols = img.getWidth();   //get width of image

    ForkJoinPool pool;  //Instantiate a ForkJoin pool

    long startTime, stopTime;  // Control variables for mesuring
    double acum = 0;           // computation time

    double seq,par; // Variables for sequential time and parallel time respectively

    int N = 10;

    //Begin Sequential
    for(int i = 0; i < N; i++){ //Run this process N times

    img = ImageIO.read(new File("download3.jpeg")); // Needed to restart the operation
                                                    // as to not overlap the result over the same
                                                    // image file

    startTime = System.currentTimeMillis(); //Start time of operation

    PGrey pgrey = new PGrey(0, numRows, numCols, img); //Creating PGrey object
    pgrey.computeDirectly(); //Apply the sequential computation to the whole image

    stopTime = System.currentTimeMillis(); //End time of operation

    ImageIO.write(img,"jpeg", new File("_sequential.jpeg")); //Write result to an image file

    acum += (stopTime - startTime); //Add all the measured time taken for the same computation

    }

    seq = acum/N; //Average sequential time

    System.out.printf("Sequential Time: %f\n", seq);
    //End Sequential


    //Begin Parallel

    acum = 0;

    for(int i = 0; i < N; i++){
      img = ImageIO.read(new File("download3.jpeg")); // Needed to restart the operation
                                                      // as to not overlap the result over the same
                                                      // image file

      startTime = System.currentTimeMillis();         //Start time of operation

      pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()); // Calls the forkjoin pool
                                                                           // to have every available processor
                                                                           //on standby to receive the work load
                                                                           //for all their threads
      pool.invoke(new PGrey(0,numRows,numCols, img));  //Call the invoke method that will run the computation of the
                                                       //  of the filter into the image in parallel

      stopTime = System.currentTimeMillis();          //End time of operation

      acum += (stopTime - startTime);

      ImageIO.write(img,"jpeg", new File("_parallel.jpeg"));  //Write result to image file

    }

    par = acum/N; // Average parallel time

    System.out.printf("Parallel Time: %f\n", par);
    //End Parallel

    //Print the speedup
    System.out.printf("Speedup: %f\n", (seq/par));
  }
}
