
import java.awt.desktop.OpenFilesEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws IOException {
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
        ThreadPoolManager2 threadPoolManager2 = new ThreadPoolManager2(threadCount);
        String filePathLocation = "src\\files";
        Stream<Path> paths = Files.walk(Path.of(filePathLocation));
        paths.filter(Files::isRegularFile).forEach(filePaths::add);
        System.out.println("Number of Documents to Process: " + filePaths.size());
        Instant start = Instant.now();

        Path currentPath = null;
        ProcessFileThread2 thread = null;
        int i = 1;
        int currentCount = 0;
        while (!filePaths.isEmpty())
        {
            try
            {
                thread = new ProcessFileThread2();
                if(i > threadCount)
                {
                    i = 1;
                }
                thread.setName("ProcessFile Thread:" + i);
                i = i + 1;
                currentPath = filePaths.pop();
                thread.setPath(currentPath);
                Future<?> newFuture = threadPoolManager2.ExecuteTask(thread);
                futures.add(newFuture);

            }
            catch(EmptyStackException e)
            {
                System.out.println("Empty StackException");
                System.exit(1);
            }
            catch(RejectedExecutionException r)
            {
                System.out.println("RejectedExecutionException");
                System.exit(1);
            }
            if(currentCount != threadPoolManager2.getActiveCount())
            {
                System.out.println("The current active thread count is: " + threadPoolManager2.getActiveCount());
                currentCount = threadPoolManager2.getActiveCount();
            }
        }

        boolean complete = false;
        while (!complete)
        {
            complete = true;
            for (Future<?> future : futures)
            {
                //System.out.println("Future is currently: " + future.state().toString());
                if (!future.isDone())
                {
                    complete = false;
                    break;
                }
            }
            if(currentCount != threadPoolManager2.getActiveCount())
            {
                System.out.println("The current active thread count is: " + threadPoolManager2.getActiveCount());
                currentCount = threadPoolManager2.getActiveCount();
            }
        }
        System.out.println("All threads free");
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("Elapsed time: " + timeElapsed.toMillis() + " ms");
        System.exit(0);
    }
}