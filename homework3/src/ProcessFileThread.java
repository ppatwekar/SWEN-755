import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ProcessFileThread extends Thread
{
    static Path path = null;

    public void run() {
        while (true)
        {
            if (path != null)
            {

                System.out.println(path);
                try {
                    Open(path);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void setPath(Path inPath)
    {
        path = inPath;
    }
    public static boolean isRunning()
    {
        return (path != null);
    }

    public static void Open(Path path) throws IOException {

        String filepath = path.toAbsolutePath().toString();
        System.out.println(path.toAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line = br.readLine();
        int wordCount = 0;
        while(line != null)
        {
            //System.out.println(line);
            String[] words = line.split("\\s+"); // Split by whitespace
            wordCount += words.length;
            line = br.readLine();

        }
        System.out.println("Word Count: " + wordCount);
        br.close();
        setPath(null);
    }
}
