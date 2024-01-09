package com.favata.cadencetest.domain.user;


import com.favata.cadencetest.domain.entity.User;

import java.util.List;

public interface UserService {
    User CreateUser(UserDto userDto);
    User GetUser(String id);
    List<User> GetUsers();
    User DeleteUser(String id);
}
