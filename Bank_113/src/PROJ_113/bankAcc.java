package PROJ_113;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class bankAcc {
	private float balance;
	private float previousTransaction;
	
	 
	public class BankAccountHandler {
	    private String pin;

	    public BankAccountHandler(String pin) {
	        this.pin = pin;
	    }

	    public void deposit(float amount) {
	        try {
	            float currentBalance = DatabaseHandler.getBalance(pin);
	            DatabaseHandler.updateBalance(pin, currentBalance + amount);
	            JOptionPane.showMessageDialog(null, "Deposited: " + amount);
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    public void withdraw(float amount) {
	        try {
	            float currentBalance = DatabaseHandler.getBalance(pin);
	            if (currentBalance >= amount) {
	                DatabaseHandler.updateBalance(pin, currentBalance - amount);
	                JOptionPane.showMessageDialog(null, "Withdrawn: " + amount);
	            } else {
	                JOptionPane.showMessageDialog(null, "Insufficient balance.");
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    public void showBalance() {
	        try {
	            float currentBalance = DatabaseHandler.getBalance(pin);
	            JOptionPane.showMessageDialog(null, "Balance: " + currentBalance);
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
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
