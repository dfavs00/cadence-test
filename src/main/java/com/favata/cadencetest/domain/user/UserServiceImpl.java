package com.favata.cadencetest.domain.user;

import com.favata.cadencetest.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User CreateUser(UserDto userDto) {
        return userRepository.CreateUser(userDto);
    }

    @Override
    public User GetUser(String id) {
        return userRepository.GetUserById(id);
    }

    @Override
    public List<User> GetUsers() {
        return userRepository.GetUsers();
    }

    @Override
    public User DeleteUser(String id) {
        return userRepository.DeleteUser(id);
    }
}
