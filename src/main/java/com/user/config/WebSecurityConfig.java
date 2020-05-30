package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
 * Way 1
 * 
 * @author Huang, Hai
 *
 */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Value("${enable.swagger.security}")
//	private boolean enableSwaggerSecurity;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//		.withUser("swagger_d0").password(new BCryptPasswordEncoder().encode("d0123456")).roles("d0")
//		.and().withUser("swagger_t0").password(new BCryptPasswordEncoder().encode("t0123456")).roles("t0")
//		.and().withUser("swagger_p0").password(new BCryptPasswordEncoder().encode("p0123456")).roles("p0");
//	}
//
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		http.authorizeRequests().antMatchers("/profile/**").anonymous().anyRequest().authenticated().and().formLogin();
////		http.csrf().disable();
////	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		if (enableSwaggerSecurity) {
//			http.authorizeRequests()
////		.antMatchers("/swagger**").authenticated()
////		.antMatchers("/v2/api-docs").authenticated()
//			.antMatchers("/v2/api-docs").hasAnyRole("d0", "t0", "p0")
//			.antMatchers("/swagger-resources").hasAnyRole("t0", "p0")
//			.antMatchers("/swagger-ui.html").hasAnyRole("p0")
//			.and().formLogin().and().anonymous();
//		} else {
//			http.authorizeRequests().anyRequest().anonymous();
//		}
//
//		http.csrf().disable();
//	}
//}

/**
 * Way 2
 * 
 * @author Huang, Hai
 *
 */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//	@Configuration
//	@Order(1)
//	public static class SwaggerWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//			auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user1")
//					.password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
//		}
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.authorizeRequests().antMatchers("/swagger**").authenticated().antMatchers("/v2/api-docs")
//					.authenticated().and().formLogin();
//			http.csrf().disable();
//		}
//	}
//
//	@Configuration
//	public static class NormalWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.authorizeRequests().anyRequest().anonymous();
//			http.csrf().disable();
//		}
//	}
//}

/**
 * Final solution
 * 
 * @author Huang, Hai
 *
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

///**
// * @author itguang
// * @create 2018-01-02 10:32
// **/
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		// auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//		// 使用自定义身份验证组件
//		auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// 禁用 csrf
//		http.cors().and().csrf().disable().authorizeRequests()
//				// 允许以下请求
//				.antMatchers("/hello").permitAll()
//				// 所有请求需要身份认证
//				.anyRequest().authenticated().and()
//				// 验证登陆
//				.addFilter(new JWTLoginFilter(authenticationManager()))
//				// 验证token
//				.addFilter(new JWTAuthenticationFilter(authenticationManager()));
//	}
//
//}

/**
 * Model to be configured
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	}
//}