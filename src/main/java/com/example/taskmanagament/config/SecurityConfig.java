package com.example.taskmanagament.config;

import com.example.taskmanagament.repository.UserRepository;
import com.example.taskmanagament.service.EmailService;
import com.example.taskmanagament.service.OtpService;
import com.example.taskmanagament.service.UserService;
import com.example.taskmanagament.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(
            @Lazy AuthenticationProvider authenticationProvider,
            @Lazy JwtFilter jwtFilter,
            @Lazy MyUserDetailsService myUserDetailsService,
            @Lazy EmailService emailService,
            @Lazy UserRepository userRepository,
            @Lazy OtpService otpService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
        this.myUserDetailsService = myUserDetailsService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtService(), myUserDetailsService);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
    @Bean
    public OtpService otpService() {
        return new OtpService(emailService);
    }
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, otpService);
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(userService());
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers( "/swagger-ui.html/**",
                                        "/swagger-resources/**",
                                        "/v2/api-docs",
                                        "/webjars/**").permitAll()
                                .anyRequest().authenticated())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .rememberMe(rememberMe ->
                        rememberMe
                                .userDetailsService(myUserDetailsService)
                                .key("secretkeyexamplesecretkeyexamplesecretkeyexamplesecretkeyexample")
                                .rememberMeParameter("remember-me")
                                .tokenValiditySeconds(86400)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
