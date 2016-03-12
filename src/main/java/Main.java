
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import mrdshinse.csv.reader.DefaultCsvReader;

/**
 *
 * @author mrdShinse
 */
public class Main {

    public static void main(String[] args) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        File file = createFile(new String[]{"a,b,c,d,e", "a,b,c,z"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr.read()) {
        }
    }

    protected static File createFile(String[] lines) {
        File file = new File("test.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ex) {
        }
        return file;
    }
}
