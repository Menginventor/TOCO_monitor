import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TOCO_monitor extends PApplet {



PrintWriter output;
Serial Serial_port;  // Create object from Serial class
PImage img1;
int screen = 0;
PFont medium;
String connecting = "disconnected";
boolean first_packet = true;
int data_rx_timer = 0;
public void setup() {
  
  //fullScreen();
  //frame.setResizable(true);

  img1 = loadImage("img1.jpg");
  img1.resize(width, img1.height*width/height);
  medium = createFont("HelveticaNeue Medium.ttf", 24);
  String os=System.getProperty("os.name");
  println(os);
  output = createWriter("testing.txt"); 
  output.println("Operating_sys : "+os);
  output.flush(); // Writes the remaining data to the file
  output.close(); // Finishes the file
}
public void draw() {
  if (connecting.equals("connected") && millis() - data_rx_timer>2000) {
     Serial_port.stop();
     connecting = "disconnected";
     println("disconnected");
  }
  switch(screen) {
  case 0:
    homepage_draw();
    break;
  }
}

public void serialEvent(Serial p) { 
  data_rx_timer = millis();
  if (first_packet) {
    first_packet = false;
    
    return;
  }
  println(p.readString());
} 
String [] old_port_list = Serial.list();
public void homepage_draw() {
  image(img1, 0, 0);
  float dialog_width = 700;
  float dialog_height = 500;
  rectMode(CENTER);
  fill(0xffD8D8D6, 200);
  noStroke();
  rect(width/2, height/2, dialog_width, dialog_height, 50);

  textFont(medium);
  fill(0xff4D4D4C);
  textSize(50);
  textAlign(CENTER, CENTER);
  text("Welcome to UTC Monitor", width/2, height/2-dialog_height/2+50);
  textSize(32);
  fill(0xff4D4D4C, abs(millis()%2000-1000)*255/750);
  if (connecting.equals("disconnected")) {

    text("Please connect your device", width/2, height/2);
    scan_port();
  }
  else  if (connecting.equals("connected")) {

    text("Connecting...", width/2, height/2);
 
  }
}
public void scan_port() {
  String [] new_port_list = Serial.list();
  if (old_port_list.length != new_port_list.length) {
    if (old_port_list.length < new_port_list.length)
      println("Plug device");
    if (connecting.equals("disconnected")) {
      for (int i = 0; i<new_port_list.length; i++ ) {
        boolean new_port = true;
        for (int j = 0; j<old_port_list.length; j++ ) {
          if (new_port_list[i].equals(old_port_list[j])  == true) {
            new_port = false;
            break;
          }
        }
        if (new_port) {
          if (!new_port_list[i].substring(0, 8).equals("/dev/tty")) {//for mac osx
            println(new_port_list[i]) ;
            try {
              Serial_port = new Serial(this, new_port_list[i], 9600);
              Serial_port.bufferUntil('\n');
              first_packet = true;
            }
            catch(Exception e) {
            }
            connecting = "connected";
            data_rx_timer = millis();
            break;
          }
        }
      }
    }
  }
  old_port_list = Serial.list();
}

  public void settings() {  size(1280, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TOCO_monitor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
