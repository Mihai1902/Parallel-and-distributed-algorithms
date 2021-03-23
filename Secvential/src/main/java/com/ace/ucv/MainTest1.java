package com.ace.ucv;


public class MainTest1 {
   public static void main(String[] args) {
      QuickSort quickSort = new QuickSort();
      String[] files = {"test_1/10e1dig5.txt", "test_1/10e2dig5.txt", "test_1/10e3dig5.txt", "test_1/10e4dig5.txt",
              "test_1/10e5dig5.txt", "test_1/10e6dig5.txt", "test_1/10e7dig5.txt", "test_1/10e8dig5.txt"};
      int iterator = 1;
      for (String file : files) {
         int capacity = 10 * iterator;
         iterator = iterator * 10;
         int[] array = File.read(file, capacity);
         float start = System.nanoTime();
         quickSort.sort(array, 0, array.length - 1);
         float finish = System.nanoTime();
         System.out.println((finish - start) / 1_000_000 + " - milliseconds " + capacity + " numbers");
      }
   }
}
