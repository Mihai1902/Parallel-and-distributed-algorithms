package com.ace.ucv.service;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class QuickSortMultiThreading extends RecursiveTask<Integer> {

   int start, end;
   int[] array;

   public QuickSortMultiThreading(int start, int end, int[] array) {
      this.array = array;
      this.start = start;
      this.end = end;
   }

   @Override
   protected Integer compute() {
      if (start >= end)
         return null;

      int partition = partition(start, end, array);

      QuickSortMultiThreading left = new QuickSortMultiThreading(start, partition - 1, array);
      QuickSortMultiThreading right = new QuickSortMultiThreading(partition + 1, end, array);

      left.fork();
      right.compute();

      left.join();
      return null;
   }

   private int partition(int start, int end, int[] array) {
      int i = start;
      int j = end;

      int pivot = new Random().nextInt(j - i) + i;

      int t = array[j];
      array[j] = array[pivot];
      array[pivot] = t;
      j--;

      while (i <= j) {

         if (array[i] <= array[end]) {
            i++;
            continue;
         }

         if (array[j] >= array[end]) {
            j--;
            continue;
         }

         t = array[j];
         array[j] = array[i];
         array[i] = t;
         j--;
         i++;
      }
      t = array[j + 1];
      array[j + 1] = array[end];
      array[end] = t;
      return j + 1;
   }
}
