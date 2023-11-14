package com.boots.controller;

import com.boots.entity.PasswordResetToken;
import com.boots.entity.User;
import com.boots.service.PasswordResetTokenService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/resetPassword")
    public String purchase(Model model) {
        model.addAttribute("resetPassword", "Форма восстановления пароля");
        return "resetPassword";
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("email") String email) {
        User user = userService.findUserByUsername(email);
        if (user == null) {
            return "redirect:/login/resetPassword?error=userNotFound";
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(user);
        passwordResetTokenService.sendPasswordResetEmail(passwordResetToken);

        return "redirect:/passwordResetConfirmation";
    }
}
