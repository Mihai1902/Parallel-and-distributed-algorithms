import com.ace.ucv.util.MainUtility;
import java.util.Arrays;
import java.util.List;

public class MainApplicationFirstTest {

   public static final String INPUT_FILE_NAME = "input";

   public static final String OUTPUT_FILE_NAME = "output";

   public static void main(String[] args) {

      List<String> files = Arrays.asList("2.txt", "4.txt", "8.txt", "16.txt", "32.txt", "64.txt", "128.txt", "256.txt",
              "512.txt", "1024.txt", "2048.txt", "4096.txt", "8192.txt", "16384.txt", "32768.txt", "65536.txt",
              "131072.txt", "262144.txt", "524288.txt", "1048576.txt", "2097152.txt", "4194304.txt", "8388608.txt",
              "16777216.txt");

      MainUtility.runApplication(files, INPUT_FILE_NAME, OUTPUT_FILE_NAME);
   }
}
