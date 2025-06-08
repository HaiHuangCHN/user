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
//	}
//}

/**
 * Way 1
 * 
 * @author hai.huang.a@outlook.com
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
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		if (enableSwaggerSecurity) {
//			http.authorizeRequests()
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
 * @author hai.huang.a@outlook.com
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
//			auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user")
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
 * @author hai.huang.a@outlook.com
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
        if (Boolean.TRUE.equals(enableSwaggerSecurity)) {
            http.authorizeRequests()
                    .antMatchers("/swagger**").authenticated()
                    .antMatchers("/v2/api-docs").authenticated()
                    .and().formLogin().loginPage("/login/page").and()
                    .anonymous()/* .and().httpBasic() */;
        } else {
            http.authorizeRequests().anyRequest().anonymous();
        }

        http.csrf().disable();
    }
}
