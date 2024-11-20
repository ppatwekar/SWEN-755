SWEN-755 Homework 4 Secure Session Management : 


Prathamesh Mahendra Patwekar (pp7138@rit.edu)
Stacy Skalicky (sms7705@g.rit.edu)
Manikantan Lakshmanan Eakiri (me2083@rit.edu)

Domain: Inventory Management for Online Book Seller
Additional Documentation:


What this does:
1 Task: Retrieve the book inventory (price and title)

The user authorized to perform the retrieval is the admin.

The user not authorized to perform the retrieval is the customer.

One user is not authenticated demonstrated by the following:
Random user does not exist in the database 
Shown by: invalid login credentials, manipulate the session id / hack in an previously existing id



Class Diagram:
homework4/documents/classdiagram.png

Power Point Slides:
homework4/documents/securesessionmanagement.pdf 





Running the Project
Make sure you have Java 17+ installed on your local system to be able to run this project. This can be configured into Intellij to run both projects. 

Step 1.
Unzip the package into a new directory
Using a integrated development environment (Intellij) was used by team.
Click on Main folder and hit the play/run build button

OR 
Open and run in a terminal or intellij from the following folder: homework4\backend\BookService
Open a mac/windows terminal
MAC:
./gradlew bootRun 
WINDOWS
.\gradlew bootRun


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
Login will be permitted


or

4c.
Type in username: admin_user
Type in password: secureAdminPass123
Result:
Login will be permitted


Step 5. Attempt to click to click "Get Books"
5a. As customer a pop up indicating unauthorized action
5b. As administrator a list of book names associated with a price will appear in the blank text box below the get books button


Step 6. Please note, whether Customer or Administrator the session will log out after 1 minute and the user will be informed on the login page

