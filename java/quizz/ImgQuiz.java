
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ImgQuiz extends RecursiveAction{
  protected static final int MIN = 1_000;
  protected static final int TROWS = 10_000;
  protected static final int TCOLS = 10_000;
  protected int start, end, ncols;
  protected int src[][],dest[][];

  public ImgQuiz(int start, int end, int ncols, int src[][], int dest[][]){
    this.start = start;
    this.end = end;
    this.ncols = ncols;
    this.src = src;
    this.dest = dest;
  }

  protected void computeDirectly(){
    for(int i = start; i < end; i++){
      for(int j = 0; j < ncols; j++){
        dest[i][j] = (-src[i][j])^(i%2);
      }
    }
  }

  @Override
  protected void compute(){
    if((end - start) <= MIN){
      computeDirectly();
    }else{
      int mid =  (end - start)/2;
      invokeAll(new ImgQuiz(start, mid, ncols, src, dest),
                new ImgQuiz(mid, end, ncols, src, dest)
      );
    }
  }

  public static void main(String[] args) {

    ForkJoinPool pool;
    int arr[][] = new int[TROWS][TCOLS];
    int res[][] = new int[TROWS][TCOLS];

    for(int i = 0; i < TROWS; i++){
      for(int j = 0; j < TCOLS; j++){
        arr[i][j] = 1;
      }
    }

    pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    pool.invoke(new ImgQuiz(0, TROWS, TCOLS, arr, res));

    for(int i = 0; i < TROWS; i++){
      for(int j = 0; j < TCOLS; j++){
        System.out.printf("%d ",res[i][j]);
      }
    }
  }
}
