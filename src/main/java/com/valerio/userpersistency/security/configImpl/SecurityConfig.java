package com.valerio.userpersistency.security.configImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("authenticationSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/persistence", "/persistence/accessDenied").permitAll()
                .and().exceptionHandling().accessDeniedPage("/persistence/accessDenied")
                .and().csrf().disable();

        // two variants of AuthorizedUrl configuration was done here
        http.authorizeRequests()
                .antMatchers("/persistence/homepage/Dashboard/**").access("hasRole('ADMIN')");
        http.authorizeRequests()
                .antMatchers("/persistence/homepage/user/**", "/persistence/redirect").hasRole("USER");  // "ROLE_" adds automatically

        //login configuration was done here
        http.formLogin()
                .loginPage("/persistence")
                .loginProcessingUrl("/loginFormPostTo")
                .usernameParameter("myLoginPageUsernameParameterName")
                .passwordParameter("myLoginPagePasswordParameterName")
                .successHandler(authenticationSuccessHandler);

        //remember me configuration was done here
        http.rememberMe()
                .tokenRepository(persistentTokenRepository())
                .rememberMeParameter("myRememberMeParameterName")
                .rememberMeCookieName("my-remember-me")
                .tokenValiditySeconds(86400);

        //logout configuration was done here
        http.logout()
                .logoutUrl("/Logout")
                .logoutSuccessUrl("/persistence");

        http.sessionManagement().maximumSessions(1);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean(value = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        CustomJdbcTokenImpl tokenRepository = new CustomJdbcTokenImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
