package ro.tuc.ds2020.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.JwtService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    private static final String API_KEY_HEADER = "X-Api-Key";
    @Value("${device.service.key}")
    private String API_KEY;

////    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the Authorization header
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("Authorization");
        String token = null;

        String header = request.getHeader(API_KEY_HEADER);
        if(header != null){
            if(header.equals(API_KEY)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        "SERVICE_USER", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))); // Adjust authorities if needed
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                log.warn("Unauthorized access with API key: " + header);
                SecurityContextHolder.clearContext();
            }
        }else{
            // Check if the header starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token

                //check if the token is valid
                try {
                    UserDTO userDTO = jwtService.validateToken(token);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDTO, null, List.of(new SimpleGrantedAuthority(userDTO.getRole().name()))); // Adjust authorities if needed
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("User with id: " + userDTO.getId() + " has been authenticated");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    log.warn("Unauthorized access with token: " + token);
                    SecurityContextHolder.clearContext();
                }
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
}
