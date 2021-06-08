// matrix utility class

public class Matrix {

   //  It returns "p.q" which is stored in result1(scalar)
   public static double dot_product(double[] p, double[] q)
   {

      // It will check if the length of two vectors same or not?
      if (p.length != q.length)
      {
         throw new RuntimeException("length does not match, Check again.");
      }

      double result1 = 0.0;
      for (int i = 0; i < p.length; i++) {
         result1 += p[i] * q[i];
      }
      return result1;
   }


   // It will return a transformed vector after doing transformation
   // Here A is a corresponding matrix
   // performing Linear tranformation of v1 to v2  by using matrix m,"v2 = m*v1"
   public static double[] transformation(double[][] A, double[] v1) {

      int nrows_m = A.length; // number of the rows of the matrix A
      int ncolumns_m = A[0].length; // number of the columns of the matrix A

      int length_v1 = v1.length;

      // check if dimension matches or not?
      // if the length of the vector is not equal to the number of rows of the matrix, then something goes wrong
      if (length_v1 != ncolumns_m) {
         throw new RuntimeException("dimensions don't match, Check again");
      }

      // the length should be equal to the number of rows of the matrix
      // the transformed vector(v2)
      double[] v2 = new double[nrows_m];

      for (int i = 0; i < nrows_m; i++) {
         for (int j = 0; j < ncolumns_m; j++) {
            v2[i] += A[i][j] * v1[j];
         }
      }

      return v2;
   } // end transformation


   public static double norm(double[] v) {
      // Norm of a vector
      return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
   }// end norm

   
   //For finding unit vector
   public static double[] unit_vector(double[] v) {
      // Divide each element by norm of vector
      return new double[] { v[0] / norm(v), v[1] / norm(v), v[2] / norm(v) };
   }

   // only deal with 3D vectors
   // to find the cos(theta) between two given vectors
   public static double finding_CosTheta(double[] v, double[] w) {
      return dot_product(v, w) / (norm(v) * norm(w));
   }// end getCosTheta

   // rotate around x-axis
   public static double[] rotationX1(double[] v, double theta) {
      // the matrix rotation around x-axis(clockwise)
      double[][] Ax = { { 1.0, 0.0, 0.0 },
                        { 0.0, Math.cos(theta), -Math.sin(theta) },
                        { 0.0, Math.sin(theta), Math.cos(theta) }
                      };

      double[] vt = transformation(Ax, v);
      return vt;

      // v = vt;
   } // rotationX1

   // this is another way to rotate the given vector that we could
   // sign indicates whether the rotation is clockwise or counterclockwise
   // this idea is for the arbitrary axis
   public static double[] rotationX2(double[] v, double[] b, int sign) {

      double[] bu = unit_vector(b); // normalize the arbitrary axis

      double g = Math.sqrt(bu[1] * bu[1] + bu[2] * bu[2]);
      // implementing
      // now we directly put cos and sin of theta
      double[][] Rx = { { 1.0, 0.0, 0.0 },
                        { 0, bu[2] / g, (-bu[1] / g) * sign },
                        { 0, (bu[1] / g) * sign, bu[2] / g } 
                     };

      double[] vt = transformation(Rx, v);

      return vt;
   } // end rotationX2

   // rotate around y-axis
   public static double[] rotationY1(double[] v, double theta) {

      double[][] Ay = { { Math.cos(theta), 0.0, Math.sin(theta) },
                        { 0.0, 1.0, 0.0 },
                        { -Math.sin(theta), 0.0, Math.cos(theta) } 
                     };

      double[] vt = transformation(Ay, v);

      return vt;
   } // end rotationY1

   // the format and idea are the same as rotate around x axis
   public static double[] rotationY2(double[] v, double[] c, int sign) {

      double[] cu = unit_vector(c);
      double g = Math.sqrt(cu[1] * cu[1] + cu[2] * cu[2]);

      // implementing the rotation around y-axis with matrix
      double[][] Ay = { { g, 0.0, (-cu[0]) * sign },
                        { 0, 1.0, 0.0 },
                        { (cu[0]) * sign, 0.0, g }
                     };

      double[] vt = transformation(Ay, v);

      return vt;
   } // end rotationY2

   // rotation with respect to z-axis
   public static double[] rotationZ1(double[] v, double theta) {

      double[][] Az = { { Math.cos(theta), -Math.sin(theta), 0.0 },
                        { Math.sin(theta), Math.cos(theta), 0.0 },
                        { 0.0, 0.0, 1.0 } 
                     };

      double[] vt = transformation(Az, v);

      return vt;
   } // end rotationZ1

   // for arbitrary rotation
   public static double[] rotateArb(double[] v, double[] p, double theta) {

      double[] vt;

      // we want to bring the arbitrary axis to the z-axis after that we can rotate it around the z-axis
      // when the rotation around z-axis will be done, we rotate the point back
      vt = rotationX2(v, p, 1); // rotation around x based on vector p
      vt = rotationY2(vt, p, 1); // rotation around y based on vector p
      vt = rotationZ1(vt, theta); //  the rotation that we want
      vt = rotationY2(vt, p, -1); // rotation around y back
      vt = rotationX2(vt, p, -1); // rotation around x back

      return vt;
   }

   // transform x and y coordinates for a 3D object to appear on the screen
   public static double[] perspectiveTransformation(double[] v, double p, double q) {
      double[] vt = new double[v.length];

      for (int k = 0; k < v.length; k++) {
         vt[k] = v[k] / (1 - p / q);
      } 
      return vt;
   }

   // scale all the vertices to a visible amount
   public static void scaling(double[][] v, double scaleFactor) {
      for (int k = 0; k < v.length; k++)
      {
         for (int l = 0; l < v[0].length; l++)
         {
            v[k][l] = v[k][l] * scaleFactor;
         }
      }
   }
}
