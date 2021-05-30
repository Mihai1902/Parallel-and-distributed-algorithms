import com.ace.ucv.util.MainUtility;
import java.util.Arrays;
import java.util.List;

public class MainApplicationSecondTest {

   public static final String INPUT_FILE_NAME = "input-test";

   public static final String OUTPUT_FILE_NAME = "output-test";

   public static void main(String[] args) {

      List<String> files = Arrays.asList("100.txt", "1000.txt", "10000.txt", "100000.txt", "1000000.txt", "10000000.txt");

      MainUtility.runApplication(files, INPUT_FILE_NAME, OUTPUT_FILE_NAME);
   }
}
