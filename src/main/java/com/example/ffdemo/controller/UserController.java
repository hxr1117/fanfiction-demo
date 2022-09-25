package com.example.ffdemo.controller;

import com.example.ffdemo.dto.UserDto;
import com.example.ffdemo.model.User;
import com.example.ffdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto) {
        userService.saveUser(userDto);
        System.out.println("Created a new user: " + userDto.getUsername());
        return "redirect:/registration?success";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String checkPassword(@ModelAttribute("user") UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByEmail(userDto.getEmail());

        if (Objects.equals(user.getPassword(), userDto.getPassword())) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("username", user.getUsername());

            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/adminLogin")
    public String adminPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    // Todo
    @PostMapping("/adminLogin")
    public String adminSubmit(@ModelAttribute("user") UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByEmail(userDto.getEmail());

        // TODO: Probably there are some problems
        if (Objects.equals(user.getPassword(), userDto.getPassword())) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("username", user.getUsername());
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("userId", "");
        request.getSession().setAttribute("username", "");
        return "redirect:/";
    }
}
