
import processing.serial.*;

Serial myPort;  // Create object from Serial class
PImage img1;
int screen = 0;
PFont medium;
String connecting = "disconnected";
void setup() {
  size(1280, 800);

  img1 = loadImage("img1.jpg");
  img1.resize(width, img1.height*width/height);
  medium = createFont("HelveticaNeue Medium.ttf", 24);
  String os=System.getProperty("os.name");
  println("os");
}
void draw() {
  switch(screen) {
  case 0:
    homepage_draw();
    break;
  }
}

void serialEvent(Serial p) { 
  println("Serial event");
  //inString = p.readString();
} 
String [] old_port_list = Serial.list();
void scan_port() {
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