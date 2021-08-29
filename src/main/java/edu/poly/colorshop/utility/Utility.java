package edu.poly.colorshop.utility;

import javax.servlet.http.HttpServletRequest;

// tiện ích
public class Utility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
        // getRequestURL() trả về URL của ứng dụng có thể được sử dụng trong quá trình
        // sản xuất, vì vậy
        // người dùng sẽ có thể nhấp vào liên kết trong email

    }
}
