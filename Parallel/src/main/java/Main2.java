import com.ace.ucv.util.QuickSortMultiThreading;
import com.ace.ucv.util.Util;

import java.util.concurrent.ForkJoinPool;

public class Main2 {
   public static void main(String[] args) {

      String absolutePath = "../Parallel/src/main/resources/";
      String[] files = {
              "test_2/2numbers.txt", "test_2/4numbers.txt", "test_2/8numbers.txt",
              "test_2/16numbers.txt", "test_2/32numbers.txt", "test_2/64numbers.txt", "test_2/128numbers.txt",
              "test_2/256numbers.txt", "test_2/512numbers.txt", "test_2/1024numbers.txt", "test_2/2048numbers.txt",
              "test_2/4096numbers.txt", "test_2/8192numbers.txt", "test_2/16384numbers.txt", "test_2/32768numbers.txt",
              "test_2/65536numbers.txt", "test_2/131072numbers.txt", "test_2/262144numbers.txt", "test_2/524288numbers.txt",
              "test_2/1048576numbers.txt", "test_2/2097152numbers.txt", "test_2/4194304numbers.txt", "test_2/8388608numbers.txt",
              "test_2/16777216numbers.txt", "test_2/33554432numbers.txt", "test_2/67108864numbers.txt" };

      int i = 1;
      int capacity;
      for (String file: files) {
         capacity = i * 2;
         int[] array = Util.read(absolutePath + file, capacity);
         long start = System.nanoTime();
         ForkJoinPool pool = ForkJoinPool.commonPool();
         pool.invoke(new QuickSortMultiThreading(0, array.length - 1, array));
         long finish = System.nanoTime();
         System.out.println((finish - start) / 1_000_000 + " - milliseconds " + capacity + " numbers");
         i *= 2;
      }
   }
}
