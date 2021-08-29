package edu.poly.colorshop.controller.guest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.service.AccountService;
import edu.poly.colorshop.service.MailerService;
import net.bytebuddy.utility.RandomString;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/site/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    HttpSession session;
    @Autowired
    private BCryptPasswordEncoder be;
    @Autowired
    MailerService mailerService;

    // register
    @GetMapping(value = "register")
    public String get(Model model) {
        model.addAttribute("account", new Account());
        return "site/account/register";
    }

    // register
    @PostMapping(value = "save")
    public String postMethodName(@ModelAttribute("account") Account entity, Model model, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        if (!checkEmail(entity.getEmail())) {
            model.addAttribute("error", "Email này đã được sử dụng!");
            return "site/account/register";
        }
        register(entity, getSiteURL(request));
        return "site/account/register_success";
    }

    // register
    public void register(Account user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = be.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        accountService.save(user);
        String to = user.getEmail();
        String subject = "Please verify your registration";
        String body = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Your company name.";
        body = body.replace("[[name]]", user.getFullname());
        String verifyURL = siteURL + "/site/account/verify?code=" + user.getVerificationCode();
        body = body.replace("[[URL]]", verifyURL);
        mailerService.send(to, subject, body);

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    // check email
    public boolean checkEmail(String email) {
        List<Account> list = accountService.findAll();
        for (Account c : list) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    // verify
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (accountService.verify(code)) {
            return "site/account/verify_success";
        } else {
            return "site/account/verify_fail";
        }
    }

    // profile
    @GetMapping(value = "user-profile")
    public String getProfile(Model model, @ModelAttribute("account") Account account) {
        String username = (String) session.getAttribute("username");
        // System.out.println("username:" + username);
        Optional<Account> user = accountService.findById(username);
        if (user.isPresent()) {
            Account entity = user.get();
            model.addAttribute("account", entity);
            // System.out.println("user" + entity);
        }

        return "site/profile/user-profile-lite";
    }

    // update profile
    @RequestMapping("profile-update")
    public String saveOrUpdate(Model model, @ModelAttribute("account") Account account) {
        // if (result.hasErrors()) {
        // return new ModelAndView("site/profile/profile");
        // }
        String username = (String) session.getAttribute("username");
        Optional<Account> user = accountService.findById(username);
        account.setUsername(user.get().getUsername());
        accountService.save(account);
        model.addAttribute("message", "Your information is saved!");
        return "redirect:/site/account/user-profile";
    }
}
