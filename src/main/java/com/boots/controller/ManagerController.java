package com.boots.controller;

import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ManagerController {
    @Autowired
    private UserService userService;

    @GetMapping("/manager")
    public String userList(Model model) {
        model.addAttribute("managerUser", userService.managerUsers());
        model.addAttribute("allPurchase", userService.purchasegtList());
        return "manager";
    }

    @PostMapping("/manager/addVisit")
    public String addVisit(@RequestParam Long userId, Double amount, Model model) {
        try {
            userService.addVisit(userId, amount);
            return "redirect:/manager";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/manager";
        }
    }
}
