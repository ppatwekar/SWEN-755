import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ProcessFileThread extends Thread
{
    Path path = null;

    ThreadPoolManager threadPoolManager = null;

    public ProcessFileThread(ThreadPoolManager threadPoolManager)
    {
        this.threadPoolManager = threadPoolManager;
    }

    public void run() {
        while (true)
        {
            if (path != null)
            {
                //System.out.println(path);
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
    public void setPath(Path inPath)
    {
        path = inPath;
    }

    public void Open(Path path) throws IOException {

        String filepath = path.toAbsolutePath().toString();
        //System.out.println(path.toAbsolutePath());
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
        System.out.println(this.getName() + "\nWord Count: " + wordCount + "\nFor Path:" + path.toAbsolutePath().toString());

        br.close();
        setPath(null);
        threadPoolManager.pushBackToStack(this);
    }
}
