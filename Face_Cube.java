/* Swing library */
import java.awt.*;
import java.awt.geom.*;

public class Face{

   Color faceColour; // it is the colour of the individual face 
   boolean isfilled; // will give option, weather fill colour or not?
   double[][] vertices; // there are four coordinates for each face in a cube

   public Face(double[][] p, Color q, boolean r){ 
      
      /* construction of face */   
      faceColour = q;
      isfilled = r;
      vertices = p;
   }
   
   public void drawingFace(Graphics2D gd){
   
      // this will connect all the vertices in order because we want the squares to be connected with edges 
      // here f represent face
      Path2D.Double f = new Path2D.Double();
      f.moveTo(vertices[0][0], vertices[0][1]);
      
      for(int k = 1; k < vertices.length; k++){
         f.lineTo(vertices[k][0], vertices[k][1]);
         //for drawing a line.
      }   
      
      f.closePath();
      
      // by corresponding button, if user wants to fill color he/she can do
      if(isfilled){
         gd.setColor(faceColour);// Set colour to gd object
         gd.fill(f);//fill that colour into face object
      }
      
      gd.draw(f);  //it will draw the face object as the specifications given to gd
         
   } // end drawingFace
}
