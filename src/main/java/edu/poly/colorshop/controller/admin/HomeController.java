package edu.poly.colorshop.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.colorshop.service.OrderService;

@Controller
public class HomeController {
    @Autowired
    OrderService orderService;
    @Autowired
    HttpSession session;

    @RequestMapping({ "/", "/home/index" })
    public String home() {
        return "redirect:/color/home";
    }

    @RequestMapping({ "/admin", "/admin/home/index" })
    public String admin(Model model) {
        return "redirect:/admin/layout.html#!/statistics";
    }
    // single page application layout trong angularjs
}
