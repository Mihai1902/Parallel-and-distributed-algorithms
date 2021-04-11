package com.ace.ucv.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {

   public static int[] read(String fileName, int capacity) {
      File file = new File(fileName);
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
}
