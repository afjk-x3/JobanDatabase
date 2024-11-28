package PROJ_113;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM extends bankAcc implements ActionListener{
	JButton withdrawButton;
	JButton depositButton;
	JButton viewbalanceButton;
	JButton exitButton;
	JFrame frame = new JFrame();
	private bankAcc bankAccount;

ATM(bankAcc bankAccount){	
		this.bankAccount = bankAccount;
		
		//LABEL TITLE  DESIGN
		JLabel label = new JLabel();
	    label.setText("ARISU BANK");
	    label.setBounds(250, 100, 600, 60);
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(new Font("Arial", Font.BOLD, 72));//set font of text
	    label.setForeground(Color.white);//set font color of text
	    
		//withdraw button
		withdrawButton = new JButton();//BUTTON
		withdrawButton.setBounds(440, 250, 220, 50);
		withdrawButton.addActionListener(this);
		withdrawButton.setText("Withdraw");
		withdrawButton.setFont(new Font ("Arial",Font.BOLD,24));
		withdrawButton.setFocusable(false);
		withdrawButton.setForeground(Color.black);//set font color of text
		withdrawButton.setBackground(Color.white);//set background color
		//deposit button
		depositButton = new JButton();//BUTTON
		depositButton.setBounds(440, 330, 220, 50);
		depositButton.addActionListener(this);
		depositButton.setText("Deposit");
		depositButton.setFont(new Font ("Arial",Font.BOLD,24));
		depositButton.setFocusable(false);
		depositButton.setForeground(Color.black);//set font color of text
		depositButton.setBackground(Color.white);//set background color
		
		//viewbalance button
		viewbalanceButton = new JButton();//BUTTON
		viewbalanceButton.setBounds(440, 410, 220, 50);
		viewbalanceButton.addActionListener(this);
		viewbalanceButton.setText("View Balance");
		viewbalanceButton.setFont(new Font ("Arial",Font.BOLD,24));
		viewbalanceButton.setFocusable(false);
		viewbalanceButton.setForeground(Color.black);//set font color of text
		viewbalanceButton.setBackground(Color.white);//set background color
		
		//exit button
		exitButton = new JButton();//BUTTON
		exitButton.setBounds(440, 490, 220, 50);
		exitButton.addActionListener(this);
		exitButton.setText("Exit");
		exitButton.setFont(new Font ("Arial",Font.BOLD,24));
		exitButton.setFocusable(false);
		exitButton.setForeground(Color.black);//set font color of text
		exitButton.setBackground(Color.white);//set background color
		
		//BACKGROUND DESIGN
		ImageIcon design = new ImageIcon ("cash.gif");
		    
		//display image,label,field,button
		JLabel desBG = new JLabel();
		desBG.setSize(1100,720);
		desBG.setIcon(design);
		desBG.add(label);
		desBG.add(depositButton);
		desBG.add(withdrawButton);
		desBG.add(viewbalanceButton);
		desBG.add(exitButton);
		    
		//name of the window
		frame = new JFrame();
		frame.setTitle(" Arisu Bank System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1100, 720);
		frame.setLocationRelativeTo(null);// Center the frame on the screen
		frame.setVisible(true);
		frame.add(desBG);
			
}

@Override
public void actionPerformed(ActionEvent e) {
		if (e.getSource() == withdrawButton) {
		String withdrawInput = JOptionPane.showInputDialog("Enter withdrawal amount:");// Prompt user to enter withdrawal amount
		   
		if (withdrawInput != null) {
			if (!withdrawInput.isEmpty()) {
				try {
					float amount = Float.parseFloat(withdrawInput);// Convert input to an integer
					bankAccount.withdraw(amount);// Withdraw the given amount
					bankAccount.showPreviousTransaction();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid withdrawal amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE); // Display an error message for invalid input
				}
		  }else {
		   JOptionPane.showMessageDialog(null, "No withdrawal amount entered. Transaction canceled.", "Information", JOptionPane.INFORMATION_MESSAGE);}   
		 	}
		}
	
		if (e.getSource() == depositButton) {
		String depositInput = JOptionPane.showInputDialog("Enter deposit amount:");// Prompt user to enter deposit amount
		   
		   if (depositInput != null) {
		       if (!depositInput.isEmpty()) {
		    	   try {
		    		   float amount = Float.parseFloat(depositInput);// Convert input to integer
		    		   bankAccount.deposit(amount);// Deposit the given amount
		    		   bankAccount.showPreviousTransaction();
		    	   } catch (NumberFormatException ex) {
		    		   JOptionPane.showMessageDialog(null, "Invalid deposit amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);// Display error message for invalid input
		    	   }   
		 } else {
		   JOptionPane.showMessageDialog(null, "No deposit amount entered. Transaction canceled.", "Information", JOptionPane.INFORMATION_MESSAGE);}   
		   	}
		}
		
		if (e.getSource() == viewbalanceButton) {
		 bankAccount.showBalance();// Display the current balance
		 }
		
		if (e.getSource() == exitButton) {
			System.exit(0);		}
	}
}
