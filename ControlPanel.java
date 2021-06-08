import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel implements ActionListener {

   Canvas controlPanel;
   JButton[] rotationButton; // rotation button
   JButton setButton; //  to take in vector inputs 
   JButton resetButton; //  to reset the cube to orient with z-axis 
   String[] bNames = {"X", "Y", "Z"}; // normal axis
   String[] arbText = {"X-component: ", "Y-component: ", "Z-component: "}; // vector description
   JTextField[] vtext; // text field for vector inputs 
   
   JButton[] rDirectionButtons; // rotation buttons
   JButton[] colorotationButtons; // fill buttons
   
   String[] rDirNames = {"CW", "ACW"}; // rotation names
   String[] colorText = {"Wire", "Solid"}; // fill names
   
   public ControlPanel(Canvas cP){
      controlPanel = cP;
      setLayout(new GridLayout(4,1));
      
      /* set up all the parts we have in the control panel
      bNamePanel stores the button of all the normal axis rotations
      rDirPanel is actually the rotate buttons and colorPanel stores the buttons for wire and solid options */
      JPanel bNamePanel = new JPanel(), rDirectionPanel = new JPanel(), 
                        colorPanel = new JPanel();
      
      // set the layout of all various button panels 
      bNamePanel.setLayout(new GridLayout(3,1));
      rDirectionPanel.setLayout(new GridLayout(4,1));
      colorPanel.setLayout(new GridLayout(4,1));
      
      rotationButton = new JButton[3];
      rDirectionButtons = new JButton[2];
      colorotationButtons = new JButton[2];
      vtext = new JTextField[3];
      
      for(int i = 0; i < vtext.length; i++){
         vtext[i] = new JTextField();
      }
      
      // organize the panel for axis buttons nicely 
      JPanel bNamePanelSub1 = new JPanel();
      JPanel bNamePanelSub2 = new JPanel();
      JPanel bNamePanelSub3 = new JPanel();
                   
      bNamePanelSub1.setLayout(new GridLayout(1,1));
      bNamePanelSub1.add(new JLabel("Normal Axis"));
      bNamePanelSub2.setLayout(new GridLayout(1,3));
      bNamePanelSub3.setLayout(new GridLayout(1,1));
      resetButton = new JButton("Reset");
      resetButton.addActionListener(this);
      bNamePanelSub3.add(resetButton);
      
      JPanel colorPanelSub1 = new JPanel(),
             colorPanelSub2 = new JPanel(),
             colorPanelSub3 = new JPanel(),
             colorPanelSub4 = new JPanel();
      
      // put all the sub panels in a array so in the end you can add all the sub panels bying using a loop
      JPanel[] colorPanelSubs = new JPanel[]{colorPanelSub4, colorPanelSub1, 
                                 colorPanelSub2, colorPanelSub3};
      
      // set up all the sub panels so they look nice 
      colorPanelSub1.setLayout(new GridLayout(1,1));
      colorPanelSub1.add(new JLabel("FILL"));
      colorPanelSub2.setLayout(new GridLayout(1,2));
      colorPanelSub3.setLayout(new GridLayout(1,1));
      
      // sub panels for the rotation panel
      JPanel rDirPanelSub1 = new JPanel(),
             rDirPanelSub2 = new JPanel(),
             rDirPanelSub3 = new JPanel(),
             rDirPanelSub4 = new JPanel();
      
      JPanel[] rDirPanelSubs = new JPanel[]{rDirPanelSub4, rDirPanelSub1, 
                                 rDirPanelSub2, rDirPanelSub3};
             
      rDirPanelSub1.setLayout(new GridLayout(1,1));
      rDirPanelSub1.add(new JLabel("Rotation"));
      rDirPanelSub2.setLayout(new GridLayout(1,2));
      rDirPanelSub3.setLayout(new GridLayout(1,1));
      
      // this is the panel for tha arbitrary axis including label and the textfields that take in user inputs 
      JPanel arbPanel = new JPanel();
      arbPanel.setLayout(new GridLayout(2,1));
      JPanel arbPanelSub1 = new JPanel();
      arbPanelSub1.setLayout(new GridLayout(1,1));
      arbPanelSub1.add(new JLabel("Arbitrary Axis"));
      
      JPanel arbPanelSub2 = new JPanel();
      arbPanelSub2.setLayout(new GridLayout(4,2));
      setButton = new JButton("Set");
      setButton.addActionListener(this);
      
      arbPanel.add(arbPanelSub1);
      arbPanel.add(arbPanelSub2);
      
      // add the buttons all at once
      for(int i = 0; i < bNames.length; i++){
         rotationButton[i] = new JButton(bNames[i]);
         rotationButton[i].addActionListener(this);
         bNamePanelSub2.add(rotationButton[i]);
                 
         arbPanelSub2.add(new JLabel(arbText[i]));
         arbPanelSub2.add(vtext[i]);
      }
      arbPanelSub2.add(new JLabel());
      arbPanelSub2.add(setButton);
      
      bNamePanel.add(bNamePanelSub1);
      bNamePanel.add(bNamePanelSub2); 
      bNamePanel.add(bNamePanelSub3);
      
      // add the buttons all at once 
      for(int j = 0; j < rDirNames.length; j++){
         rDirectionButtons[j] = new JButton(rDirNames[j]);
         rDirectionButtons[j].addActionListener(this);
         rDirPanelSub2.add(rDirectionButtons[j]);
         
         colorotationButtons[j] = new JButton(colorText[j]);
         colorotationButtons[j].addActionListener(this);
         colorPanelSub2.add(colorotationButtons[j]);
         
      }
      
      // add in all the sub panels all at once 
      for(int k = 0; k < colorPanelSubs.length; k++){
         colorPanel.add(colorPanelSubs[k]);
         rDirectionPanel.add(rDirPanelSubs[k]);
      }
      
      // add all the sub control panels to the 
      // real control panel
      add(bNamePanel);
      add(arbPanel);
      add(colorPanel);
      add(rDirectionPanel);
  }
   
   
  /* check if the string gives a numerical value
   try to convert the string text into a number
   if failed, we will be careful not to take in 
   that invalid result. We certainly don't want
   to deal with random string inputs from users */
  public static boolean isNumerical(String str){  
    try{  
      double d = Double.parseDouble(str);  
    }catch(NumberFormatException notfe){  
      return false; 
    }  
    return true;  
  } // end isNumerical
   
   public void actionPerformed(ActionEvent event){
   
      // if rotate around x-axis
      if(event.getSource() == rotationButton[0]){
         controlPanel.isRX = true;
         controlPanel.isRY = false;
         controlPanel.isRZ = false;
         controlPanel.isArb = false;
         controlPanel.theta = 0.0;
      }
      
      // if rotate around y-axis 
      if(event.getSource() == rotationButton[1]){
         controlPanel.isRX = false;
         controlPanel.isRY = true;
         controlPanel.isRZ = false;
         controlPanel.isArb = false;
         controlPanel.theta = 0.0;
      }
      
      // if rotate around z-axis 
      if(event.getSource() == rotationButton[2]){
         controlPanel.isRX = false;
         controlPanel.isRY = false;
         controlPanel.isRZ = true;
         controlPanel.isArb = false;
         controlPanel.theta = 0.0;
      }
      
      // if the user lost the orientation of the cube he could reset the axis to the z-axis
      if(event.getSource() == resetButton){
      
         // the vertices of how a cube is to be oriented with it is set to align with the z-axis 
         controlPanel.vertices = new double[][]{{-1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, 
                                          {1.0, -1.0, 1.0}, {-1.0, -1.0, 1.0}, 
                                          {-1.0, -1.0, -1.0}, {1.0, -1.0, -1.0}, 
                                          {1.0, 1.0, -1.0}, {-1.0, 1.0, -1.0}
                                       };
         
         Matrix.scaling(controlPanel.vertices, 100.0); // don't forget about the scale
         controlPanel.theta = 0.0;
         
         controlPanel.repaint();

      }
      
      /* if the user has entered the vector that indicates 
       an arbitrary axis. once setButton is clicked, all
       text inputs will be converted into doubles. if the
       convertion fails, nothing happens and the cube will
       stuck there, unable to do anything unless a valid 
       vector is entered */
      if(event.getSource() == setButton){
         controlPanel.isRX = false;
         controlPanel.isRY = false;
         controlPanel.isRZ = false;
         controlPanel.isArb = true;
         controlPanel.theta = 0.0;
         
         double[] aT = new double[3];
         
         // if validation check fails, doing nothing, disable rotations function
         for(int k = 0; k < vtext.length; k++){
            if(vtext[k].getText().isEmpty() || 
               !isNumerical(vtext[k].getText())){
               controlPanel.isRX = false;
               controlPanel.isRY = false;
               controlPanel.isRZ = false;
               controlPanel.isArb = false;
               controlPanel.theta = 0.0;

               return;
            }
         }
         // convet string into double validation check 
         for(int i = 0; i < vtext.length; i++){
            if(!(vtext[i].getText().isEmpty()) &&
                  isNumerical(vtext[i].getText())){
               aT[i] = Double.parseDouble(vtext[i].getText());
            }
         }
         
         controlPanel.b = aT;
      }
      
      // rotate clock wise
      if(event.getSource() == rDirectionButtons[0]){
         controlPanel.theta = -15.0;
         controlPanel.repaint();
      }
      
      // rotate counterclockwise 
      if(event.getSource() == rDirectionButtons[1]){
         controlPanel.theta = 15.0;
         controlPanel.repaint();
      }
      
      // it will fill with wireframe
      if(event.getSource() == colorotationButtons[0]){
         for(int i = 0; i < controlPanel.isSolid.length; i++){
            controlPanel.isSolid[i] = false;
            controlPanel.theta = 0.0;
         }
         controlPanel.repaint();
      }
      
      // it will fill with color 
      if(event.getSource() == colorotationButtons[1]){
         for(int i = 0; i < controlPanel.isSolid.length; i++){
            controlPanel.isSolid[i] = true;
            controlPanel.theta = 0.0;
         }
         controlPanel.repaint();
      }
      
   } // end actionPerformed

}
