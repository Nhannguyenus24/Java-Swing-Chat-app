package com.user.events;

import com.user.models.UserModel;
public interface EventChat {
    void onItemClicked(UserModel user);
    void onItemClicked(Integer groupid);
}
