package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivityModel {

    public static List<Object[]> getAllUserActivities() {
        List<Object[]> userActivities = new ArrayList<>();
        String sql = """
                    SELECT
                        u.username,
                        u.full_name,
                        COUNT(DISTINCT ua.activity_id) AS total_activities
                    FROM User u
                    LEFT JOIN User_Activity ua ON u.user_id = ua.user_id
                    GROUP BY u.user_id
                    ORDER BY u.username
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                userActivities.add(new Object[] {
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getInt("total_activities")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActivities;
    }

    public static List<Object[]> filterUserActivity(String name, String startDate, String endDate, int activityCount,
            String comparison) {
        List<Object[]> userActivities = new ArrayList<>();
        String baseSql = """
                    SELECT
                        u.username,
                        u.full_name,
                        COUNT(DISTINCT ua.activity_id) AS total_activities
                    FROM User u
                    LEFT JOIN User_Activity ua ON u.user_id = ua.user_id
                    WHERE u.username LIKE ? AND ua.activity_time BETWEEN ? AND ?
                    GROUP BY u.user_id
                """;

        String sql = baseSql;
        if (comparison.equals("=")) {
            sql += " HAVING total_activities = ?";
        } else if (comparison.equals("<")) {
            sql += " HAVING total_activities < ?";
        } else if (comparison.equals(">")) {
            sql += " HAVING total_activities > ?";
        } else {
            throw new IllegalArgumentException("Invalid comparison operator: " + comparison);
        }

        sql += " ORDER BY u.username";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            stmt.setInt(4, activityCount);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userActivities.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getInt("total_activities")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActivities;
    }

    public static Map<Integer, Integer> getMonthlyUserActivityByYear(int year) {
        Map<Integer, Integer> activityData = new HashMap<>();
        String sql = """
                    SELECT MONTH(u.created_at) AS month,
                           COUNT(DISTINCT u.user_id) AS user_count
                    FROM User u
                    WHERE YEAR(u.created_at) = ?
                    GROUP BY MONTH(u.created_at)
                    ORDER BY month;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    activityData.put(
                            rs.getInt("month"),
                            rs.getInt("user_count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activityData;
    }
}
