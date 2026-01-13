package headFirst.decorator.starbuzz.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) {
        int c;
        try {
            InputStream inputStream = new LowerCaseInputStream(new BufferedInputStream(
                    new FileInputStream(Test.class.getClassLoader().getResource("1.txt").getFile())));
            while ((c = inputStream.read()) >= 0) {
                System.out.print((char) c);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
