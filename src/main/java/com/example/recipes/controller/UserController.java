package com.example.recipes.controller;

import com.example.recipes.model.User;
import com.example.recipes.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public String findUserById(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userProfile";
    }
}
