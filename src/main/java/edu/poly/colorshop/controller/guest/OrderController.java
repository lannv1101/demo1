package edu.poly.colorshop.controller.guest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.colorshop.entity.Order;
import edu.poly.colorshop.service.OrderService;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping(value = "checkout")
    public String checkout() {
        return "site/order/checkout";
    }

    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        model.addAttribute("orders", orderService.findByUsername(username));
        return "site/order/list";
    }

    @RequestMapping(value = "detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        Double amount = 0.0;
        for (int i = 0; i < order.getOrderDetails().size(); i++) {
            amount += (order.getOrderDetails().get(i).getQuantity()
                    * (order.getOrderDetails().get(i).getPrice() - (order.getOrderDetails().get(i).getPrice()
                            * order.getOrderDetails().get(i).getProduct().getDiscount() * 0.01)));
            // System.out.println("aaaaa" + amount);
        }
        model.addAttribute("amount", amount);
        model.addAttribute("order", orderService.findById(id));
        return "site/order/bill";
    }
}
