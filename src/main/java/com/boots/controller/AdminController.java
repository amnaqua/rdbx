package com.boots.controller;

import com.boots.entity.User;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());

        return "admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(required = false) Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@RequestParam(required = false) Long userId,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String phoneNumber,
                             @RequestParam Double purchaseSum,
                             @RequestParam Integer visitsCount,
                             @RequestParam String roles,
                             Model model) {
        try {
            User user = new User(username, password, phoneNumber, purchaseSum, visitsCount);
            userService.addUser(user, roles);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("paramError", "Неверно введены данные пользователя");
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/update")
    public String updateUser(@RequestParam Long userId,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String password,
                          @RequestParam(required = false) String phoneNumber,
                          @RequestParam(required = false) Double purchaseSum,
                          @RequestParam(required = false) Integer visitsCount,
                          @RequestParam(required = false) String roles,
                          Model model) {
        try {
            userService.updateUser(userId, username, password, phoneNumber, purchaseSum, visitsCount, roles);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}
