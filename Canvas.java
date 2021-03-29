/* Swing library */
import javax.swing.*;
import java.awt.*;
import java.lang.Math;

public class Canvas extends JPanel{
   
   double[][] vertices; // the coordinates of the cube, will be updated constantly
   Face[] face; // representing the six faces of the cube 
   double z = 150.0; // z axis, this will indicate the length of the cube 
   double e = z/(150.0/1000.0); // distance from the eye
   boolean isRX, isRY, isRZ, isArb; // switch to tell which rotation it is currently in 
   double theta; //  degree of rotation
   double[] b; // the vector representing an arbitrary axis 
   boolean[] isSolid; // switch to tell if the cube should be in wireframe or colored 
   
   // initiate the original cube on the panel
   public Canvas(){
   
      setPreferredSize(new Dimension(650,650)); //size of canvas
		setBackground(new Color(255, 255, 204)); // light yellow 
      
      /* initialization */
      theta = 0.0;
      isRX = false; isRY = false; isRZ = false; isArb = false;
      
      isSolid = new boolean[6];
      for(int k = 0; k < isSolid.length; k++){
         isSolid[k] = false;
      }
      
      // set up the vertices 
      //Here -1 is indicating 0 value
      vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0},
                                {1.0, -1.0, 1.0}, {-1.0, -1.0, 1.0},
                                {-1.0, -1.0, -1.0}, {1.0, -1.0, -1.0}, 
                                {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}
                              };

                           
      // scaling the vertices for proper visiblity
      Matrix.scaling(vertices, z);
      
      // orienting the arbitrary axis to the z axis 
      b = new double[]{0.0,0.0,1.0};
      
   }
   
   public void paintComponent(Graphics p){

		super.paintComponent(p);  //without this background color will not set.
	
		Graphics2D gd = (Graphics2D)p; //casting, so we can use JAVA2D.
		gd.translate(getWidth()/2,getHeight()/2);
		gd.scale(1,-1);
      
      // invoke any of the rotation methods if called, based on 
      //theta, we know the cube should be rotating how much amount in which direction
      for(int k = 0; k < vertices.length; k++){
         if(isRZ)
         {
            vertices[k] = Matrix.rotationZ1(vertices[k], Math.toRadians(theta));
         }
         if(isRX)
         {
            vertices[k] = Matrix.rotationX1(vertices[k], Math.toRadians(theta));
         }
         if(isRY)
         {
            vertices[k] = Matrix.rotationY1(vertices[k], Math.toRadians(theta));
         }
         if(isArb)
         {
            vertices[k] = Matrix.rotateArb(vertices[k], b, Math.toRadians(theta));
         }
         
      }
      
      /* make a copy of the vertices after rotation, we can leave the old vertices there
       waiting for later rotation. Meanwhile, we will do perspective transform to the 
       copied vertices, just so they could be appeared on a 2D screen.*/

      double[][] vt = new double[vertices.length][];
      
      for(int i = 0; i < vertices.length; i++){
         vt[i] = Matrix.perspectiveTransformation(vertices[i], vertices[i][2], e);
      }

      /* build up each face based on the vertices each face has */ 
      Face F1, F2, F3, F4, F5, F6;
      F1 = new Face(new double[][]{vt[1], vt[0], vt[3], vt[2]}, 
                     new Color(255,0,0), isSolid[0]); // Dark red
      F2 = new Face(new double[][]{vt[6], vt[1], vt[2], vt[5]}, 
                     new Color(255,140,0), isSolid[1]); // Orange
      F3 = new Face(new double[][]{vt[7], vt[6], vt[5], vt[4]}, 
                     new Color(255,255,0), isSolid[2]); // yellow 
      F4 = new Face(new double[][]{vt[0], vt[7], vt[4], vt[3]}, 
                     new Color(0,0,255), isSolid[3]);   //  blue
      F5 = new Face(new double[][]{vt[6], vt[7], vt[0], vt[1]}, 
                     new Color(204,0,102), isSolid[4]); // dark pink
      F6 = new Face(new double[][]{vt[2], vt[3], vt[4], vt[5]}, 
                     new Color(0,255,255), isSolid[5]); // light blue
            
      face = new Face[]{F1, F2, F3, F4, F5, F6};
      
      /* This will sort the faces based on the average z value of the vertices on each face we want
       to figure out which face is the front face and which one is the back face. */
      bubbleSortFaces(face); 
      
      //after we sort out the order of the faces. we fill from face  with low average z to high average of z
      for(int k = 0; k < face.length; k++){
         face[k].drawingFace(gd);
      }
          
	 }

    // implementing a bubble sort algorithm to sort out faces based on their average z values
   
    public void bubbleSortFaces(Face[] faces){
      
       double[] faceZ = new double[6];       
       
       for(int i = 0; i < faceZ.length; i++){
         faceZ[i] = ((faces[i].vertices[0][2])+(faces[i].vertices[1][2])+
                        (faces[i].vertices[2][2])+(faces[i].vertices[3][2]))/4;
       }
       
       int n = faceZ.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (faceZ[j] > faceZ[j+1])
                {
                    // swap temp and faceZave[i]
                    double temp = faceZ[j];
                    faceZ[j] = faceZ[j+1];
                    faceZ[j+1] = temp;
                    
                    // swap tmpFace and faces[j] in the meantime 
                    Face tmpFace = faces[j];
                    faces[j] = faces[j+1];
                    faces[j+1] = tmpFace;
                }

    }
   
}