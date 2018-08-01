package pl.jstk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests()
			.antMatchers("/","/css/*","/img/**","/*css/*","/webjars/**","/login").permitAll()
        	.anyRequest().authenticated()
        	.and()
        	.formLogin().loginPage("/login").permitAll()
        	.and()
        	.formLogin().defaultSuccessUrl("/")
        	.and()
        	.formLogin().failureUrl("/login?error")
        	.and()
        	.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/403");
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
		.withUser("admin").password(passwordEncoder().encode("admin1234")).roles("ADMIN")
        .and().withUser("user").password(passwordEncoder().encode("Qwerty")).roles("USER");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
