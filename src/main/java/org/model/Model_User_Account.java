package org.model;

public class Model_User_Account {

    public String getUserName() {
        return userName;
    }
    public boolean isStatus() {
        return status;
    }
    public Model_User_Account(int userID, String userName, String gender, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.status = status;
    }

    private int userID;
    private String userName;
    private String gender;
    private boolean status;
}
