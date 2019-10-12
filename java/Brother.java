public class Brother implements Runnable{
  long id;
  Brother bro;
  Thread other;

  setBro(Brother bro){
    this.bro = bro;
  }

  public static void main(String[] args) {
    Brother br1 = new Brother();
    Brother br2 = new Brother();

    br1.setBro(br2);
    br2.setBro(br1);

    
  }
}
