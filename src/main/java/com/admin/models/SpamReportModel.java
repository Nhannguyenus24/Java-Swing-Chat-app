package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpamReportModel {
    public static List<Object[]> getAllReports() {
        List<Object[]> reports = new ArrayList<>();
        String sql = """
                    SELECT u.username, u.full_name, sr.report_time, sr.reason
                    FROM Spam_Reports sr
                    INNER JOIN User u ON sr.user_id = u.user_id
                    ORDER BY sr.report_time DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reports.add(new Object[] {
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getTimestamp("report_time"),
                        rs.getString("reason")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static List<Object[]> filterReports(String username, Date startDate, Date endDate) {
        List<Object[]> reports = new ArrayList<>();
        String sql = """
                    SELECT u.username, u.full_name, sr.report_time, sr.reason
                    FROM Spam_Reports sr
                    INNER JOIN User u ON sr.user_id = u.user_id
                    WHERE (u.full_name LIKE ? OR ? IS NULL)
                    AND sr.report_time BETWEEN ? AND ?
                    ORDER BY sr.report_time DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + username + "%");
            stmt.setString(2, username.isEmpty() ? null : username);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reports.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getTimestamp("report_time"),
                            rs.getString("reason")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static boolean blockUser(String username) {
        String sql = "UPDATE User SET status = 'Locked' WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
