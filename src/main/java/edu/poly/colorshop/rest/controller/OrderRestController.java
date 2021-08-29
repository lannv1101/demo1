package edu.poly.colorshop.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.entity.Order;
import edu.poly.colorshop.entity.OrderDetail;
import edu.poly.colorshop.entity.Product;
import edu.poly.colorshop.model.Report;
import edu.poly.colorshop.model.ReportByMonth;
import edu.poly.colorshop.service.AccountService;
import edu.poly.colorshop.service.MailerService;
import edu.poly.colorshop.service.OrderDetailService;
import edu.poly.colorshop.service.OrderService;
import edu.poly.colorshop.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
    @Autowired
    OrderService orderService;
    @Autowired
    MailerService mailerService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProductService productService;
    Account account;

    @GetMapping()
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping(value = "{orderId}")
    public List<OrderDetail> getAmount(@PathVariable Long orderId) {
        return orderDetailService.getAmount(orderId);
    }

    @GetMapping(value = "sum")
    public List<Object> getSumYear() {
        return orderDetailService.getSumYear();
    }

    @GetMapping(value = "month")
    public List<Object> getMonth() {
        return orderService.selectMonth();
    }

    @GetMapping(value = "year")
    public List<Object> getYear() {
        return orderService.selectYear();
    }

    @GetMapping(value = "inventoryByCategory")
    public List<Report> getInventoryByCategory() {
        return orderDetailService.getInventoryByCategory();
    }

    @GetMapping(value = "inventoryByMonth")
    public List<ReportByMonth> getInventoryByMonth() {
        return orderDetailService.getInventoryByMonth();
    }

    @GetMapping(value = "thongke/{year}/{month}")
    public List<OrderDetail> getThongke(@PathVariable Integer month, @PathVariable Integer year) {
        return orderDetailService.getValueByMonthAndYear(year, month);
    }

    @GetMapping(value = "thongkeCate/{year}/{month}")
    public Object getCate(@PathVariable Integer month, @PathVariable Integer year) {
        return orderDetailService.getByCateMonthAndYear(year, month);
    }

    @PostMapping()
    public Order create(@RequestBody JsonNode orderData) throws MessagingException {
        ObjectMapper mapper = new ObjectMapper();// Chuyển đổi JSON
        Order order = mapper.convertValue(orderData, Order.class);
        orderService.save(order);
        TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {

        };
        // lấy dữ liệu từ orderDetails
        List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
                .peek(d -> d.setOrder(order)).collect(Collectors.toList());
        for (OrderDetail orderDetail : details) {// duyệt từ orderDetails trên angularjs
            Product product = productService.findById(orderDetail.getProduct().getId());
            // lấy id của sản phẩm
            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());// lấy sl ban đầu trừ SL mua
            productService.save(product);

        }

        Optional<Account> a = accountService.findById(orderData.get("account").get("username").asText());
        orderDetailService.saveAll(details);
        Long id = orderService.getMaxId();
        // System.out.println("id" + id);
        // System.out.println("createDate" + orderData.get("createDate").asText());
        // System.out.println("email" + a.get().getEmail());
        String to = a.get().getEmail();
        String subject = "Đặt hàng Thành Công, mã đơn hàng #" + id;
        String body = "Xin chào " + orderData.get("account").get("username").asText() + " ,\r\n" + "\r\n" + "Đơn hàng #"
                + id + " của bạn đã được đặt thành công ngày :" + orderData.get("createDate").asText() + "\r\n" + "\r\n"
                + "Vui lòng đăng nhập COLORMARKET để xác bạn theo dõi và nhận hàng với sản phẩm trong vòng 3 ngày.\r\n"
                + "Chúng tôi sẽ gọi điện thoại để xác nhận bạn đã đặt hàng và chuẩn bị giao hàng, bạn vui lòng giữ điện thoại trong ít phút";
        mailerService.send(to, subject, body);
        return order;
    }
}
