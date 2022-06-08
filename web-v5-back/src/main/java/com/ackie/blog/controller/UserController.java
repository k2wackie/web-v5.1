package com.ackie.blog.controller;

import com.ackie.blog.model.User;
import com.ackie.blog.repository.UserRepository;
import com.ackie.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    private final UserService userService;

    @CrossOrigin("*")
    @PostMapping("/user/register")
    public User newUser(@RequestBody User user) {
        return userService.save(user);
    }

//    @CrossOrigin("*")
//    @PostMapping("/user/login")
//    public String login() {
////    public User login(@RequestBody User user) {
////        return repository.findByUsername(user.getUsername());
//        return "user/login";
//    }
//    @CrossOrigin("*")
//    @PostMapping("/user/logout")
//    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//    }

    @CrossOrigin("*")
    @GetMapping("/user")
    public List<User> all() {
        return repository.findAll();
    }
    @CrossOrigin("*")
    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
