import java.util.Random;

public class Race{
  public static void main(String[] args) {
    int tortoisePace, harePace;

    int track_length = 1000;
    int hareRestTime = 200;

    Random random = new Random();

    tortoisePace = random.nextInt(8) + 3;
    harePace = random.nextInt(501) + 500;

    Racer hare = new Racer("Hare",hareRestTime,harePace,track_length);
    Racer tortoise = new Racer("Tortoise",0,tortoisePace,track_length);

    Thread hr = new Thread(hare);
    Thread tr = new Thread(tortoise);

    hr.start();
    tr.start();
  }
}
