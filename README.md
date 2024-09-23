SWEN-755 Homework 1

Prathamesh Mahendra Patwekar (pp7138@rit.edu)
Stacy Skalicky (sms7705@g.rit.edu)
Manikantan Lakshmanan Eakiri (me2083@rit.edu)


Running the Project
Make sure you have Java 17+ installed on your local system to be able to run this project. This can be configured into Intellij to run both projects. 

Step 0)
Unzip the package into a new directory
Using a integrated development environment (Intellij) was used by team.
Open from the LightBulbController folder level in one instance.
Open from the LightBulbSystem folder level in a different instance .

Step 1) Run the LightBulbController service using main.java
Step 2) Run the LightBulbSystem service using main.java 

Step 3) Observe output from each one as explained below
Step 4) Type hearbeat into LighbulbSystem console to begin random 
hearbeat failiure 
Step 5) Restart at step 1 - 3 
Step 6) Type sensor into LighbulbSystem which will cause an exeception to 
randomly occur at percentage and kill the lightbulb.

Additional command below simulate movement/lack of movement in the office.

Also see video for assistance:
https://drive.google.com/file/d/12aj9i1tNhlGH9wkeoasDTVsAxdl6CXG4/view?usp=drivesdk


Initial Setup:
Main: Waiting to connect to the Lightbulb System... → The system is preparing to connect to the lightbulb service.
Main: Connected to the Lightbulb System! → The connection is successfully established.
Various threads are started:
portOut thread handles outgoing data.
portInThread manages incoming data.
sensorReceiverLightbulbSenderThread processes sensor data and communicates with the lightbulb system.
HeartBeatReceiver Thread monitors heartbeat signals from the lightbulb system and reports to fault monitor if necessary.
Sensor and Light Control Process:
The PortDataInController repeatedly sends data to the SensorReceiverLightbulbSender to process it. The SensorReceiverLightbulbSender contains the logic to switch the LightBulb on/off.

The SensorReceiverLightbulbSender processes sensor data (shown as random decimal values), and based on the currentCount value, it sends instructions to turn the lightbulb ON or OFF:
Sent LightBulb OFF to LightControllerService means the lightbulb is turned off.
Sent LightBulb ON to LightControllerService means the lightbulb is turned on.


Heartbeat System:
The HeartBeatReceiver in the LightBulbController listens for heartbeat signals from the LightBulb system (Received 'beat' from LightBulb System), ensuring the system is still active and communicating properly.
Light State Changes:
The currentCount is an internal counter that affects whether the lightbulb is turned on or off:
Negative or low values (currentCount=-1, -2, etc.) result in the bulb being turned OFF.
Higher values (currentCount=4, 5, 6, etc.) result in the bulb being turned ON.

Lighbulb System Outlut:
The output from your LightBulbSystem file shows the sequence of interactions between several system components, particularly focusing on the LightBulb Controller, sensor-generated data, and the status of the lightbulb (ON/OFF). Here’s a breakdown of the key events happening in the output:
Connection Established:
"Connected to the LightBulb Controller!" indicates that the system successfully established communication with the controller.
Threads Started:
"Main: Starting data out thread" and "Main: Starting AsyncConcreteSystemComponents.Sensor" refer to the initiation of separate threads for handling outbound data and sensor readings.
Sensor Data:
Lines like "Sensor: Generated 0.26060553847040024" indicate that the sensor in the system is continuously generating random values (between 0 and 1). These readings are sent to the LightControllerService for processing, as indicated by lines like "Sent Sensor 0.26060553847040024 to LightControllerService."

Lightbulb State:
The lightbulb's state (ON/OFF) is frequently received and processed. For instance:
"SinglePortDataIn: Received LightBulb OFF" shows the system received a signal indicating the lightbulb is off.
"PortDataInController: ReceivedLightBulb OFF" indicates that this data was passed to the controller.
"IFLightBulb OFF" and "PROCESS INPUT: LightBulb OFF" indicate that the system confirmed the lightbulb is OFF and has processed that input.
Similar messages for the lightbulb turning ON occur later, such as "SinglePortDataIn: Received LightBulb ON."
Heartbeat Mechanism:
The system periodically sends a "heartbeat" to monitor its connection to the LightControllerService. Lines like "Sent beat to LightControllerService" show this process, ensuring the system is still connected and functioning.
Light Sensor Fault and Detection:
A line saying "Light Sensor Fault and Detection Style" indicates the system monitors different types of faults such as flickering or random issues in the lightbulb sensor.
Image Data:
The line "BufferedImage@490ab905..." provides information about the graphical representation being handled in the system (possibly a visual representation of the light or its status). It contains details about image dimensions, color models, and raster data.
Key Concepts:
Sensor Data: Continuously generated values, sent to the controller to determine the lightbulb's behavior.
LightBulb Status: The system receives feedback whether the lightbulb is ON or OFF and processes it.
Heartbeat: The system sends regular signals to ensure it's functioning and in sync with the LightControllerService.
Fault Detection: It monitors for potential issues in the sensor or lightbulb performance.


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