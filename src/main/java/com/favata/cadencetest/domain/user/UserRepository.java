package com.favata.cadencetest.domain.user;

import com.favata.cadencetest.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    static class UserStorage {
        private final HashMap<String, User> idMap = new HashMap<>();
    }

    private final UserStorage userStorage = new UserStorage();

    public User CreateUser (UserDto userDto) {
        String newId = UUID.randomUUID().toString();
        User newUser = new User(newId, userDto.getName());
        userStorage.idMap.put(newId, newUser);
        return newUser;
    }

    public User GetUserById (String id) {
        return userStorage.idMap.get(id);
    }

    public List<User> GetUsers () {
        return new ArrayList<>(userStorage.idMap.values());
    }

    public User DeleteUser (String id) {
        User oldUser = new User(userStorage.idMap.get(id));
        userStorage.idMap.remove(id);
        return oldUser;
    }
}
