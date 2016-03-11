
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import mrdshinse.csv.reader.DefaultCsvReader;

/**
 *
 * @author mrdShinse
 */
public class Main {

    public static void main(String[] args) {
        File file = createFile(new String[]{"\", \"b\"　,\"\"c\", d"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
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
