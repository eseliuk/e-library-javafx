package com.stormnet.net.server.dao;

import com.stormnet.net.data.users.User;

import java.util.List;

public interface UserDao {

    List<User> readAllUsers();

    User readUser(Long id);

    Long saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);


}
