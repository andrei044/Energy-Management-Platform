package ro.tuc.ds2020.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.JwtService;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
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
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
