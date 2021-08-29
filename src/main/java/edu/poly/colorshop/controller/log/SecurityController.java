package edu.poly.colorshop.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.colorshop.service.AccountService;

@Controller
@RequestMapping("security")
public class SecurityController {
    @RequestMapping(value = "login/form")
    public String loginForm(Model model) {
        return "security/login";
    }

    @RequestMapping(value = "login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Đăng nhập thành công !");
        return "redirect:/color/home";
    }

    @RequestMapping(value = "login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Sai thông tin đăng nhập !");
        return "security/login";
    }

    @RequestMapping(value = "unauthorized")
    public String unauthorized(Model model) {
        model.addAttribute("message", "Không có quyền truy xuất !");
        return "security/login";
    }

    @RequestMapping(value = "logoff/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Bạn đã đăng xuất !");
        return "security/login";
    }

    // OAuth2
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/oauth2/login/success")
    public String success(OAuth2AuthenticationToken oauth2) {
        accountService.formLoginOAuth2(oauth2);
        return "forward:/security/login/success";
    }
}
