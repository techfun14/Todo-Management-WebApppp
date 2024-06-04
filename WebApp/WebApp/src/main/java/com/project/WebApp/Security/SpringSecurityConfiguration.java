package com.project.WebApp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration
public class SpringSecurityConfiguration {
//    InMemoryUserDetailsManager
//    InMemoryUserDetailsManager(UserDetails... users)

        @Bean
        public InMemoryUserDetailsManager createUserDetailsManager(){
            UserDetails user1 = createNewUser("user", "dummy");
            UserDetails user2= createNewUser("Utkarsh","1234");
            return new InMemoryUserDetailsManager(user1,user2);
        }

        private UserDetails createNewUser(String username,String password) {
            Function<String, String> passwordEncoder= input-> passwordEncoder().encode(input);
            return User.builder().passwordEncoder(passwordEncoder).username(username).password(password).roles("USER","ADMIN").build();
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
             return new BCryptPasswordEncoder();
        }
        @Bean
         public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
            http.formLogin(Customizer.withDefaults());
            http.csrf(csrf -> csrf.disable());
            http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
            return http.build();
        }

}
