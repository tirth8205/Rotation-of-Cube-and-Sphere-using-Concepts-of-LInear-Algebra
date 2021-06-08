import java.awt.*;
import javax.swing.*;

public class RunnableGUI{

   public static void main(String[] args){
		
		JFrame frame = new JFrame();
		frame.setTitle("Cube3D");
		frame.setSize(802,802);

		//it will Set the window to close when upper right corner is clicked 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      Canvas controlPanel = new Canvas();
      ControlPanel controller = new ControlPanel(controlPanel);
      Container cP = frame.getContentPane();
     
      // it will set up a border size just so canvas and controller will not touch each other
	   cP.setLayout(new BorderLayout(10, 10));
     
      // set the size of the panel to match up with the canvas
      controlPanel.setPreferredSize(new Dimension(650, 650));
      controller.setPreferredSize(new Dimension(242,600));
      
      cP.add(controlPanel, BorderLayout.CENTER); // it will put the canvas in the center
      cP.add(controller,BorderLayout.EAST); //it will put the controller in the east

		frame.add(controlPanel);
		frame.pack(); // it will resize to preferred size for components.
		frame.setResizable(true);
		frame.setVisible(true);
		
   }
}

