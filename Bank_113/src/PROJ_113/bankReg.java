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

public class bankReg extends bankAcc implements ActionListener {
	JTextField nameField;
	JTextField depositField;
	JButton registerButton;
	JTextField pinField;
	JFrame frame = new JFrame();
	static String name;
	static String PIN;
	static String depositStr;
	private bankAcc bankAccount;
	
public bankReg() {
	this.bankAccount = new bankAcc();
		
		//LABEL TITLE  DESIGN
		JLabel label = new JLabel();
	    label.setText("REGISTRATION");
	    label.setForeground(Color.white);//set font color of text
	    label.setBounds(280, 100, 600, 60);
	    label.setFont(new Font("Arial", Font.BOLD, 72));//set font of text
	   
	    //JLABEL OUTPUT 
	    JLabel nameLabel = new JLabel("Name:"); 
	    nameLabel.setBounds(340, 250, 100, 30);
	    nameLabel.setHorizontalAlignment(JLabel.CENTER);
	    nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    nameLabel.setForeground(Color.white);//set font color of text
	    
	    JLabel pinLabel = new JLabel("PIN (minimum 6 digits):");
	    pinLabel.setBounds(220, 300, 210, 30);
	    pinLabel.setHorizontalAlignment(JLabel.CENTER);
	    pinLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    pinLabel.setForeground(Color.white);//set font color of text
	    
	    JLabel depositLabel = new JLabel("Deposit (minimum 3000):");
	    depositLabel.setBounds(215, 350, 210, 30);
	    depositLabel.setHorizontalAlignment(JLabel.CENTER);
	    depositLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    depositLabel.setForeground(Color.white);//set font color of text
	    
	    //TEXTFIELD DESIGN
	    nameField = new JTextField(20);
	    nameField.setBounds(430, 250, 220, 30);
	    nameField.setHorizontalAlignment(JTextField.CENTER);
	    nameField.setFont(new Font("Arial", Font.PLAIN, 16));
	    
	    pinField = new JTextField(20);
	    pinField.setBounds(430, 300, 220, 30);
	    pinField.setHorizontalAlignment(JTextField.CENTER);
	    pinField.setFont(new Font("Arial", Font.PLAIN, 16));
	    
	    depositField = new JTextField(20);
	    depositField.setBounds(430, 350, 220, 30);
	    depositField.setHorizontalAlignment(JTextField.CENTER);
	    depositField.setFont(new Font("Arial", Font.PLAIN, 16));
	    
	    //REGISTER BUTTON DESIGN
	    registerButton = new JButton("Register");
	    registerButton.addActionListener(this);
	    registerButton.setBounds(530, 400, 120, 30);
	    registerButton.setFocusable(false);
	    registerButton.setForeground(Color.black);//set font color of text
	    registerButton.setBackground(Color.white);//set background color
	    
	    //BACKGROUND DESIGN
	    ImageIcon design = new ImageIcon ("blue.jpg");
	    Border border = BorderFactory.createLineBorder(Color.yellow,8);
	    //display the image	
	    JLabel desBG = new JLabel(design);
	    desBG.setSize(1100,720);
	    desBG.setBorder(border);
	    desBG.add(label);
	    desBG.add(nameLabel);
	    desBG.add(nameField);
	    desBG.add(pinLabel);
	    desBG.add(pinField);
	    desBG.add(depositLabel);
	    desBG.add(depositField);
	    desBG.add(registerButton);
	    
	    //FRAME
	    frame = new JFrame();
	    frame .setTitle("Arisu Bank System");
	    frame .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame .setResizable(false);
	    frame .setSize(1100, 720);
	    frame .setLocationRelativeTo(null);// Center the frame on the screen
	    frame .setVisible(true);
	    frame .setContentPane(desBG);
}

@Override
public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			
		bankReg.name = nameField.getText();
		bankReg.PIN = pinField.getText();
		bankReg.depositStr = depositField.getText();
		//check if the name and PIN is not input if the user is pressing the register button
		if (bankReg.name.isEmpty() || bankReg.PIN.isEmpty() || bankReg.depositStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill up the requirements so that you can create an account.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
				
		// Validate ID input to ensure it contains only numbers
		try {
			int pinNumber = Integer.parseInt(bankReg.PIN);
			} catch (NumberFormatException ex) {
			 JOptionPane.showMessageDialog(null, "PIN must contain only numbers.", "Error", JOptionPane.ERROR_MESSAGE);
			 return;
			}

		// Validate name input to ensure it does not contain numbers
		if (bankReg.name.chars().anyMatch(Character::isDigit)) {
			JOptionPane.showMessageDialog(null, "Name cannot contain numbers. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			
		//to limit 4 pin
		if (bankReg.PIN.length() != 6) {
			JOptionPane.showMessageDialog(null, "PIN must be exactly six digits long.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
				
		try {
			float amount = Float.parseFloat(bankReg.depositStr);
				if (amount < 3000) {
				JOptionPane.showMessageDialog(null, "Minimum deposit amount is 3000.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
				// Call the deposit method in BankAcc and save the deposit amount
			    bankAccount.deposit(amount);
				// Open the next class if registration is successful  
				new pin(bankAccount);
				frame.dispose();		      
				JOptionPane.showMessageDialog(null, "Registration successful! Welcome, " +bankReg.name + "!");
				}
	     }catch (NumberFormatException ex) {
		JOptionPane.showMessageDialog(null, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	  }
	}			
}

	


