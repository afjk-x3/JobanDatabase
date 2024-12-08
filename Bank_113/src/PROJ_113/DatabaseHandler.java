package PROJ_113;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    // Save user data during registration
    public static void saveUser(String name, String pin, float balance) throws SQLException {
        String insertUserQuery = "INSERT INTO Users (name, pin) VALUES (?, ?)";
        String insertAccountQuery = "INSERT INTO BankAccount (user_id, balance) VALUES (LAST_INSERT_ID(), ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(insertUserQuery);
             PreparedStatement accountStmt = conn.prepareStatement(insertAccountQuery)) {

            // Save user
            userStmt.setString(1, name);
            userStmt.setString(2, pin);
            userStmt.executeUpdate();

            // Save account
            accountStmt.setFloat(1, balance);
            accountStmt.executeUpdate();
        }
    }

    // Retrieve user data for PIN validation
    public static boolean validatePin(String pin) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE pin = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the PIN exists
            }
        }
        return false;
    }

    // Retrieve balance for a user
    public static float getBalance(String pin) throws SQLException {
        String query = "SELECT balance FROM BankAccount b JOIN Users u ON b.user_id = u.user_id WHERE u.pin = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("balance");
            }
        }
        return 0.0f;
    }

    // Update balance after deposit/withdrawal
    public static void updateBalance(String pin, float newBalance) throws SQLException {
        String query = "UPDATE BankAccount b JOIN Users u ON b.user_id = u.user_id SET b.balance = ? WHERE u.pin = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setFloat(1, newBalance);
            stmt.setString(2, pin);
            stmt.executeUpdate();
        }
    }
}
