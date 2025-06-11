package service;

import domain.User;
import utility.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserService() {
    }

    public static void createUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName) VALUES (?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    public static List<User> readUsers() {
        String sql = "SELECT userId, firstName, lastName FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                ));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error reading users", e);
        }
    }

    public static void updateUser(User user) {
        String sql = "UPDATE users SET firstName = ?, lastName = ? WHERE userId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setInt(3, user.getUserId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("No user updated; ID not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public static void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE userId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("No user deleted; ID not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
