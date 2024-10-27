
import java.awt.desktop.OpenFilesEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException {
        Stack<Path> filePaths = new Stack<>();
        List<Future<?>> futures = new ArrayList<>();

        int threadCount = 10;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Provide Number of Threads to run:");
            String input = scanner.nextLine();
            System.out.println(input);
            if (Integer.parseInt(input) > threadCount) {
                System.out.println("Number of Threads exceeded");
            } else {
                threadCount = Integer.parseInt(input);
                break;
            }
        }
        System.out.println("Number of Threads: " + threadCount);
        //ThreadPoolManager threadPoolManager = new ThreadPoolManager(threadCount);
        ThreadPoolManager2 threadPoolManager2 = new ThreadPoolManager2(threadCount);
        String filePathLocation = "src\\files";
        Stream<Path> paths = Files.walk(Path.of(filePathLocation));
        paths.filter(Files::isRegularFile).forEach(filePaths::add);
        System.out.println("Number of Documents to Process: " + filePaths.size());
        Instant start = Instant.now();

        while (!filePaths.isEmpty())
        {
            ProcessFileThread2 thread = new ProcessFileThread2();
            thread.setName("Process File Thread:" + Instant.now());
            thread.setPath(filePaths.pop());
            futures.add(threadPoolManager2.ExecuteTask(thread));
        }

        boolean complete = false;
        while (!complete)
        {
            complete = true;
            for (Future<?> future : futures)
            {
                if (!future.isDone())
                {
                    complete = false;
                    break;
                }
            }
        }
        System.out.println("All threads free");
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("Elapsed time: " + timeElapsed.toMillis() + " ms");
        System.exit(0);
    }
}