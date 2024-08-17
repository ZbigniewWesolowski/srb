
package pl.srb.srb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll() // Zezwala na dostęp do wszystkich stron bez logowania
//                        .requestMatchers("/", "/about", "/gallery", "/**").permitAll() // Zezwól na dostęp do strony głównej i logowania bez logowania
//                        .requestMatchers("/reserve").hasRole("USER")  // Tylko dla użytkowników z rolą USER
//                        .anyRequest().authenticated() // Wymagaj uwierzytelnienia dla wszystkich innych stron
                )
                .formLogin(form -> form
                        .loginPage("/login") // Ustawienie strony logowania
                        .successHandler(authenticationSuccessHandler())
                );
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                org.springframework.security.core.Authentication authentication)
                    throws IOException, ServletException {
                // Sprawdzenie roli użytkownika
                if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    response.sendRedirect("/admin/home");
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                    response.sendRedirect("/user/home");
                } else {
                    response.sendRedirect("/"); // Domyślne przekierowanie, jeśli rola jest nieznana
                }
            }
        };
    }


    @Bean
    public UserDetailsService userDetailsService() {
        // Tworzenie użytkowników w pamięci dla testów
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}