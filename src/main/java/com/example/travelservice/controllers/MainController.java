package com.example.travelservice.controllers;

import com.example.travelservice.models.User;
import com.example.travelservice.models.dto.CaptchaResponseDto;
import com.example.travelservice.repos.UserRepository;
import com.example.travelservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/")
    public String getHome(Model model) {

        return "home";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@RequestParam("g-recaptcha-response") String captchaResponse,
                           @Valid User user,
                           BindingResult bindingResult,
                           Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()) {
            model.addAttribute("captchaError", "Поставте прапорець, щоб продовжити");
        }

        if(bindingResult.hasErrors() ||!response.isSuccess()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            return "registration";

        } else {
            if (!userService.addUser(user)) {
                model.addAttribute("failure", "Користувач з вказаною електронною поштою вже існує!");
                return "registration";
            }

            model.addAttribute("email", user.getEmail());
            return "activation";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("success", "Користувач був успішно активований! Ввійдіть в обліковий запис");
        } else {
            model.addAttribute("failure", "Код активації не знайдено!");
        }

        return "login";
    }

    @GetMapping("/forgot-password/email")
    public String getEnterEmail(Model model) {

        return "enter_email";
    }

    @PostMapping("/forgot-password/instr")
    public String sendInstructions(@RequestParam String userEmail,
                                 Model model) {
        boolean isSent = userService.sendPasswordInstructions(userEmail);
        if (isSent) {
            model.addAttribute("success", "Повідомлення відправлено! Перевірте скриньку");
        } else {
            model.addAttribute("failure", "Користувача з вказаною поштою не знайдено!");
        }

        return "enter_email";
    }

    @GetMapping("/forgot-password/{id}")
    public String getNewPassword(@PathVariable Long id,
                                 Model model) {
        model.addAttribute("id", id);

        return "new_password";
    }

    @PostMapping("/forgot-password/{id}")
    public String restorePassword(@AuthenticationPrincipal User user,
                                  @PathVariable Long id,
                                  @RequestParam String password,
                                  Model model) {
        if (userService.setNewPassword(id, password)){
            model.addAttribute("success", "Пароль успішно відновлено!");
        } else {
            model.addAttribute("failure", "Користувач не знайдений!");
        }

        if(user != null) {
            model.addAttribute("user", user);
            return "profile";
        } else {
            return "login";
        }

    }
}
