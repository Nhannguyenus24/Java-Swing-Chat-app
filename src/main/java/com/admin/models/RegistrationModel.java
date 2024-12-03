package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationModel {

    public static List<Object[]> getAllRegistrations() {
        List<Object[]> registrations = new ArrayList<>();
        String sql = """
                    SELECT u.username, u.full_name, u.created_at
                    FROM User u
                    ORDER BY u.created_at DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registrations.add(new Object[] {
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getTimestamp("created_at")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public static List<Object[]> filterRegistrations(String keyword, Date startDate, Date endDate) {
        List<Object[]> results = new ArrayList<>();
        String sql = """
                    SELECT u.username, u.full_name, u.created_at
                    FROM User u
                    WHERE (u.full_name LIKE ? OR ? IS NULL)
                    AND u.created_at BETWEEN ? AND ?
                    ORDER BY u.created_at DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, keyword.isEmpty() ? null : keyword);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getTimestamp("created_at")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static Map<Integer, Integer> getMonthlyRegistrationsByYear(int year) {
        Map<Integer, Integer> registrations = new HashMap<>();
        String sql = """
                    SELECT MONTH(created_at) AS month, COUNT(*) AS total
                    FROM User
                    WHERE YEAR(created_at) = ?
                    GROUP BY MONTH(created_at)
                    ORDER BY month
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registrations.put(rs.getInt("month"), rs.getInt("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrations;
    }
}
