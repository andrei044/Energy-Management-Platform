package ro.tuc.ds2020.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //    private final ApiKeyAuthFilter apiKeyAuthFilter;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
//        this.apiKeyAuthFilter = apiKeyAuthFilter;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/device").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/device/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/device").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/device").hasRole("ADMIN")
                        .requestMatchers("/user", "/user/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000","https://localhost:3000","https://52.174.86.46:3000","http://52.174.86.46:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(List.of("http://localhost:8080","http://127.0.0.1:8080", "http://localhost", "http://127.0.0.1"));
//        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//        corsConfig.setAllowCredentials(true);
//        corsConfig.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsFilter(source);
//    }
}
//
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ro.tuc.ds2020.dtos.UserDTO;
//import ro.tuc.ds2020.services.JwtService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final JwtService jwtService;
//
//    public SecurityConfig(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/").permitAll() // Public endpoint
//                        .anyRequest().authenticated() // All other endpoints require authentication
//                )
//                .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public OncePerRequestFilter jwtAuthenticationFilter() {
//        return new OncePerRequestFilter() {
//            @Override
//            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
//                String authorizationHeader = request.getHeader("Authorization");
//
//                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                    String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//                    try {
//                        UserDTO user = jwtService.validateToken(token);
//                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                                user, null, null); // Adjust authorities if needed
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    } catch (Exception e) {
//                        // Optionally log or handle invalid token cases
//                        SecurityContextHolder.clearContext();
//                    }
//                }
//
//                try {
//                    filterChain.doFilter(request, response);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//    }
//}

