package com.user.models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.ResultSet;

public class ChatModel {
    public UserModel user;
    public String chat_name;
    public ArrayList<String> content;
    public ArrayList<LocalDateTime> timestamp;
    public ArrayList<String> sender_name;
    public ArrayList<Integer> member_id;
    public ArrayList<Boolean> is_sender;
    public Integer chat_id;
    public Boolean is_group;

    public ChatModel() {
        this.user = null;
        this.is_group = false;
        chat_id = 0;
        member_id = new ArrayList<>();
        is_sender = new ArrayList<>();
        timestamp = new ArrayList<>();
        sender_name = new ArrayList<>();
        content = new ArrayList<>();
        chat_name = "";
    }

    public ChatModel(UserModel user, Integer id) {
        this.user = user;
        member_id = new ArrayList<>();
        is_sender = new ArrayList<>();
        timestamp = new ArrayList<>();
        sender_name = new ArrayList<>();
        content = new ArrayList<>();
        chat_id = id;
        String query = """
                    SELECT m.content, m.timestamp, u.username AS sender_name, (m.sender_id = ?) AS is_sender
                    FROM message m
                    JOIN user u ON m.sender_id = u.user_id
                    WHERE m.chat_id = ? AND m.owner_id = ?
                    ORDER BY m.timestamp ASC
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.userID);
            statement.setInt(2, id);
            statement.setInt(3, user.userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    is_sender.add(resultSet.getBoolean("is_sender"));
                    content.add(resultSet.getString("content"));
                    timestamp.add(resultSet.getTimestamp("timestamp").toLocalDateTime());
                    sender_name.add(resultSet.getString("sender_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String getChatName = """
                    SELECT chat_name, is_group
                    FROM chat
                    WHERE chat_id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(getChatName)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    chat_name = resultSet.getString("chat_name");
                    is_group = resultSet.getBoolean("is_group");
                } else {
                    throw new SQLException("No data found for chat_name with ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String getChatMember = """
                    SELECT user_id
                    FROM chat_member
                    WHERE chat_id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(getChatMember)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    member_id.add(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatModel(UserModel user, Integer friend_id, String friend_name) {
        chat_name = friend_name;
        member_id = new ArrayList<>();
        is_sender = new ArrayList<>();
        timestamp = new ArrayList<>();
        sender_name = new ArrayList<>();
        content = new ArrayList<>();
        member_id.add(friend_id);
        member_id.add(user.userID);
        this.user = user;
        this.is_group = false;
        String getChatId = """
                SELECT cm1.chat_id
                FROM chat_member cm1
                JOIN chat_member cm2 ON cm1.chat_id = cm2.chat_id
                JOIN chat c ON cm1.chat_id = c.chat_id
                WHERE c.is_group = FALSE
                  AND cm1.user_id = ?
                  AND cm2.user_id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(getChatId)) {
            statement.setInt(1, user.userID);
            statement.setInt(2, friend_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    chat_id = resultSet.getInt("chat_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = """
                    SELECT m.content, m.timestamp, (m.sender_id = ?) AS is_sender
                    FROM message m
                    WHERE m.chat_id = ? AND m.owner_id = ?
                    ORDER BY m.timestamp ASC
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.userID);
            statement.setInt(2, chat_id);
            statement.setInt(3, user.userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    content.add(resultSet.getString("content"));
                    timestamp.add(resultSet.getTimestamp("timestamp").toLocalDateTime());
                    is_sender.add(resultSet.getBoolean("is_sender"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        String query = """
                    INSERT INTO message (chat_id, sender_id, content, owner_id) VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            for (Integer memberId : member_id) {
                statement.setInt(1, chat_id);
                statement.setInt(2, this.user.userID);
                statement.setString(3, message);
                statement.setInt(4, memberId);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(LocalDateTime time) {
        String query = """
                    DELETE FROM message m
                    WHERE m.owner_id = ? AND m.timestamp = ? AND m.chat_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.userID);
            statement.setTimestamp(2, Timestamp.valueOf(time));
            statement.setInt(3, chat_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllMessage() {
        String query = """
                    DELETE FROM message m
                    WHERE m.owner_id = ? AND m.chat_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.userID);
            statement.setInt(2, chat_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportSpam() {

    }

    public void addMember(Integer new_member_id) {
        member_id.add(new_member_id);
        String query = """
                INSERT INTO chat_member (chat_id, member_id) VALUES (?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chat_id);
            statement.setInt(2, new_member_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMember(Integer remove_member_id) {
        member_id.remove(remove_member_id);
        String query = """
                DELETE FROM chat_member WHERE chat_id = ? AND user_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chat_id);
            statement.setInt(2, remove_member_id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAdmin(Integer new_admin_id) {
        String query = """
                    UPDATE chat_member SET is_admin = 1 WHERE chat_id = ? AND user_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chat_id);
            statement.setInt(2, new_admin_id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeName(String new_name) {
        String query = """
                    UPDATE chat SET chat_name = ? WHERE chat_id = ?;
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, new_name);
            statement.setInt(2, chat_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getListUserId() {
        List<Integer> list = new ArrayList<>();
        String query = """
                    SELECT user_id
                    FROM chat_member
                    WHERE chat_id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chat_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
