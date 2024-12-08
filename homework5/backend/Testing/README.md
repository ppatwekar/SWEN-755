SWEN-755 Homework 5 Secure Session Management Testing: 

Install cypress and node.js for your system and add node js to your environment 
node -v
This should print the Node.js version. 
If not, install Node.js from https://nodejs.org/en. 
Check npm version:

npm -v
This should print the npm version.

Update your environment variables for your user account on windows:
(Please note path is based on windows environment, this path may be different, but needs to be in your local path way)
Go to System Properties
Edit Environment Variables
Make sure the Path for user variables for your account includes the nodejs location (how to get this is below)

Go to command prompt and query:
In command line type: where node

Example output:
C:\Program Files\nodejs\



Prathamesh Mahendra Patwekar (pp7138@rit.edu)
Stacy Skalicky (sms7705@g.rit.edu)
Manikantan Lakshmanan Eakiri (me2083@rit.edu)

Start by running BookService from command line:


Running the Project
Make sure you have Java 17+ installed on your local system to be able to run this project. This can be configured into Intellij to run both projects. 

Open and run in a terminal or intellij from the following folder: homework5\backend\BookService
Open a mac/windows terminal
MAC:
./gradlew bootRun 
WINDOWS
.\gradlew bootRun


Open a command line at this location:
SWEN-755\homework5\backend\Testing


Setting up Cypress:

npx cypress open
------------------
First time:
Select y 
Allow access
------------------- 
Select E2E Testing 
Hit Continue (Keep all default js created)
Select Chrome
Select : Start E2E Testing in Chrome 
Select : Create new spec and allow default spec creation (make no changes)
Select : Allow new spec to run
Keep open the browser with the test
Go to a your window file exploere and copy over hw5.cy.js to
SWEN-755\homework5\backend\Testing\cypress\e2e
Return back to chrome
Select specs from the left toolbar (looks like a cmd window / console window, 2nd item from the top)
Now select hw5.cy.js
This should run and pass 

Some example expected output:

assertexpected 4a647de78d0d1b741fd179453801bf1b1fa9909a4f0bef0a7c64c946870a0526 to not equal admin_user

assertexpected b721718f9eaa78b78591621b1dbf98e795f24f4b2b1ce694b8c42ae15981a416 to not equal secureAdminPass123



