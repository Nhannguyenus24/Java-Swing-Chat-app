package com.admin.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupModel {
    public static List<Object[]> getAllGroups() {
        List<Object[]> groups = new ArrayList<>();
        String sql = """
                    SELECT
                        g.group_name,
                        COUNT(m.user_id) AS member_count,
                        g.created_at
                    FROM Chat_Groups g
                    LEFT JOIN Group_Members m ON g.group_id = m.group_id
                    GROUP BY g.group_id
                    ORDER BY g.group_name
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                groups.add(new Object[] {
                        rs.getString("group_name"),
                        rs.getInt("member_count"),
                        rs.getTimestamp("created_at")
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
                        g.group_name,
                        COUNT(m.user_id) AS member_count,
                        g.created_at
                    FROM Chat_Groups g
                    JOIN Group_Members m ON g.group_id = m.group_id
                    WHERE g.group_name LIKE ?
                    GROUP BY g.group_id
                    ORDER BY g.group_name
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    groups.add(new Object[] {
                            rs.getString("group_name"),
                            rs.getInt("member_count"),
                            rs.getTimestamp("created_at")
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
                    SELECT u.username, u.full_name, u.address, u.dob, u.gender, u.email, u.status
                    FROM Group_Members gm
                    INNER JOIN Chat_Groups cg ON gm.group_id = cg.group_id
                    INNER JOIN User u ON gm.user_id = u.user_id
                    WHERE cg.group_name = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName); // Truyền tham số tên nhóm

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    members.add(new Object[] {
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
        return members;
    }

    public static List<Object[]> getAdminsByGroupname(String groupName) {
        List<Object[]> admins = new ArrayList<>();
        String sql = """
                    SELECT u.username, u.full_name, u.address, u.dob, u.gender, u.email, u.status
                    FROM Group_Members gm
                    INNER JOIN Chat_Groups cg ON gm.group_id = cg.group_id
                    INNER JOIN User u ON gm.user_id = u.user_id
                    WHERE cg.group_name = ? AND u.is_admin = TRUE;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName); // Truyền tham số tên nhóm

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    admins.add(new Object[] {
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
        return admins;
    }
}
