package edu.poly.colorshop.controller.guest;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.service.AccountService;

@Controller
@RequestMapping("color")
public class ChangePasswordController {

    @Autowired
    private AccountService accountService;
    @Autowired
    HttpSession session;
    @Autowired
    private BCryptPasswordEncoder be;

    @GetMapping("/change_password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("pageTitle", "Change Expired Password");
        return "site/changepass/change_password";
    }

    @PostMapping("/change_password")
    public String processChangePassword(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes ra, @AuthenticationPrincipal Authentication authentication) throws ServletException {
        String username = (String) session.getAttribute("username");
        // System.out.println("username:" + username);
        Optional<Account> account = accountService.findById(username);
        System.out.println("user" + account);
        // Account account = (Account) authentication.getPrincipal();

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        model.addAttribute("pageTitle", "Change Expired Password");
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Your new password must be different than the old one.");
            return "site/changepass/change_password";
        }
        if (!be.matches(oldPassword, account.get().getPassword())) {
            model.addAttribute("message", "Your old password is incorrect.");
            return "site/changepass/change_password";

        } else {
            accountService.changePassword(account.get(), newPassword);
            request.logout();
            ra.addFlashAttribute("message", "You have changed your password successfully. " + "Please login again.");

            return "redirect:/security/login/form";
        }

    }
}
