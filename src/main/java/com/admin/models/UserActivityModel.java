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
                        u.created_at,
                        COUNT(CASE WHEN ua.activity_type = 'app open' THEN 1 END) AS app_open_count,
                        COUNT(CASE WHEN ua.activity_type = 'chat' THEN 1 END) AS chat_count,
                        COUNT(CASE WHEN ua.activity_type = 'group join' THEN 1 END) AS group_join_count,
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
                        rs.getTimestamp("created_at"),
                        rs.getInt("app_open_count"),
                        rs.getInt("chat_count"),
                        rs.getInt("group_join_count"),
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
                        u.created_at,
                        COUNT(CASE WHEN ua.activity_type = 'app open' THEN 1 END) AS app_open_count,
                        COUNT(CASE WHEN ua.activity_type = 'chat' THEN 1 END) AS chat_count,
                        COUNT(CASE WHEN ua.activity_type = 'group join' THEN 1 END) AS group_join_count,
                        COUNT(DISTINCT ua.activity_id) AS total_activities
                    FROM User u
                    LEFT JOIN User_Activity ua ON u.user_id = ua.user_id
                    WHERE u.username LIKE ? AND ua.activity_date BETWEEN ? AND ?
                    GROUP BY u.user_id
                """;

        String sql = baseSql;
        switch (comparison) {
            case "=":
                sql += " HAVING total_activities = ?";
                break;
            case "<":
                sql += " HAVING total_activities < ?";
                break;
            case ">":
                sql += " HAVING total_activities > ?";
                break;
            default:
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
                            rs.getTimestamp("created_at"),
                            rs.getInt("app_open_count"),
                            rs.getInt("chat_count"),
                            rs.getInt("group_join_count"),
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
                SELECT MONTH(ua.activity_date) AS month,
                       COUNT(DISTINCT ua.activity_id) AS total_activities
                FROM User_Activity ua
                WHERE YEAR(ua.activity_date) = ?
                GROUP BY MONTH(ua.activity_date)
                ORDER BY month;
            """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    activityData.put(
                            rs.getInt("month"),
                            rs.getInt("total_activities"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activityData;
    }
}
