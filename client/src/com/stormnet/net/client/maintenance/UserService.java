package com.stormnet.net.client.maintenance;

import com.stormnet.net.data.users.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers() ;

    User readUserById(Long userId);

    Long saveUser(User user);

    void delUser(Long userId);

}
