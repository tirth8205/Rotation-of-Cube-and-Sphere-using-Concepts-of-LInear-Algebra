import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

// main method logic of the shaded sphere
public class Canvas extends JPanel{

   BufferedImage sphereImage; // buffered image where we draw the sphere
   int w = 620; // w=width of the canvas
   int h = 620; // h=height of the canvas 
   double Ka = 0.03; // Ka is the ambient coefficient 
   
   double intensityMax = 250.0; // maximum intensity (visible)
   double intensityMin = 40.0; // minimum intensity
   double radius = 150.0; // it is the radius of the sphere
   
   double[] pL; // position of the light source
   double lX, lY, lZ; //These are individual components of the light source 
      
   boolean isRed, isGreen, isBlue; // switch for changing colors 
   boolean isSpecular; // where or not put in specular mode 

   public Canvas(){
      setPreferredSize(new Dimension(620,620));
		sphereImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      setBlackScene(); // set the background colour to black
      
      /* initialising all the fields */
      isRed = false;
      isGreen = false;
      isBlue = false;
      isSpecular = false;
      lX = 0.0;
      lY = 100.0; 
      lZ = 440.0;
   } // end SpherePanel
   
   // getting the ambient light, which is a constant 
   public double getIa(){
      return Ka*intensityMax;
   }
   
   // get the diffuse light 
   // Id = Ka*intensityMax*(n*l)

   public double getId(double[] N, double[] L){
      double Range = intensityMax - intensityMin;
      double Id = Range*dot_product(unit_vector(N),unit_vector(L)) + intensityMin;
      return Id; 
   } // end getId
   
   // vector subtraction
   // v = a - b
   public double[] vSubtraction(double[] a, double[] b){
      double[] v = new double[3];
      v[0] = a[0] - b[0];
      v[1] = a[1] - b[1];
      v[2] = a[2] - b[2];
      return v;
   }
   
   
   // scale it by a factor of a 
   // w = v * a
   public double[] scaling(double[] V, double a){
      double[] w = new double[3];
      w[0] = V[0]*a;
      w[1] = V[1]*a;
      w[2] = V[2]*a;
      return w;
   }
   
   // get the specular light
   public double getIs(double[] N, double[] L){
      
      double[] n = unit_vector(N); // normalize the normal vector to the surface
      double[] l = unit_vector(L); // normalize the light source vector 
      
      double c = 2*(dot_product(n,l));
      
      double[] R = vSubtraction(scaling(n, c), l); // reflection vector
      double[] r = unit_vector(R);  // normalize reflection vector
      double[] E = {0.0,50.0,200.0}; // position of the eye
      double[] V = vSubtraction(E,N); // vector from the position of the surface to the eye
      double[] v = unit_vector(V); // normalising vector 
            
      double Range = (intensityMax - intensityMin)/9; // set a reasonable range of addtional light 
      double cos = dot_product(r,v); // angle between reflection vector and camera vector 
      double cos_s_p = Math.pow(cos, 8); // making the reflection sharp
      
      return Range*cos_s_p;
   } // end getIs
   
   /* this is the main method of getting the intensity 
    of the light. The value we get should be in the range
    of 0 to 255. Then this value we could use to make colors
    do not assume unit_vector vector for both vectors*/

   public int getI(double[] N, double[] L){
   
      double IA = getIa(); // get ambient light intensity
      double ID = getId(N,L); // get diffused light intensity 
      double IS = getIs(N,L); // get specular light intensity 
      
      double Idouble = IA+ID; // only consider ambient and diffused light
      
      if(isSpecular)
      {
         Idouble = Idouble + IS; // adding in the specular light 
      }
      
      int I = (int)Math.round(Idouble); // rounding the double value to the nearest integer
      
      /* when I is greater than 255, we know it's absolute white
       otherwise, we will encounter out of bound issue when we 
       try to make a color*/
      if(I >= 250){
         I = 250;
      }
      if(I <= 0){
         I = 0;
      }
      return I;
   } // end getI
   
   // return a unit_vector vector 
   public double[] unit_vector(double[] v){
      return new double[]{v[0]/norm(v), v[1]/norm(v), v[2]/norm(v)};
   }
   
   // return the length of a vector 
   public double norm(double[] v){
      return Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
   }
   
   // return v.n, the dot product
   // the result should be a scalar
   public static double dot_product(double[] v, double[] n){
      
      // check if the length of two vectors match 
      if(v.length != n.length){
         throw new RuntimeException("length does not match,Check again");
      }
      double sum = 0.0;
      for(int k = 0; k < v.length; k++){
         sum += v[k]*n[k];
      }
      return sum;
   } // end dot 
   
   // translate the coordinate just so the origin is (300, 300)
   // since setRGB() doesn't deal with negative coordinates
   public int translationX(int A){
      return A+310; // x translation
   }
   
   // translate the coordinate just so the origin is
   // (300, 300)
   public int translationY(int B){
      return 310-B; // y translation
   }
   
   // get the min and max of X
   public double getranslationX(double p, double q){
      return Math.sqrt(p*p - q*q);
   }
   
   //Z given x, y and r, try to find z
   public double getZ(double p, double q, double r){
      return Math.sqrt(p*p - q*q - r*r);
   }
   
   // it will render a black background, set the RGB of every pixel to black
   public void setBlackScene(){
      for ( int rC = -h/2; rC < h/2; rC++ ) {
         for ( int cC = w/2; cC < -w/2; cC++ ) {
         
            // Set the pixel colour of the image n.b. x = cc, y = rc
            sphereImage.setRGB(translationX(cC), translationY(rC), Color.black.getRGB());
         }//for columns
      }//for rows
   } // end setBlackScene
   
   // render the every pixel of the sphere, set every pixel to its right color to make a 3D effect 
   public void setSphere(){
   
    //It will start from bottom to top incrementing along with every pixel 
    for(int y = (int)(-radius); y < (int)(radius); y++){
         
         // get the max and min x value given y 
         double xmax = getranslationX(radius, (int)y);
         double xmin = -getranslationX(radius, (int)y);
         
         // now loop through all the x in the range we calculated based on a given y, round double to the closest integer
         for(int x = (int)Math.round(xmin); x < (int)Math.round(xmax); x++){
            
            // given x, y, r, according to the equation of a sphere, we can find z 
            double z = getZ(radius, x, y);
            // normal of the pixel,since the sphere sits at the origin
            double[] N = new double[]{(double)x, (double)y, z};
            // here we are getting the intensity of every single pixel 
            int I = getI(N, vSubtraction(pL,N));
            Color shadeColor = getColor(I);
            // set the corresponding pixel with the right intensity
            // here we should translate x, y since setColor() doesn't deal with negative coordinates 
            sphereImage.setRGB(translationX(x),translationY(y), shadeColor.getRGB());
         }
      }
   }// end setSphere
   
   
   // see which color should be drawn on the sphere
   public Color getColor(int I){
   
      // default color: gray
      Color shadeColor = new Color(I,I,I);  
       
      if(isRed){
         shadeColor = new Color(I,0,0);
      }
      
      if(isGreen){
         shadeColor = new Color(0,I,0);
      }
      
      if(isBlue){
         shadeColor = new Color(0,0,I);
      }
      
      return shadeColor;
   } // end getColor
   
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D gd = (Graphics2D)g;
      pL = new double[]{lX, lY, lZ};
      setSphere(); // set the pixels of the sphere
      gd.drawImage(sphereImage, 0, 0, this);
      gd.dispose();
   } // end paintComponent
      
}
