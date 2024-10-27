
import java.awt.desktop.OpenFilesEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException
    {
        List<Path> filePaths = new ArrayList<>();
        
        int threadCount = 1;
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Provide Number of Threads to run:");
            String input = scanner.nextLine();
            System.out.println(input);
            if(Integer.parseInt(input) > threadCount) {
                System.out.println("Number of Threads exceeded");
            }
            else
            {
                threadCount = Integer.parseInt(input);
                break;
            }
        }
        System.out.println("Number of Threads: " + threadCount);
        ThreadPoolManager threadPoolManager = new ThreadPoolManager(threadCount);
        threadPoolManager.setName("ThreadPoolManager");
        threadPoolManager.start();
        System.out.println(threadPoolManager.getName());

        String filesPath = "src\\files";
        Stream<Path> paths = Files.walk(Path.of(filesPath));
        paths.filter(Files::isRegularFile).forEach(filePaths::add);
        System.out.println("Number of Documents to Process: " + filePaths.size());

        int filesParsed = 0;
        System.out.println("Total files:" + filePaths.size());
        while(filesParsed < filePaths.size())
        {
            ProcessFileThread thread = threadPoolManager.getAvailableThread();
            if (thread != null)
            {
                Path tempPath = filePaths.get(filesParsed);
                thread.setPath(tempPath);
                filesParsed = filesParsed + 1;
                System.out.println("Files parsed: " + filesParsed);
            }
        }
    }
}