import java.util.concurrent.*;

public class ThreadPoolManager2
{

    ThreadPoolExecutor executorService;
   public ThreadPoolManager2(int threadCount)
   {
       executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
   }
   public Future<?> ExecuteTask(ProcessFileThread2 thread) throws RejectedExecutionException
   {
       return executorService.submit(thread);

   }
   public int getActiveCount()
   {
       return executorService.getActiveCount();
   }
}

