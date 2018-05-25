void mouseReleased(){
  switch(screen) {
  case 0:
    
    break;

  case 1:
    if(overRectC(150, 100, 200, 100)){//rec
      println("REC");
      recording = true;
    }
    else if (overRectC(150, 250, 200, 100)){//stop
      //println("STOP");
      recording = false;
    }
    break;
  }
}
boolean overRectC(int _x, int _y, int _w, int _h)  {
  return overRect(_x - _w/2,_y-_h/2,_w,_h);
}
boolean overRect(int x, int y, int w, int h)  {
  if (mouseX >= x && mouseX <= x+w && 
      mouseY >= y && mouseY <= y+h) {
    return true;
  } else {
    return false;
  }
}