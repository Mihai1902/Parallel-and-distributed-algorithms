package com.ace.ucv.util;


import com.ace.ucv.service.QuickSort;
import com.ace.ucv.service.FileService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

public class MainUtility {

   public static final String DOT_CHARACTER = "\\.";

   public static final int FILE_SIZE_INDEX = 0;

   public static void runApplication(List<String> files, String input, String output) {
      QuickSort quickSort = new QuickSort();
      files.forEach(file -> {
         FileService fileService = new FileService(Integer.parseInt(file.split(DOT_CHARACTER)[FILE_SIZE_INDEX]));
         int[] array = fileService.read(input);

         Instant start = Instant.now();
         quickSort.sort(array, 0, array.length - 1);
         Instant end = Instant.now();
         fileService.write(array, output);

         Duration elapsed = Duration.between(start, end);
         Logger.getLogger(MainUtility.class.getName()).info(fileService.getSize() + " - time elapsed: "
                 + elapsed.toMillis() + " milliseconds.");
      });
   }
}
