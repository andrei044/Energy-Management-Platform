package ro.tuc.ds2020.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.JwtService;

import java.util.Map;
@Component
@Slf4j
public class CustomHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract query parameters
        String query = request.getURI().getQuery();
        if (query == null || !query.contains("token=")) {
            log.warn("Missing token in query parameters");
            return false; // Reject the handshake
        }

        // Extract the token
        String token = query.split("token=")[1];
        if (token.contains("&")) {
            token = token.split("&")[0]; // Handle additional query parameters
        }

        String username = extractUsernameFromRequest(request);
        // Validate the token
        try{
            UserDTO userDTO = jwtService.validateToken(token);
            if(username.equals("admin") && !userDTO.getRole().equals(UserDTO.RoleDTO.ROLE_ADMIN)){
                log.warn("Unauthorized access");
                return false;
            }
            if(userDTO.getRole().equals(UserDTO.RoleDTO.ROLE_CLIENT) && !username.equals(userDTO.getName())){
                log.warn("Unauthorized access");
                return false;
            }
        } catch (Exception e) {
            log.warn("Invalid token");
            return false; // Reject the handshake if validation fails
        }

        // Optionally, store validated attributes in the session
        attributes.put("username", username);
        attributes.put("token", token);

        return true; // Accept the handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Optionally handle post-handshake logic
        log.warn("Handshake completed successfully");
    }

    private String extractUsernameFromRequest(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return path.substring(path.lastIndexOf("/") + 1); // Extract device ID from URL
    }
}
