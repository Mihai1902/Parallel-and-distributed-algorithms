package com.ace.ucv.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

   private final int size;

   public FileService(int size) {
      this.size = size;
   }

   public void write(int[] array, String file) {
      try (BufferedWriter outputWriter = new BufferedWriter(
              new FileWriter(Paths.get("").toAbsolutePath().
                      toString() + "\\" + file + "\\" + size + ".txt"))) {

         for (int i = 0; i < array.length; i++) {
            outputWriter.write(array[i] + " ");
            if ((i + 1) % 100 == 0) {
               outputWriter.newLine();
            }
         }

         outputWriter.flush();
      } catch (IOException exception) {
         exception.printStackTrace();
      }
   }

   public int[] read(String file) {
      File filename = new File(Paths.get("").toAbsolutePath().
              toString() + "\\" + file + "\\" + size + ".txt");
      try {
         List<String> strings = new ArrayList<>();
         List<String> lines = Files.readAllLines(filename.toPath());
         lines.parallelStream().forEachOrdered(line -> strings.addAll(Arrays.asList(line.split(" "))));
         List<Integer> numbers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());
         return numbers.stream().mapToInt(i -> i).toArray();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public int getSize() {
      return size;
   }
}
