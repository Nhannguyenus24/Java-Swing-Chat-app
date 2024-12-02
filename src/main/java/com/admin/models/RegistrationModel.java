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
                    SELECT u.username, u.full_name, ur.registration_time
                    FROM User_Registrations ur
                    INNER JOIN User u ON ur.user_id = u.user_id
                    ORDER BY ur.registration_time DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registrations.add(new Object[] {
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getTimestamp("registration_time")
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
                    SELECT u.username, u.full_name, ur.registration_time
                    FROM User_Registrations ur
                    INNER JOIN User u ON ur.user_id = u.user_id
                    WHERE (u.full_name LIKE ? OR ? IS NULL)
                    AND ur.registration_time BETWEEN ? AND ?
                    ORDER BY ur.registration_time DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán tham số cho câu lệnh SQL
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, keyword.isEmpty() ? null : keyword);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getTimestamp("registration_time")
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
                    SELECT MONTH(registration_time) AS month, COUNT(*) AS total
                    FROM User_Registrations
                    WHERE YEAR(registration_time) = ?
                    GROUP BY MONTH(registration_time)
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
