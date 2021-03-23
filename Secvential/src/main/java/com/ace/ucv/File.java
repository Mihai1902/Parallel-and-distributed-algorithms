package com.ace.ucv;

import java.io.*;
import java.util.*;

public class File {

   public static void write(int quantity) {
      ArrayList<Integer> numbers = new ArrayList<>();
      String filePath = "test_2/" + quantity + "numbers.txt";
      try (PrintWriter file = new PrintWriter(
              new BufferedWriter(
                      new FileWriter(filePath)));
      ) {
         for (int i = 1; i <= quantity; i++) {
            numbers.add(i);
         }
         Collections.shuffle(numbers);
         numbers.forEach(file::println);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static int[] read(String fileName, int capacity) {
      java.io.File file = new java.io.File(fileName);
      int[] arr = new int[capacity];
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
         String text;

         int i = 0;
         while ((text = reader.readLine()) != null) {
            arr[i] = (Integer.parseInt(text));
            i++;
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return arr;
   }

   public static int[] convertIntegers(List<Integer> integers) {
      int[] ret = new int[integers.size()];
      Iterator<Integer> iterator = integers.iterator();
      for (int i = 0; i < ret.length; i++)
      {
         ret[i] = iterator.next();
      }
      return ret;
   }
}

