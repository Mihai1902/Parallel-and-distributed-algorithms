package com.ace.ucv.util;

import com.ace.ucv.service.FileService;
import com.ace.ucv.service.QuickSortMultiThreading;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class MainUtility {

   public static final String DOT_CHARACTER = "\\.";

   public static final int FILE_SIZE_INDEX = 0;

   public static void runApplication(List<String> files, String input, String output) {
      files.forEach(file -> {
         Instant start = Instant.now();
         FileService fileService = new FileService(Integer.parseInt(file.split(DOT_CHARACTER)[FILE_SIZE_INDEX]));
         int[] array = fileService.read(input);

         ForkJoinPool pool = ForkJoinPool.commonPool();
         pool.invoke(new QuickSortMultiThreading(0, array.length - 1, array));

         fileService.write(array, output);

         Instant end = Instant.now();

         Duration elapsed = Duration.between(start, end);
         Logger.getLogger(MainUtility.class.getName()).info(fileService.getSize() + " - time elapsed: "
                 + elapsed.toMillis() + " milliseconds.");
      });
   }
}
