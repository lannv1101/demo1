package edu.poly.colorshop.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
    @Autowired
    AccountService accountService;
    @Autowired
    HttpSession session;

    @GetMapping()
    public List<Account> getAccount(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return accountService.getAdministrations();
        }
        return accountService.findAll();
    }

    @GetMapping(value = "list")
    public List<Account> getAll() {
        return accountService.findAll();
    }

    @GetMapping(value = "{username}")
    public Optional<Account> getById(@PathVariable("username") String username) {
        return accountService.findById(username);
    }

    @DeleteMapping(value = "{username}")
    public void delete(@PathVariable("username") String username) {
        accountService.deleteById(username);
    }

    @PutMapping(value = "{username}")
    public Account update(@PathVariable("username") String username, @RequestBody Account account) {
        return accountService.save(account);
    }

    @PostMapping()
    public Account post(@RequestBody Account entity) {
        // if (errors.hasErrors()) {
        // model.addAttribute("message", "Vui lòng sửa các lỗi sau:");
        // return accountService.save(entity);
        // }
        return accountService.save(entity);

    }

    @GetMapping(value = "countUser")
    public Long getCount() {
        return accountService.getSize();
    }

    // find name user login
    @GetMapping(value = "userNow")
    public Optional<Account> getId() {
        String name = (String) session.getAttribute("username");
        return accountService.findById(name);
    }
}
