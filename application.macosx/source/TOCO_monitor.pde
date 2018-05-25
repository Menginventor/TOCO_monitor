
import processing.serial.*;
PrintWriter output;
Serial Serial_port;  // Create object from Serial class
PImage img1;
int screen = 0;
PFont medium;
String connecting = "disconnected";
boolean first_packet = true;
int data_rx_timer = 0;
void setup() {
  size(1280, 800);
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
void draw() {
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

void serialEvent(Serial p) { 
  data_rx_timer = millis();
  if (first_packet) {
    first_packet = false;
    
    return;
  }
  println(p.readString());
} 
String [] old_port_list = Serial.list();