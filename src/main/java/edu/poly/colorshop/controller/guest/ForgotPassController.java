package edu.poly.colorshop.controller.guest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.service.AccountService;
import edu.poly.colorshop.service.MailerService;
import edu.poly.colorshop.utility.Utility;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("color")
public class ForgotPassController {
    @Autowired
    AccountService accountService;
    @Autowired
    MailerService mailerService;

    @GetMapping(value = "forgot_password")
    public String showForgot() {
        return "site/resetpass/forgotPassword";
    }

    @PostMapping(value = "forgot_password")
    public String processForgot(HttpServletRequest request, Model model) throws ClassNotFoundException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            accountService.updateResetToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/color/reset_password?token=" + token;
            String subject = "Here's the link to reset your password";
            String body = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + resetPasswordLink
                    + "\">Change my password</a></p>" + "<br>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";
            mailerService.send(email, subject, body);
            model.addAttribute("error", "We have sent a reset password link !");
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "site/resetpass/forgotPassword";
    }

    // resetPassword
    @GetMapping(value = "reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account account = accountService.getByResetToken(token);
        model.addAttribute("token", token);
        if (account == null) {
            model.addAttribute("message", "Invalid token");
            return "message";
        }
        return "site/resetpass/resetPassword";
    }

    @PostMapping(value = "reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Account account = accountService.getByResetToken(token);
        // System.out.println("aaaaaaaaaaaaaaaaaaa" + token);
        model.addAttribute("title", "Reset your password");
        if (account == null) {
            model.addAttribute("message", "Invalid token");
            return "site/resetpass/forgotPassword";
        } else {
            accountService.updatePassword(account, password);
            model.addAttribute("message", "You have successfully changed your password");
            return "site/resetpass/forgotPassword";
        }

    }

}
