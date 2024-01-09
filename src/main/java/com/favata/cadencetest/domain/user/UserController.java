package com.favata.cadencetest.domain.user;

import com.favata.cadencetest.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> CreateUser(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.CreateUser(userDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetUser(@PathVariable("id") String id) {
        try {
            User user = userService.GetUser(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> GetUsers() {
        try {
            return ResponseEntity.ok(userService.GetUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> DeleteUser(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(userService.DeleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
