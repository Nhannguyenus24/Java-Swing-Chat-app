package com.user.models;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.SecureRandom;
import com.user.Services.EmailSender;
import java.time.LocalDate;

public class UserModel {
    public int userID;
    public String userName;
    public String email;
    public boolean status;
    public boolean isAdmin;
    public String fullname;
    public LocalDate dob;
    public String gender;
    public String address;

    public UserModel(int userID) {
        String query= """
            SELECT user_id, username, email, status, is_admin, full_name, dob, gender, address
            FROM user
            WHERE user_id= ?
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()){
                        this.userID = resultSet.getInt("user_id");
                        this.userName = resultSet.getString("username");
                        this.email = resultSet.getString("email");
                        this.status = resultSet.getString("status").equals("Inactive");
                        this.isAdmin = resultSet.getBoolean("is_admin");
                        this.fullname = resultSet.getString("full_name");
                        this.address = resultSet.getString("address");
                        this.gender = resultSet.getString("gender");
                        this.dob = resultSet.getDate("dob").toLocalDate();
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserModel> getFriends() {
        List<UserModel> friends = new ArrayList<>();
        String query = """
            SELECT u.user_id
            FROM friends f
            JOIN user u ON (f.friend_id = u.user_id AND f.user_id = ?)
            WHERE f.block = 0 AND f.is_block = 0;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.userID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel friend = new UserModel(
                            resultSet.getInt("user_id")
                    );
                    friends.add(friend);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public List<UserModel> getMemberGroup(Integer chatId) {
        List<UserModel> members = new ArrayList<>();
        String query = """
            SELECT user_id
            FROM chat_member
            WHERE chat_id = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, chatId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel member = new UserModel(resultSet.getInt("user_id"));
                    members.add(member);
                }
            }
        } catch( SQLException e){
            e.printStackTrace();
        }
        return members;
    }

    public Boolean isAdminGroup(Integer chatId) {
        String query = """
        SELECT is_admin
        FROM chat_member
        WHERE chat_id = ? AND user_id = ?
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, chatId);
            statement.setInt(2, this.userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_admin");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<UserModel> getFriendRequests() {
        List<UserModel> friendRequests = new ArrayList<>();
        String query = """
        SELECT u.user_id, u.username, u.email, u.status = 'Active' AS status, u.is_admin
        FROM Friend_request fr
        JOIN user u ON fr.user_request_id = u.user_id
        WHERE fr.user_accept_id = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel requester = new UserModel(
                            resultSet.getInt("user_id")
                    );
                    friendRequests.add(requester);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    public List<UserModel> getStrangers() {
        List<UserModel> strangers = new ArrayList<>();
        String query = """
        SELECT u.user_id, u.username, u.email, u.status = 'Active' AS status, u.is_admin
        FROM user u
        WHERE u.user_id != ?
        AND NOT EXISTS (
            SELECT 1
            FROM friends f
            WHERE (f.user_id = ? AND f.friend_id = u.user_id) 
               OR (f.user_id = u.user_id AND f.friend_id = ?)
        )
        AND NOT EXISTS (
            SELECT 1
            FROM Friend_request fr
            WHERE fr.user_request_id = u.user_id AND fr.user_accept_id = ?
        )
        AND NOT EXISTS (
            SELECT 1
            FROM Friend_request fr
            WHERE fr.user_request_id = ? AND fr.user_accept_id = u.user_id
        );
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.userID);
            preparedStatement.setInt(2, this.userID);
            preparedStatement.setInt(3, this.userID);
            preparedStatement.setInt(4, this.userID);
            preparedStatement.setInt(5, this.userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel stranger = new UserModel(
                            resultSet.getInt("user_id")
                    );
                    strangers.add(stranger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strangers;
    }

    public void sendFriendRequest(int recipientUserID) {
        String query = """
        INSERT INTO Friend_request (user_request_id, user_accept_id)
        VALUES (?, ?)
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.userID);
            statement.setInt(2, recipientUserID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unFriend(int friendId) {
        String query = """
        DELETE FROM friends 
        WHERE (user_id = ? AND friend_id = ?)
           OR (user_id = ? AND friend_id = ?);
    """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, this.userID);
            statement.setInt(2, friendId);
            statement.setInt(3, friendId);
            statement.setInt(4, this.userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void blockFriend(int friendId, Boolean is_block) {
        String query = """
        UPDATE friends 
        SET block = ?
        WHERE (user_id = ? AND friend_id = ?);
    """;
        String query2 = """
        UPDATE friends 
        SET is_block = ?
        WHERE (user_id = ? AND friend_id = ?);
       """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, is_block);
            statement.setInt(2, this.userID);
            statement.setInt(3, friendId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query2)) {
            statement.setBoolean(1, is_block);
            statement.setInt(2, friendId);
            statement.setInt(3, this.userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserModel> getListBlock(){
        List<UserModel> list = new ArrayList<>();
        String query = """
            SELECT u.user_id
            FROM friends f
            JOIN user u ON (f.friend_id = u.user_id AND f.user_id = ?)
            WHERE f.block = 1 AND f.is_block = 0;
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.userID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel block = new UserModel(
                            resultSet.getInt("user_id")
                    );
                    list.add(block);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        String query = """
        UPDATE user
        SET password = ?
        WHERE user_id = ? AND password = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userID);
            statement.setString(3, oldPassword);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserInfo(int userID, String username, String fullName, String address, String dob, String gender, String email) {
        String query = """
        UPDATE user
        SET username = ?, full_name = ?, address = ?, dob = ?, gender = ?, email = ?
        WHERE user_id = ?
    """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, fullName);
            statement.setString(3, address);
            statement.setString(4, dob);
            statement.setString(5, gender);
            statement.setString(6, email);
            statement.setInt(7, userID);
            if (statement.executeUpdate() > 0){
                this.userName = username;
                this.fullname = fullName;
                this.address = address;
                this.gender = gender;
                this.email = email;
                this.dob = LocalDate.parse(dob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void acceptInvite(int inviteUserID) {
        String insertFriendQuery = """
        INSERT INTO friends (user_id, friend_id, created_at)
        VALUES (?, ?, CURRENT_TIMESTAMP)
    """;

        String deleteInviteQuery = """
        DELETE FROM Friend_request
        WHERE user_request_id = ? AND user_accept_id = ?
    """;
        String insertChatQuery = """
        INSERT INTO chat (is_group, chat_name)
        VALUES (false, "")
    """;

        String insertChatMemberQuery = """
        INSERT INTO chat_member (chat_id, user_id)
        VALUES (?, ?)
    """;
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertFriendQuery)) {
                statement.setInt(1, this.userID);
                statement.setInt(2, inviteUserID);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(insertFriendQuery)) {
                statement.setInt(1, inviteUserID);
                statement.setInt(2, this.userID);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(deleteInviteQuery)) {
                statement.setInt(1, inviteUserID);
                statement.setInt(2, this.userID);
                statement.executeUpdate();
            }
            int chatId = -1;
            try (PreparedStatement statement = connection.prepareStatement(insertChatQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chatId = generatedKeys.getInt(1);
                    }
                }
            }
            if (chatId != -1) {
                try (PreparedStatement statement = connection.prepareStatement(insertChatMemberQuery)) {
                    statement.setInt(1, chatId);
                    statement.setInt(2, this.userID);
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(insertChatMemberQuery)) {
                    statement.setInt(1, chatId);
                    statement.setInt(2, inviteUserID);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rejectInvite(int inviteUserID) {
        String query = """
        DELETE FROM Friend_request
        WHERE user_request_id = ? AND user_accept_id = ?
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, inviteUserID);
            statement.setInt(2, this.userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public void createRandomPassword() {
        String password = generateRandomPassword(20);

        String updatePasswordQuery = """
        UPDATE user
        SET password = ?
        WHERE user_id = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updatePasswordQuery)) {
            statement.setString(1, password);
            statement.setInt(2, this.userID);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                EmailSender.sendEmail("YOUR NEW PASSWORD FROM CHATAPP", "This is your new random password: " + password, this.email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGroup(String groupName, List<String> members) {
        String createGroup = """
        INSERT INTO chat (is_group, chat_name) VALUES (1, ?)
    """;

        String memberIdsQuery = """
        SELECT user_id FROM user WHERE username = ?
    """;

        String addMembers = """
        INSERT INTO chat_member (chat_id, user_id, is_admin) VALUES (?, ?, ?)
    """;

        int chatId = -1;

        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createGroup, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, groupName);
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chatId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating group failed, no chat ID obtained.");
                    }
                }
            }

            // Step 2: Retrieve user IDs of all members based on their usernames
            List<Integer> userIds = new ArrayList<>();
            userIds.add(userID);
            try (PreparedStatement statement = connection.prepareStatement(memberIdsQuery)) {
                for (String member : members) {
                    statement.setString(1, member);
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            userIds.add(rs.getInt("user_id"));
                        } else {
                            System.err.println("User not found: " + member);
                        }
                    }
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(addMembers)) {
                for (int i = 0; i < userIds.size(); i++) {
                    int userId = userIds.get(i);
                    statement.setInt(1, chatId);
                    statement.setInt(2, userId);
                    statement.setBoolean(3, i == 0); // First member is the admin
                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getGroupName() {
        List<String> groupNames = new ArrayList<>();
        String query = """
            SELECT c.chat_name
            FROM chat c
            INNER JOIN chat_member cm ON c.chat_id = cm.chat_id
            WHERE c.is_group = 1 AND cm.user_id = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groupNames.add(resultSet.getString("chat_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupNames;
    }

    public List<Integer> getGroupId() {
        List<Integer> ids = new ArrayList<>();
        String query = """
            SELECT c.chat_id
            FROM chat c
            INNER JOIN chat_member cm ON c.chat_id = cm.chat_id
            WHERE c.is_group = 1 AND cm.user_id = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getInt("chat_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    public void setAdmin(Integer chatID, Integer user, Integer mode) {
        String query = """
        UPDATE chat_member 
        SET is_admin = ?
        WHERE chat_id = ? AND user_id = ?
    """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mode);
            statement.setInt(2, chatID);
            statement.setInt(3, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeGroupName(Integer chatID, String groupName) {
        String query = """
        UPDATE chat 
        SET chat_name = ?
        WHERE chat_id = ?
    """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, groupName);
            statement.setInt(2, chatID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember(Integer chatID, Integer user){
        String query = """
            INSERT INTO chat_member (chat_id, user_id, is_admin) 
            VALUES (?, ?, 0)
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chatID);
            statement.setInt(2, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMember(Integer chatID, Integer user) {
        String query = """
        DELETE FROM chat_member 
        WHERE chat_id = ? AND user_id = ?
    """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chatID);
            statement.setInt(2, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void outGroup(Integer chatID) {
        String query = """
            DELETE FROM chat_member 
            WHERE chat_id = ? AND user_id = ?
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chatID);
            statement.setInt(2, this.userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportSpam(Integer friendId){
        String query = """
           INSERT INTO spam_reports (user_id, reason)
           VALUES (?, ?)
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, friendId);
            statement.setString(2, "spam message");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
