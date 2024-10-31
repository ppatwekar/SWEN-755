What is the purpose of the project?
Often times, conferences have word limits on the amount of words a document can have. This project deals with counting words from multiple files and outputing their word count. 
The implementation is multi-threaded and offers a performance boost compared to a single threaded application. Performance in this case can be important because reviewers may want to reject certain papers that do not meet the word count specified. For this case, using multiple threads to process multiple files at once can let reviewers go through a large set of files faster than a single threaded application would allow. This would help reviewers send feedback within the right amount of time.
Performance metrics for this shall be noted later in this readme. 

How does the project work?
The user gets prompted to enter how many threads they wish to run. Upon entering this data, the project instantiates a ThreadPoolManager object. This class is found in the java.util.Concurrent package and it is an ExecutorService which executes tasks asynchronously using a pool of threads that the user specifies. The project then submits a task for each file that needs to have its words counted and the threadpoolexecuter uses the number of threads available to work on the task. It should be noted that if the user inputs "1" for the number of threads to be used, and there are 'n' files for which we need to count words ('n' tasks), the threadpoolexecutor will wait until it has finished processing one task/file before moving on to the next one. However, in the case of multiple threads (input > 1), multiple files will be counted asynchronously. This is the nature of multithreading. 

The processing times for the text files vary based on the hardware the project is deployed to. Below are some interesting metrics found through testing:

Number of files to process: 40, file size: 5.2 MB

2.5 GHz Dual-Core Intel Core i5
10 threads: 5.345s
1 thread: 40.653s

Apple M1
10 threads: 1.537s
1 thread: 2.565s

It should be noted that along with the hardware, these metrics can vary based on the number of threads and processes currently running on the operating system. 



