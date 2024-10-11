SWEN-755 Homework 2

Prathamesh Mahendra Patwekar (pp7138@rit.edu)
Stacy Skalicky (sms7705@g.rit.edu)
Manikantan Lakshmanan Eakiri (me2083@rit.edu)


Running the Project
Make sure you have Java 17+ installed on your local system to be able to run this project. This can be configured into Intellij to run both projects. 

Step 1.
Unzip the package into a new directory
Using a integrated development environment (Intellij) was used by team.
Open from the LightBulbController folder level in one instance.
Open from the LightBulbSystem folder level in a different instance.

Step 2. Run the LightBulbController service using main.java

Step 3. Run the first instance LightBulbSystem service using main.java
Step 3a. Type : "active" into the console

Step 4. Run the second instance LightBulbSystem service using main.java 
Step 4a. Type : "inactive" into the console

Step 5. Observe the active lightbulb send heartbeats and sensor data
Step 5a. Observe the inactive lightbulb receive on/off data from the active lightbulb

Step 6. Type sensor into LighbulbSystem which will cause an exeception to 
randomly occur at percentage and kill the active lightbulb
Step 6a. (alternative) Kill the active lightbulb gui, or type die in the active lightbulb main

Step 7. Observe the inactive lightbulb begin sending heartbeats and sensor data


Additional commands below simulate movement/lack of movement in the office.




Non-deterministic Failures and User Inputs
The LightBulbSystem can take in user input such as flicker, random, on, off, die, heartbeat, sensor. These cause the system to skew the results of the system. 
Flicker: Makes the lightbulb graphic flicker.

On: Causes the Sensor from the LightBulb system to send biased inputs to the LightBulbController which in turn will reply back to switch the Light ON.

Off: Causes the Sensor from the LightBulb system to send biased inputs to the LightBulbController which in turn will reply back to switch the Light OFF.

Heartbeat: Causes the heartbeat thread to send heartbeats infrequently, leading to simulating a non-deterministic failure, which is detected by the LightBulbController.

Sensor: Causes an exception to occur at an indeterminate time randomly in the class that calculates sensor data.

Die: Causes the LightBulbSystem’s Socket to close, thereby simulating a Socket fault, leading to a simulation non-deterministic failure, which is detected by the LightBulbController.


Note: The office light on PNG image used to simulate light activation in an office was generated by the chatGpt below.
The image was then manually edited for an off view. 
"Interaction with OpenAI’s GPT-4 via a customized image-generating assistant on ChatGPT, September 2024.”