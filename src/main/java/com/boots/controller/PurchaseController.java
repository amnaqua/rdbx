package com.boots.controller;

import com.boots.entity.Purchase;
import com.boots.repository.PurchaseRepository;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PurchaseController {
    @Autowired
    UserService userService;
    @Autowired
    PurchaseRepository purchaseRepository;

    @GetMapping("/purchase")
    public String purchase(Model model) {
        model.addAttribute("purchase", "Форма покупки");
        return "purchase";
    }

    @PostMapping("/purchase")
    public String purchase(@RequestParam Double amount,
                           @RequestParam String qrCode,
                           Model model) {
        try {
            if (amount > 0) {
                Purchase purchase = new Purchase();
                purchase.setAmount(amount);
                purchase.setQrCode(qrCode);
                purchaseRepository.save(purchase);
            }

            return "redirect:/purchase";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/purchase";
        }
    }
}
