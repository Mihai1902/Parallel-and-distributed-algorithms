import com.ace.ucv.util.QuickSortMultiThreading;
import com.ace.ucv.util.Util;

import java.util.concurrent.ForkJoinPool;

public class Main {
   public static void main(String[] args) {

      String absolutePath = "../Parallel/src/main/resources/";
      String[] files = {"test_1/10e1dig5.txt", "test_1/10e2dig5.txt", "test_1/10e3dig5.txt", "test_1/10e4dig5.txt",
              "test_1/10e5dig5.txt", "test_1/10e6dig5.txt", "test_1/10e7dig5.txt", "test_1/10e8dig5.txt"};

      int i = 1;
      for (String file : files) {
         int capacity = 10 * i;
         i *= 10;
         int[] array = Util.read(absolutePath + file, capacity);
         long startTime = System.nanoTime();
         ForkJoinPool pool = ForkJoinPool.commonPool();
         pool.invoke(new QuickSortMultiThreading(0, array.length - 1, array));
         long endTime = System.nanoTime();

         System.out.println((endTime - startTime) / 1_000_000 + " - milliseconds " + capacity + " numbers");
      }
   }
}
