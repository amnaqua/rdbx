package com.boots.controller;

import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerController {
    @Autowired
    private UserService userService;

    @GetMapping("/manager")
    public String userList(Model model) {
        model.addAttribute("managerUser", userService.managerUsers());
        return "manager";
    }

    @PostMapping("/manager/addVisit")
    public String addVisit(@RequestParam Long userId,
                              Model model) {
        try {
            userService.addVisit(userId);
            return "redirect:/manager";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/manager";
        }
    }

    @GetMapping("/manager/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("managerUser", userService.usergtList(userId));
        return "manager";
    }
}
