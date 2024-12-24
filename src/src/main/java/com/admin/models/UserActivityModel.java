package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivityModel {

    // Lấy danh sách tất cả các hoạt động đăng nhập của người dùng
    public static List<Object[]> getAllUserActivities() {
        List<Object[]> userActivities = new ArrayList<>();
        String sql = """
                    SELECT
                        u.username,
                        u.full_name,
                        u.created_at,
                        COUNT(lh.login_time) AS login_count
                    FROM User u
                    LEFT JOIN login_history lh ON u.user_id = lh.user_id
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
                        rs.getInt("login_count")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActivities;
    }

    // Lọc hoạt động người dùng dựa trên số lần đăng nhập
    public static List<Object[]> filterUserActivity(String name, String startDate, String endDate, int loginCount,
            String comparison) {
        List<Object[]> userActivities = new ArrayList<>();
        String baseSql = """
                    SELECT
                        u.username,
                        u.full_name,
                        u.created_at,
                        COUNT(lh.login_time) AS login_count
                    FROM User u
                    LEFT JOIN login_history lh ON u.user_id = lh.user_id
                    WHERE u.username LIKE ? AND lh.login_time BETWEEN ? AND ?
                    GROUP BY u.user_id
                """;

        String sql = baseSql;
        switch (comparison) {
            case "=":
                sql += " HAVING login_count = ?";
                break;
            case "<":
                sql += " HAVING login_count < ?";
                break;
            case ">":
                sql += " HAVING login_count > ?";
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
            stmt.setInt(4, loginCount);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userActivities.add(new Object[] {
                            rs.getString("username"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("login_count")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActivities;
    }

    // Lấy dữ liệu hoạt động theo tháng cho năm cụ thể chỉ tính số lần đăng nhập
    public static Map<Integer, Integer> getMonthlyUserActivityByYear(int year) {
        Map<Integer, Integer> activityData = new HashMap<>();
        String sql = """
                    SELECT MONTH(lh.login_time) AS month,
                           COUNT(lh.login_time) AS login_count
                    FROM login_history lh
                    WHERE YEAR(lh.login_time) = ?
                    GROUP BY MONTH(lh.login_time)
                    ORDER BY month;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    activityData.put(
                            rs.getInt("month"),
                            rs.getInt("login_count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activityData;
    }
}
