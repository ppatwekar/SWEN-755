import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolManager2
{

    ExecutorService executorService;
   public ThreadPoolManager2(int threadCount)
   {
       executorService = Executors.newFixedThreadPool(threadCount);
   }
   public Future<?> ExecuteTask(ProcessFileThread2 thread)
   {
       return executorService.submit(thread);

   }
}
