import java.awt.*;
import javax.swing.*;


// GUI launcher
public class RunnableGUI{

   public static void main(String[] args){
		
		JFrame frame = new JFrame();
		frame.setTitle("Sphere3D");
		frame.setSize(800,800);

		//Sets the window to close when upper right corner clicked.  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      Canvas canvas = new Canvas();
      ControlPanel controller = new ControlPanel(canvas);
      
      Container cp = frame.getContentPane();
     
     
      // set up a border size just so canvas and controller will not touch each other
	   cp.setLayout(new BorderLayout(10, 10));
     
      // set the size of the panel to match up with the canvas
      canvas.setPreferredSize(new Dimension(620, 620));
      controller.setPreferredSize(new Dimension(165,600));
      
      cp.add(canvas, BorderLayout.CENTER); // it will put the canvas in the center
      cp.add(controller,BorderLayout.EAST); // it will put the controller in the east
      
		frame.add(canvas);
		frame.pack(); //  resizes to preferred size for components.
		frame.setResizable(false);
		frame.setVisible(true);
		
   }
}
