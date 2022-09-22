package com.example.travelservice.controllers;

import com.example.travelservice.models.User;
import com.example.travelservice.repos.UserRepository;
import com.example.travelservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/profile")
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/load-docs")
    public String loadDocs(@AuthenticationPrincipal User user,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam String docs,
                           Model model) throws IOException {

        userService.loadDocs(user, file, docs);

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String update(@AuthenticationPrincipal User user,
                         @RequestParam String email,
                         @RequestParam String firstName,
                         @RequestParam String secondName,
                         @RequestParam String studentCard,
                         @RequestParam String benefitCard,
                         Model model) {

        if (!user.getEmail().equals(email)) {
            userService.updateEmail(user, email);

            model.addAttribute("email", email);
            return "activation";
        }
        userService.updateUser(user, firstName, secondName, studentCard, benefitCard);

        model.addAttribute(user);
        return "profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Model model) {

        if (userService.changePassword(user, oldPassword, newPassword)) {
            model.addAttribute("success", "Пароль успішно змінено!");
        } else {
            model.addAttribute("failure", "Старий пароль введено неправильно!");
        }

        model.addAttribute("user", user);
        return "profile";
    }
}
