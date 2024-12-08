package FINALPROJ;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BankSystemGUI {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "java";
    private static final String DB_PASSWORD = "password";

    private JFrame frame;
    private int authenticatedUserId = -1; // Tracks authenticated user ID

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankSystemGUI::new);
    }

    public BankSystemGUI() {
        showCreateAccountGUI();
    }

    // GUI 1: Create Account
  
    private void showCreateAccountGUI() {
    	    frame = new JFrame("Create Account");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(400, 250);

    	    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

    	    JLabel nameLabel = new JLabel("Name:");
    	    JTextField nameField = new JTextField();
    	    JLabel pinLabel = new JLabel("PIN (6 digits):");
    	    JPasswordField pinField = new JPasswordField();
    	    JButton createButton = new JButton("Create Account");
    	    JButton loginButton = new JButton("Already Have an Account"); // New button
    	    JLabel resultLabel = new JLabel("", JLabel.CENTER);

    	    createButton.addActionListener(e -> {
    	        String name = nameField.getText();
    	        String pin = new String(pinField.getPassword());

    	        if (pin.length() != 6 || !pin.matches("\\d+")) {
    	            resultLabel.setText("Invalid PIN. Must be 6 digits.");
    	            return;
    	        }

    	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	            String userQuery = "INSERT INTO Users (name, pin) VALUES (?, ?)";
    	            try (PreparedStatement stmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
    	                stmt.setString(1, name);
    	                stmt.setString(2, pin);
    	                stmt.executeUpdate();

    	                try (ResultSet rs = stmt.getGeneratedKeys()) {
    	                    if (rs.next()) {
    	                        int userId = rs.getInt(1);

    	                        String accountQuery = "INSERT INTO BankAccount (user_id, balance) VALUES (?, 0.00)";
    	                        try (PreparedStatement accountStmt = conn.prepareStatement(accountQuery)) {
    	                            accountStmt.setInt(1, userId);
    	                            accountStmt.executeUpdate();
    	                        }

    	                        resultLabel.setText("Account created! Your User ID is: " + userId);
    	                        JOptionPane.showMessageDialog(frame, "Account created! Your User ID is: " + userId);
    	                        frame.dispose();
    	                        showPinAuthenticationGUI(); // Go to PIN authentication
    	                    }
    	                }
    	            }
    	        } catch (SQLException ex) {
    	            resultLabel.setText("Error: " + ex.getMessage());
    	        }
    	    });

    	    // Action listener for login button
    	    loginButton.addActionListener(e -> {
    	        frame.dispose(); // Close the current frame
    	        showPinAuthenticationGUI(); // Go to PIN authentication GUI
    	    });

    	    panel.add(nameLabel);
    	    panel.add(nameField);
    	    panel.add(pinLabel);
    	    panel.add(pinField);
    	    panel.add(createButton);
    	    panel.add(loginButton); // Add the new button
    	    panel.add(resultLabel);

    	    frame.add(panel);
    	    frame.setVisible(true);
    }
        
        

       
    

    // GUI 2: PIN Authentication
    private void showPinAuthenticationGUI() {
        frame = new JFrame("Authenticate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel resultLabel = new JLabel("", JLabel.CENTER);

        loginButton.addActionListener(e -> {
            int userId;
            try {
                userId = Integer.parseInt(userIdField.getText());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid User ID.");
                return;
            }

            String pin = new String(pinField.getPassword());

            if (authenticateUser(userId, pin)) {
                authenticatedUserId = userId; // Save authenticated user ID
                frame.dispose();
                showTransactionGUI(); // Go to transaction GUI
            } else {
                resultLabel.setText("Authentication failed.");
            }
        });

        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginButton);
        panel.add(resultLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    // GUI 3: Transactions
    private void showTransactionGUI() {
        frame = new JFrame("Transactions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Show Balance");
        JButton exitButton = new JButton("Exit");

        depositButton.addActionListener(e -> performTransaction("Deposit"));
        withdrawButton.addActionListener(e -> performTransaction("Withdraw"));
        balanceButton.addActionListener(e -> showBalance());
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Goodbye!");
            System.exit(0);
        });

        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(balanceButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Perform deposit or withdraw
    private void performTransaction(String type) {
        String input = JOptionPane.showInputDialog(frame, "Enter amount to " + type.toLowerCase() + ":");
        if (input == null || input.isEmpty()) return;

        double amount;
        try {
            amount = Double.parseDouble(input);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(frame, "Invalid amount.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = (type.equals("Deposit"))
                    ? "UPDATE BankAccount SET balance = balance + ? WHERE user_id = ?"
                    : "UPDATE BankAccount SET balance = balance - ? WHERE user_id = ? AND balance >= ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, authenticatedUserId);
                if (type.equals("Withdraw")) stmt.setDouble(3, amount);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, type + " successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Transaction failed. Insufficient balance.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    // Show balance
    private void showBalance() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT balance FROM BankAccount WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, authenticatedUserId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double balance = rs.getDouble("balance");
                        JOptionPane.showMessageDialog(frame, "Your balance is: $" + balance);
                    } else {
                        JOptionPane.showMessageDialog(frame, "User not found.");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    // Authenticate user
    private boolean authenticateUser(int userId, String pin) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT 1 FROM Users WHERE user_id = ? AND pin = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setString(2, pin);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
