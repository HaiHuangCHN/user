package com.user.center.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// Disable default authorization provided by Spring Security, set allow
//		// anonymous access
//		http.authorizeRequests().anyRequest().anonymous();
//		// Disable CSRF protection, please implement the token approach according to
//		// Spring Security spec before enable, and we can enable by URL pattern
//		http.csrf().disable();
//		// No cache HTTP header is enabled by default, we can implement the request
//		// matcher here to disable for particular URL or override the header in
//		// controller function
//	}
//}

/**
 * Swagger 访问需认证
 *
 * @author hai.huang.a@outlook.com
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${enable.swagger.security}")
    private Boolean enableSwaggerSecurity;

    @Value("${swagger.username}")
    private String swaggerUsername;

    @Value("${swagger.password}")
    private String swaggerPassword;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser(swaggerUsername).password(new BCryptPasswordEncoder().encode(swaggerPassword))
                .roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (enableSwaggerSecurity) {
            http.authorizeRequests().antMatchers("/swagger**").authenticated().antMatchers("/v2/api-docs").authenticated().and().formLogin().loginPage("/common/login/page").and()
                    .anonymous()/* .and().httpBasic() */;
        } else {
            http.authorizeRequests().anyRequest().anonymous();
        }

        http.csrf().disable();
    }
}