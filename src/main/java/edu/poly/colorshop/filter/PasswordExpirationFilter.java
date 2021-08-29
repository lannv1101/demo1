package edu.poly.colorshop.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.poly.colorshop.entity.Account;

// bộ lọc
@Component
public class PasswordExpirationFilter implements Filter {
    // kiểm tra xem người dùng hiện đang đăng nhập đã hết hạn mật khẩu hay chưa
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (isUrlExcluded(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("PasswordExpirationFilter");
        Account account = getLoggedInCustomer();
        if (account != null && account.isPasswordExpired()) {
            showChangePasswordPage(response, httpRequest, account);
        } else {
            chain.doFilter(httpRequest, response);
        }

    }

    // phương thức xác định xem yêu cầu hiện tại có dành cho tài nguyên tĩnh (hình
    // ảnh, CSS, JS…) hay không
    private boolean isUrlExcluded(HttpServletRequest httpRequest) throws IOException, ServletException {
        String url = httpRequest.getRequestURL().toString();
        if (url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".js")
                || url.endsWith("/color/change_password")) {
            return true;
        }
        return false;
    }

    // phương thức trả về một đối tượng UserDetails đại diện cho một người dùng đã
    // được xác thực
    private Account getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }

        if (principal != null && principal instanceof Account) {
            Account account = (Account) principal;
            return account;
        }

        return null;
    }

    // chuyển hướng người dùng đến trang thay đổi mật khẩu nếu mật khẩu của họ hết
    // hạn
    private void showChangePasswordPage(ServletResponse response, HttpServletRequest httpRequest, Account account)
            throws IOException {
        System.out.println("Customer: " + account.getFullname() + " - Password Expired:");
        System.out.println("Last time password changed: " + account.getPasswordChangedTime());
        System.out.println("Current time: " + new Date());

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String redirectURL = httpRequest.getContextPath() + "/color/change_password";
        httpResponse.sendRedirect(redirectURL);
    }
}
