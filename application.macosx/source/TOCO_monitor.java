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




Serial myPort;  // Create object from Serial class
PImage img1;
int screen = 0;
PFont medium;
String connecting = "disconnected";
public void setup() {
  //size(1280, 680);
  frame.setLocation(0, 0);
  
  //frame.setResizable(true);

  img1 = loadImage("img1.jpg");
  img1.resize(width, img1.height*width/height);
  medium = createFont("HelveticaNeue Medium.ttf", 24);
  String os=System.getProperty("os.name");
  println(os);
}
public void draw() {
  switch(screen) {
  case 0:
    homepage_draw();
    break;
  }
}

public void serialEvent(Serial p) { 
  println("Serial event");
  //inString = p.readString();
} 
String [] old_port_list = Serial.list();
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
          println(new_port_list[i]) ;
        }
      }
    }
  }
  old_port_list = Serial.list();
}
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
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TOCO_monitor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
