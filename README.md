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

About the directories:  
***Model directory*** which contains the classes that are related to the model. *FlightGearControllerModel* is the facade model class, which implements the *IFlightGearContollerModel* interface.  
The main responsibility of this class is to connect with the flight gear and send the data corresponding the user action at every time interval.  
***ViewModel directory*** which contains a view model for every user control, for example - the *NetworkingViewModel* and the *RudderViewModel*. The purpose of the view models is to decorate the behavior of the model to the needs of the view.  
***View directory*** which contains the .xml and the kotlin code behind for the view, which contains the main activity and the joystick.  

Installation and Running instructions
----------------------
The application is written in **Kotlin** and **XML** using *android studio*.  
To run the app, clone this repository into your *android studio*, connect a
physical device or use an emulator, press 'run' and the app should run on your device.

Open *flightgear* on your pc, and enter the *ip* and *port* that the app is using 
into the corresponding fields in the app.
In flightgear start the plane and click *Cessna C172P -> Autostart*. The plane engines should start.
Press *connect* in the app and now you can control the plane.


Demonstration Video
----------------
https://youtu.be/pHdEVfeBaUk

Uml Diagram
----------------
![Screenshot](uml.png)  
  




