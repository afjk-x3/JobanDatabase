package PROJ_113;

import javax.swing.JOptionPane;

public class bankAcc {
	private float balance;
	private float previousTransaction;
	
	 
bankAcc(){// Constructor to initialize balance and previous transaction
	this.balance = 0;
	this.previousTransaction = 0;
}

public void deposit (float amount) {// Method to deposit amount into the account
	if (amount > 0) {
		balance += amount;
		previousTransaction = amount;
		}
	}	
	
public void withdraw (float amount) {// Method to withdraw amount from the account
	if (amount > 0) {
	  if (balance - amount >= 0) {
	  this.balance -= amount;
	  this.previousTransaction = -amount;
	} else {
	   JOptionPane.showMessageDialog(null,"Insufficient balance. Withdrawal canceled.");//Display message if the user withdraw less than 0
	  }
	} else {
	   JOptionPane.showMessageDialog(null,"Invalid amount. Withdrawal amount must be greater than 0.");// Display error message for invalid amount
	 }
}

public void showBalance() { // Method to display the current balance
	JOptionPane.showMessageDialog(null,String.format("Balance: %.2f ",balance));
}

public void showPreviousTransaction() {// Method to show the details of the previous transaction
	if (previousTransaction > 0) {
	    JOptionPane.showMessageDialog(null,String.format("Deposited: %.2f",previousTransaction));
	} else if (previousTransaction < 0) {
	    JOptionPane.showMessageDialog(null,String.format("Withdrawn: %.2f",previousTransaction));
	} else {
	    JOptionPane.showMessageDialog(null,"No transaction occurred");
	}
}	


}
