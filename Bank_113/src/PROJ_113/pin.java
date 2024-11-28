package PROJ_113;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JPasswordField;

public class pin extends bankAcc implements ActionListener{
	JPasswordField pinField = new JPasswordField();
	JFrame frame = new JFrame();
	JButton enterButton;
	JButton exitButton;
	private bankAcc bankAccount;
	
public pin(bankAcc bankAccount){
	this.bankAccount = bankAccount;
		
		//LABEL TITLE  DESIGN
	    JLabel label = new JLabel();
	    label.setText("ENTER PIN");
	    label.setForeground(Color.white);//set font color of text
	    label.setBounds(240, 100, 600, 60);
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(new Font("Arial", Font.BOLD, 72));//set font of text
	 
	    //USER INPUT PIN
	    pinField.setBounds(420, 200, 250, 100);
	    pinField.setHorizontalAlignment(JTextField.CENTER);
	    pinField.setFont(new Font("Arial", Font.BOLD, 64));
	    
	    //REGISTER BUTTON DESIGN
	    enterButton = new JButton("Enter");
	    enterButton.addActionListener(this);
	    enterButton.setBounds(550, 350, 120, 35);
	    enterButton.setFocusable(false);
	    enterButton.setForeground(Color.black);
	    enterButton.setBackground(Color.white);//set background color
	    	        
	    //background design
	    ImageIcon design = new ImageIcon ("blue.jpg");
	    Border border = BorderFactory.createLineBorder(Color.yellow,8);
		//display image,label,field,button
	    JLabel desBG = new JLabel();
	    desBG.setSize(1100,720);
	    desBG.setBorder(border);
	    desBG.setIcon(design);
	    desBG.add(label);
	    desBG.add(pinField);
	    desBG.add(enterButton);
		    
	    //name of the window
	    frame = new JFrame();
	    frame.setTitle("Arisu Bank System");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setSize(1100, 720);
	    frame.setLocationRelativeTo(null);// Center the frame on the screen
	    frame.setVisible(true);
	    frame.add(desBG);
}

@Override
public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enterButton) {
		String pin = String.valueOf(pinField.getPassword());
		
		//check if there is no input
		if (pin.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No input, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		try {
		    int pinNumber = Integer.parseInt(pin);
		} catch (NumberFormatException ex) {
		    JOptionPane.showMessageDialog(null, "PIN must contain only numbers.", "Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		//to limit 6 pin
		if (pin.length() != 6) {
		    JOptionPane.showMessageDialog(null, "PIN must be exactly six digits long.", "Information", JOptionPane.INFORMATION_MESSAGE);
		    return;
		}
						
		if (pin.equals(bankReg.PIN)) { // ensure if the user will input the same pin that the user input in registration
            new ATM(bankAccount);
            frame.dispose(); // Close the current frame
        }else {
			JOptionPane.showMessageDialog( null,"Incorrect PIN!!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}

	
}