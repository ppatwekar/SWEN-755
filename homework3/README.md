What is the purpose of the project?
Often times, conferences have word limits on the amount of words a document can have. This project deals with counting words from multiple files and outputing their word count. 
The implementation is multi-threaded and offers a performance boost compared to a single threaded application. Performance in this case can be important because reviewers may want to reject certain papers that do not meet the word count specified. For this case, using multiple threads to process multiple files at once can let reviewers go through a large set of files faster than a single threaded application would allow. This would help reviewers send feedback within the right amount of time.
Performance metrics for this shall be noted later in this readme. 

How does the project works?

