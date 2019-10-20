package com.lms.LMSOrchestrator.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }
//        inMemoryAuthentication().
//        withUser("librarian").password(passwordEncoder().encode("librarian123")).
//        roles("Librarian").authorities("ACCESS_LIBRARIAN", "ACCESS_BORROWER")
//        .and().
//        withUser("borrower").password(passwordEncoder().encode("borrower123")).
//        roles("Borrower").authorities("ACCESS_BORROWER")
//        .and().
//        withUser("admin").password(passwordEncoder().encode("admin123")).
//        roles("Admin").authorities("ACCESS_ADMIN", "ACCESS_LIBRARIAN", "ACCESS_BORROWER");
//    }

    //We provide request authorization in the below method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/orchestrator").permitAll()
        .antMatchers("/orchestrator/librarian/**").hasAuthority("ACCESS_LIBRARIAN")
        .antMatchers("/orchestrator/borrower/**").hasAnyAuthority("ACCESS_BORROWER")
        .antMatchers("/orchestrator/admin/**").hasAuthority("ACCESS_ADMIN")
        .and().httpBasic();
    }
  
    
    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
