package ru.otus.spring13homework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/css/*").permitAll()
                .and()

                .authorizeRequests().antMatchers(HttpMethod.GET, "/", "/book").authenticated()
                .and()

                .authorizeRequests().antMatchers(HttpMethod.POST, "/book/*/comment").hasRole("USER")
                .and()

                .authorizeRequests().antMatchers("/book/edit", "/book/create").hasRole("ADMIN")
                .and()

                .authorizeRequests().antMatchers(HttpMethod.GET, "/book/*").hasAnyRole("ADMIN", "USER")
                .and()

                .authorizeRequests().antMatchers("/book/**").hasRole("ADMIN")
                .and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()

                .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
        ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
