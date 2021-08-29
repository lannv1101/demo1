package edu.poly.colorshop.controller.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.colorshop.service.CategoryService;
import edu.poly.colorshop.service.OrderDetailService;
import edu.poly.colorshop.service.ProductService;

@Controller
@RequestMapping("color")
public class GuestHomeController {
    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "home")
    public String index(Model model) {
        model.addAttribute("top10New", productService.findTop10ByOrderByCreateDateDesc());
        model.addAttribute("top10Best", orderDetailService.findTop10ByOrderByQuantityDesc());
        model.addAttribute("top3", categoryService.findTop());
        return "site/layout/_main";
    }

    @GetMapping(value = "category")
    public String category(Model model) {
        return "site/category/categories";
    }

    @GetMapping(value = "contact")
    public String contact(Model model) {
        return "site/contact/contactUs";
    }

    @GetMapping(value = "aboutUs")
    public String aboutUs(Model model) {
        return "site/aboutUs/aboutUs";
    }
}
