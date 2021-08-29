package edu.poly.colorshop.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartController {
    @GetMapping(value = "order/_shopping-cart")
    public String shop(Model model) {
        return "site/order/_shopping-cart";
    }

}
