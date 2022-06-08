package com.ackie.blog.controller;

import com.ackie.blog.model.User;
import com.ackie.blog.repository.UserRepository;
import com.ackie.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    @PostMapping("/user/register")
    public User newUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/user")
    public List<User> all() {
        return repository.findAll();
    }

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
