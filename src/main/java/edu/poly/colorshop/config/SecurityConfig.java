package edu.poly.colorshop.config;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.poly.colorshop.entity.Account;
import edu.poly.colorshop.service.AccountService;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AccountService accountService;
    @Autowired
    BCryptPasswordEncoder pe;
    @Autowired
    HttpSession session;

    // Cung cấp nguồn dữ liệu đăng nhập
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                Optional<Account> user = accountService.findById(username);// tai account thong qua username...
                session.setAttribute("username", user.get().getUsername());
                // System.out.println("aaaaaaaaa" + session);
                String password = user.get().getPassword();
                if (user.isPresent() && pe.matches(pe.encode(password), user.get().getPassword())) {
                    user.get().setPassword("");

                }
                // String password = pe.encode(user.get().getPassword());
                String[] roles = user.get().getAuthorities().stream().map(er -> er.getRole().getId())
                        .collect(Collectors.toList()).toArray(new String[0]);
                return User.withUsername(username).password(password).roles(roles).build();
            } catch (Exception e) {
                throw new UsernameNotFoundException(username + "not found");
            }

        });
    }

    // Phân quyền sử dụng
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/order/**").authenticated().antMatchers("/admin/**")
                .hasAnyRole("STAF", "DIRE").antMatchers("/rest/authorities").hasRole("DIRE").anyRequest().permitAll();
        http.formLogin().loginPage("/security/login/form").loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/security/login/success", false).failureUrl("/security/login/error");
        http.rememberMe().tokenValiditySeconds(88400);
        // k dc quyen truy xuat
        http.exceptionHandling().accessDeniedPage("/security/unauthorized");
        http.logout().logoutUrl("/security/logoff").logoutSuccessUrl("/security/logoff/success");

        // OAuth2-Đăng nhập từ mạng xã hội
        http.oauth2Login().loginPage("/security/login/form").defaultSuccessUrl("/security/login/success", true)
                .failureUrl("/security/login/error").authorizationEndpoint().baseUri("/oauth2/authorization");
    }

    // Cơ chế mã hóa mật khẩu
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cho phép truy xuất RESP API từ bên ngoài (domain khác)
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
