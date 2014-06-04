JavaOTGembed
============

Java application running on OTG board


This is embedded application to start timer and send output to Raspberry Pi.

We have to create a jar file which will be executed on IOIO OTG board

Connect board on USB port of computer and later it will be connected to Pi Board.



Download zip file from https://github.com/ytai/ioio/wiki/Downloads

I am using APP-IOIO0503.zip which is 5.00/5.03 version

Import projects in eclipse then open HelloIOIOConsole.java file in HelloIOIOConsole 
project. Then replace this file the same file present in repository.

OR

Import projects from Repository, Add Library 'IOIOLib' library then try to run on Pi Board.


INITIAL SETUP FOR RUNNING APPLICATION:
--------------
To run application we have to connect IOIO otg board on usb port of raspberry Pi and 
enable linux to detect IOIO on virtual serial port.

1. Make sure the host mode switch on the board is in the auto ("A") position. 
This will enable the IOIO to act as a USB device (whereas the other mode forces 
it to host mode).

2. Connect the IOIO to a PC with a micro-B USB cable. It should power up at this point 
(red pwr LED is constantly on).

3. Download a udev rules file from the Downloads page, and copy it to your rules 
directory using command 
     (sudo cp 50-ioio.rules /etc/udev/rules.d). 

4. Then restart udev (sudo restart udev or sudo /etc/init.d/udev restart). 

This is a one-time process.


TO RUN APPLICATION:
-------------------

Now we can run our jar application on OTG board by using command:

     (java -jar HelloIOIO.jar)

Or search for particular serial port then run    

    (java -Dioio.SerialPorts=COM13 -jar HelloIOIO.jar)
     
     
