# FlightGear Controller Android Application
About the application
---------------------
This is a flight controller android application. The application works as following: 

The user opens the app, and inserts the *ip* and *port* of the machine running the flightgear.
The user presses *connect* - and now he can control the airplane in the flightgear running on his computer.

Moving the joystick in the y axis will change the elevator, and moving the joystick in the x axis will change the aileron left and right.
The user can also adjust the rudder by moving the bottom slider, and the throttle by moving the left slider
  
![Screenshot](captures/window.png) 
  
  
About the Project Structure
---------------------------
The project implements the **MVVM architectural pattern**. Accordingly, the project files are splitted into three folders - **Model**, **View**, and **ViewModel**.  
The responsility **Model** is to analyze the given flight data .csv file, communicate with the *FlightGear*, and do all of the calculations like finding linear regression between two flight data properties.  
The **View** is responsibe on showing the flight data to the user in an interactive way - data like the graphs and the anomaly. The View is also responsible on getting commands from the user - commands like pause the rendering and increase the rendering speed.  
The **ViewModel** is responsible on connecting between the **Model** and the **View**, while making an *abstraction* of the **Model** for the **View**.  
  
About the directories:  
***Model directory*** which contains the class that are related to the model. *FlightGearModel* is the facade model class, which implements the *IFlightGearModel* interface.  
This class contains an instance of an object that implements the *IAsyncFGClient* interface, and uses that object to communicate with the FlightGear asynchronously.  
In this way, the *IFlightGearModel* and *IAsyncFGClient* interfaces are built together in the *Strategy design pattern*, which allows you to combine a *FlightGearModel* with every *AsyncFGClient* which implements the *IAsyncFGClient* interface. In the project, we implemented the *AsyncTcpFGClient*, which helps the *FlightGearModel* to communicate with the *FlightGear* in TCP protocol.  
***ViewModel directory*** which contains a ViewModel for every user control, for example - the *PlayBackViewModel* is a ViewModel for the playback scrollbar, which gets from the 
model the data which is relevant to the scrollbar (like the flight video speed), and decorates the relevant operations of the model for the scrollbar, like *Render* which starts 
the rendering of the flight data. There is a data binding between every ViewModel to its user control.  
***View directory*** which contains the .xaml file and the C# code behind for every user controls and window. The directory contains 2 windows - *Opening window*, and *Main window*, and all of the user controls which exist in those windows.  
**Plugins directory** which contains two anomaly algorithm plugins.  

Installation and Running instructions
----------------------
The application is written in **Kotlin** and **XML** using *android studio*.  
To run the app, clone this repository into your *android studio*, connect a
physical device or use an emulator, press 'run' and the app should run on your device.

Open *flightgear* on your pc, and enter the *ip* and *port* that the app is using 
into the corresponding fields in the app.
Press *connect* and now you can control the plane.


Demonstration Video
----------------
https://youtu.be/2U8jjl2nS5c

Uml Diagram
----------------
![Screenshot](uml.png)  
  




