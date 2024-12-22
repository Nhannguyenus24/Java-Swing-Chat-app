package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupModel {

    public static List<Object[]> getAllGroups() {
        List<Object[]> groups = new ArrayList<>();
        String sql = """
                    SELECT
                        c.chat_name AS group_name,
                        COUNT(cm.user_id) AS member_count
                    FROM chat c
                    LEFT JOIN chat_member cm ON c.chat_id = cm.chat_id
                    WHERE c.is_group = 1
                    GROUP BY c.chat_id
                    ORDER BY c.chat_name;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                groups.add(new Object[] {
                        rs.getString("group_name"),
                        rs.getInt("member_count"),
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Object[]> searchGroups(String keyword) {
        List<Object[]> groups = new ArrayList<>();
        String sql = """
                    SELECT
                        c.chat_name AS group_name,
                        COUNT(cm.user_id) AS member_count
                    FROM chat c
                    LEFT JOIN chat_member cm ON c.chat_id = cm.chat_id
                    WHERE c.is_group = 1 AND c.chat_name LIKE ?
                    GROUP BY c.chat_id
                    ORDER BY c.chat_name;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    groups.add(new Object[] {
                            rs.getString("group_name"),
                            rs.getInt("member_count"),
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Object[]> getMembersByGroupname(String groupName) {
        List<Object[]> members = new ArrayList<>();
        String sql = """
                    SELECT
                        u.username,
                        u.full_name,
                        u.address,
                        u.dob,
                        u.gender,
                        u.email,
                        u.status
                    FROM chat_member cm
                    INNER JOIN chat c ON cm.chat_id = c.chat_id
                    INNER JOIN user u ON cm.user_id = u.user_id
                    WHERE c.chat_name = ? AND c.is_group = 1;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    members.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("address"),
                            rs.getDate("dob"),
                            rs.getString("gender"),
                            rs.getString("email"),
                            rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static List<Object[]> getAdminsByGroupname(String groupName) {
        List<Object[]> admins = new ArrayList<>();
        String sql = """
                    SELECT
                        u.username,
                        u.full_name,
                        u.address,
                        u.dob,
                        u.gender,
                        u.email,
                        u.status
                    FROM chat_member cm
                    INNER JOIN chat c ON cm.chat_id = c.chat_id
                    INNER JOIN user u ON cm.user_id = u.user_id
                    WHERE c.chat_name = ? AND c.is_group = 1 AND cm.is_admin = 1;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    admins.add(new Object[] {
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("address"),
                            rs.getDate("dob"),
                            rs.getString("gender"),
                            rs.getString("email"),
                            rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
