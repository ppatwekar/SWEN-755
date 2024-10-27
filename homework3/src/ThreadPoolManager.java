import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class ThreadPoolManager extends Thread
{
    Stack<ProcessFileThread> threadInUseStack = new Stack<>();
    Stack<ProcessFileThread> threadAvailableStack = new Stack<>();

   public ThreadPoolManager(int threadCount)
   {
       threadCount = 1;
       for (int i = 0; i < threadCount; i++)
       {
           ProcessFileThread aThread = new ProcessFileThread();
           aThread.setName("ProcessFileThread " + i);
           aThread.start();
           System.out.println("Check if is running before:" + aThread.isAlive());
           threadAvailableStack.push(aThread);
           System.out.println(aThread.getName());
       }
   }

    public void run()
    {
        while (true)
        {
            try
            {
                ProcessFileThread peekThread = threadInUseStack.peek();
                if (!peekThread.isRunning())
                {
                    threadAvailableStack.push(threadInUseStack.pop());
                }
            }
            catch(EmptyStackException e)
            {
                if((threadAvailableStack.isEmpty()))
                {
                    System.out.println("Waiting for resources.");
                }
            }
            
        }
    }
    public ProcessFileThread getAvailableThread() throws EmptyStackException
    {
        ProcessFileThread tempThread;
        try
        {
            tempThread = threadAvailableStack.pop();
            threadInUseStack.push(tempThread);
            System.out.println("Getting available thread: " + tempThread.getName() + " " + threadAvailableStack.size());
        }
        catch(EmptyStackException e)
        {
            return null;
        }
        return tempThread;
    }
}
