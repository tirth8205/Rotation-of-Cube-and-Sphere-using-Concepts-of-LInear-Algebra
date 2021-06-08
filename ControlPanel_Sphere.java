import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


// control panel for user inputs 
public class ControlPanel extends JPanel implements ActionListener
{
   Canvas canvas; // canvas panel for drawing the sphere
   JButton[] colorButtons; // buttons for changing the sphere colors
   String[] colorNames = {"Red", "Green", "Blue", "Gray"};
   
   JButton[] specButtons; // these are the button to activate specular light
   String[] specNames = {"Specular", "Non-specular"};
   
   JButton[] posButtons; // these are the buttons to change the postions of the light 
   String[] posNames = {"X+50", "X-50", "Y+50", "Y-50", "Reset"};
      
   public ControlPanel(Canvas c){
      canvas = c;
      setLayout(new GridLayout(3,1));
      
      // set up the color panel and add in the corresponding buttons
      JPanel colorPanel = new JPanel();
      colorPanel.setLayout(new GridLayout(3,3));
      colorButtons = new JButton[4];
      for(int k = 0; k < colorNames.length; k++){
         colorButtons[k] = new JButton(colorNames[k]);
         colorButtons[k].addActionListener(this);
         colorPanel.add(colorButtons[k]);
      }
      
      // set up the specular light panel and add in corresponding buttons 
      JPanel specPanel = new JPanel();
      specButtons = new JButton[2];
      specPanel.setLayout(new GridLayout(3,1));
      
      for(int k = 0; k < specNames.length; k++){
         specButtons[k] = new JButton(specNames[k]);
         specButtons[k].addActionListener(this);
         specPanel.add(specButtons[k]);
      }
      
      /* set up the panel for changing the position of the light source and add in the corresponding
       buttons to change the location for simplicity, we can only change x and y coordinates
       letting the distance to the sphere fixed */
      JPanel posPanel = new JPanel();
      posPanel.setLayout(new GridLayout(3,2));
      
      posButtons = new JButton[5];
      
      for(int k = 0; k < posNames.length; k++){
         
         posButtons[k] = new JButton(posNames[k]);
         posButtons[k].addActionListener(this);
         posPanel.add(posButtons[k]);
      }
      
      // add in all the panels for control
      add(colorPanel);
      add(specPanel);
      add(posPanel);
   } // end ControlPanel
   
   public void actionPerformed(ActionEvent event){
      
      // if the user wants a red sphere
      if(event.getSource() == colorButtons[0]){
         canvas.isRed = true;
         canvas.isGreen = false;
         canvas.isBlue = false;
         canvas.repaint();
      }
      
      // if the user wants a green sphere
      if(event.getSource() == colorButtons[1]){
         canvas.isRed = false;
         canvas.isGreen = true;
         canvas.isBlue = false;
         canvas.repaint();
      }
      
      // if the user wants a blue sphere
      if(event.getSource() == colorButtons[2]){
         canvas.isRed = false;
         canvas.isGreen = false;
         canvas.isBlue = true;
         canvas.repaint();
      }
      
      // default sphere, gray
      if(event.getSource() == colorButtons[3]){
         canvas.isRed = false;
         canvas.isGreen = false;
         canvas.isBlue = false;
         canvas.repaint();
      }
      
      // if the user wants to have specular effect
      if(event.getSource() == specButtons[0]){
         canvas.isSpecular = true;
         canvas.repaint();
      }
      
      // if the user only want ambient and diffused light effect
      if(event.getSource() == specButtons[1]){
         canvas.isSpecular = false;
         canvas.repaint();
      }
      
      // move the light source to the right
      if(event.getSource() == posButtons[0]){
         canvas.lX += 50.0;
         canvas.repaint();
      }
      
      // move the light source to the left
      if(event.getSource() == posButtons[1]){
         canvas.lX -= 50.0;
         canvas.repaint();
      }
      
      // move the light source up
      if(event.getSource() == posButtons[2]){
         canvas.lY += 50.0;
         canvas.repaint();
      }
      
      // move the light source down 
      if(event.getSource() == posButtons[3]){
         canvas.lY -= 50.0;
         canvas.repaint();
      }
      
      // move the light source to the default position
      if(event.getSource() == posButtons[4]){
         canvas.lX = 0.0;
         canvas.lY = 120.0;
         canvas.repaint();
      }
      
   }// end actionPerformed 

}
