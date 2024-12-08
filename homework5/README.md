SWEN-755 Homework 5 Secure Session Management Testing: 


Prathamesh Mahendra Patwekar (pp7138@rit.edu)
Stacy Skalicky (sms7705@g.rit.edu)
Manikantan Lakshmanan Eakiri (me2083@rit.edu)

Domain: Inventory Management for Online Book Seller


Additional Documentation:

What this does:
1 Task: Retrieve the book inventory (price and title)

The user authorized to perform the retrieval is the admin.

The user not authorized to perform the retrieval is the customer.

Both admin type and customer type roles can now logout 

One user is not authenticated demonstrated by the following:
Random user does not exist in the database 
Shown by: invalid login credentials, manipulate the session id / hack in an previously existing id


This should be run on Windows 10/11 PC/Laptop:


Class Diagram:
homework5/documents/hw5_classdiagram.png

Power Point Slides:
homework5/documents/Homework5_Slides.pdf 


Recreating Breaker 1:
Able to act with privileges that should not be allowed in the same browser instance. 
Steps:
1. Start the web application of homework 4 using the readme in HW4 and login as customer
2. Now open in a new tab in the same browser and login as administrator
3. Attempt to issue click getbooks() command expected result: success  actual result: success
4. Switch back to the browser tab with your customer logged in instance
5. Attempt to issue click getbooks() command expected result: fail, actual result : success

What we did for Breaker 1: We chose not to figure out how to resolve/fix this.

Recreating Breaker 2:
Able to re-log in after closing the browser and re-opening a fresh one with just a copied login session details
Steps:
1. Start the web application of homework 4 using the readme in HW4 and login as customer or administrator
2. Copy the http://localhost:8080/books/?sessionId=<UUID> from browser
3. Close the browser 
4. Reopen and clear any existing information and then paste your copied session url details into the space the browser provides to enter a url
5. Expectation: should no longer be viable information
   Actual: will be viable because no logout or action to clear the session information occurred

What we did for Breaker 2: 
In the homework 5 instance we added logout functionality, still could close without logging out, but now the user and intentionally logout with new button and session id become invalid/defunct. 


Recreating Breaker 3:
Issue with login requiring double issue / input of credentials 
1. Start the web application of homework 4 using the readme in HW4 and login as customer or administrator (intermittently)
2. Expectation: On issuance of valid details, user is transitioned to a new web page for inventory details 
   Actual: Login attempter sometimes has to enter twice. 

What we did for Breaker 3:
In the homework 5 instance we added a way to prevent default behavior that was not allowing all the submission details to be handled in the right order before being utilized to login.


Recreating Breaker 4: 
1. Having button/access that isn't permissible visible for roles that cannot use it. 

What we did for Breaker 4:
What we did for Breaker 1: We chose not to figure out how to resolve/fix this.


Recreating Breaker 5/6: Able to see obscured password in submitted url and inspection window

What we did for breaker 5/6: Added Cypress front end tests to detect this and fail as well as resolved and obscured it in HW5.



Running the Project

Make sure you have Java 17+ installed on your local system to be able to run this project. This can be configured into Intellij to run both projects. 

Starting with HW4:
Step 1.
Unzip the package into a new directory
Using a integrated development environment (Intellij) was used by team.

Install cypress and node.js for your system and add node js to your environment 
node -v
This should print the Node.js version. 
If not, install Node.js from https://nodejs.org/en. 
Check npm version:

npm -v
This should print the npm version.

Update your environment variables for your user account on windows:
C:\Program Files\nodejs\


Cypress Steps:




See information above to repeat breakers. 



Step 2: For testing to work for fail/pass using cypress situation you must have website running. 
Click on Main folder and hit the play/run build button
OR 
Open and run in a terminal or intellij from the following folder: homework4\backend\BookService
Open a mac/windows terminal
MAC:
./gradlew bootRun 
WINDOWS
.\gradlew bootRun


In a New Console, run the following command to a cypress instance
Select ED2 and continue (keep defaults)
We used chrome, we said  



Step 2. 
Open Chrome Web browser
or 
Open a Brave Web Browser

Step 3. 
Type in the URL: http://localhost:8080/login/

Step 4. Login as particular user
4a.
You start as an anonymous user, a session id will be associated to you once your are authenticated
Type in any username and password : login will be denied

or 

4b.
Type in username: customer_user 
Type in password: customerPass456
Result:
Login will be permitted (Note: You may need to re-enter the credentials and click on Sign-In again. This is a minor bug we are working on)


or

4c.
Type in username: admin_user
Type in password: secureAdminPass123
Result:
Login will be permitted
(Note: You may need to re-enter the credentials and click on Sign-In again. This is a minor bug we are working on)


Step 5. Attempt to click to click "Get Books"
5a. As customer a pop up indicating unauthorized action
5b. As administrator a list of book names associated with a price will appear in the blank text box below the get books button


Step 6. Please note, whether Customer or Administrator the session will log out after 1 minute and the user will be informed on the login page


