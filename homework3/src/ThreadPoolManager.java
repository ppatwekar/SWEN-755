import java.util.*;

public class ThreadPoolManager
{
    Deque<ProcessFileThread> threadAvailableStack = new ArrayDeque<>();
    int threadCount = 0;
   public ThreadPoolManager(int threadCount)
   {
       this.threadCount = threadCount;
       for (int i = 0; i < threadCount; i++)
       {
           ProcessFileThread aThread = new ProcessFileThread(this);
           aThread.setName("Thread " + (i + 1));
           threadAvailableStack.push(aThread);
           System.out.println("Creating: " + aThread.getName());
       }
   }
    public ProcessFileThread getAvailableThread() throws EmptyStackException
    {
        ProcessFileThread popThread = null;
        try
        {
            popThread = threadAvailableStack.pop();
        }
        catch(NoSuchElementException e)
        {
            //System.out.println("All " + threadCount + " threads are in use");
        }
        return popThread;
    }
    public void pushBackToStack(ProcessFileThread threadCompleted)
    {
        threadAvailableStack.push(threadCompleted);
    }
    public boolean allThreadsFree()
    {
        return threadAvailableStack.size() == threadCount;
    }
}
