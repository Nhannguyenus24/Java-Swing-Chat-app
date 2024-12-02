package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public static List<Object[]> getAllUsers() {
        String sql = "SELECT username, full_name, address, dob, gender, email, status FROM user";
        List<Object[]> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new Object[] {
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("address"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean addUser(String username, String fullName, String address, String dob, String gender,
            String status, String email, String password) {
        String sql = "INSERT INTO user (username, full_name, address, dob, gender, status, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, fullName);
            stmt.setString(3, address);
            stmt.setString(4, dob);
            stmt.setString(5, gender);
            stmt.setString(6, status);
            stmt.setString(7, email);
            stmt.setString(8, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(String username, String fullName, String address, String dob, String gender,
            String email) {
        String sql = "UPDATE user SET full_name = ?, address = ?, dob = ?, gender = ?, email = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, address);
            stmt.setString(3, dob);
            stmt.setString(4, gender);
            stmt.setString(5, email);
            stmt.setString(6, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean toggleLockUnlockStatus(String username, String currentStatus) {
        String sql = "UPDATE user SET status = 'Active' WHERE username = ?";
        if (!"Locked".equals(currentStatus)) {
            sql = "UPDATE user SET status = 'Locked' WHERE username = ?";
        }
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean resetPassword(String username, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPasswordByUsername(String username) {
        String sql = "SELECT password FROM user WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object[]> searchUsers(String criteria, String keyword) {
        String sql = "SELECT username, full_name, address, dob, gender, email, status FROM user WHERE ";
        if (criteria.equals("Tên đăng nhập"))
            sql += "username LIKE ?";
        else if (criteria.equals("Họ tên"))
            sql += "full_name LIKE ?";
        else if (criteria.equals("Trạng thái"))
            sql += "status LIKE ?";
        else
            return new ArrayList<>();

        List<Object[]> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("address"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("email"),
                            rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Object[]> getLoginHistoryByUsername(String username) {
        String sql = """
                SELECT lh.login_time
                FROM login_history lh
                JOIN user u ON lh.user_id = u.user_id
                WHERE u.username = ?
                ORDER BY lh.login_time DESC
                """;
        List<Object[]> loginHistory = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loginHistory.add(new Object[] { rs.getString("login_time") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginHistory;
    }

    public static List<Object[]> getFriendListByUsername(String username) {
        String sql = """
                SELECT u2.username, u2.full_name, u2.address, u2.dob, u2.gender, u2.email, u2.status
                FROM Friends f
                JOIN user u1 ON f.user_id = u1.user_id
                JOIN user u2 ON f.friend_id = u2.user_id
                WHERE u1.username = ?
                """;
        List<Object[]> friends = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    friends.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("address"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("email"),
                            rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static List<Object[]> getAllLoginHistory() {
        List<Object[]> loginHistory = new ArrayList<>();
        String sql = """
                SELECT lh.login_time, u.username, u.full_name
                FROM login_history lh
                JOIN user u ON lh.user_id = u.user_id
                ORDER BY lh.login_time DESC
                """;
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                loginHistory.add(new Object[] {
                        rs.getTimestamp("login_time"),
                        rs.getString("username"),
                        rs.getString("full_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginHistory;
    }

}
