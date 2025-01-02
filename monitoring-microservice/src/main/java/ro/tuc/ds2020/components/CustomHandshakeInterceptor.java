package ro.tuc.ds2020.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ro.tuc.ds2020.services.JwtService;

import java.util.Map;
@Component
public class CustomHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract query parameters
        String query = request.getURI().getQuery();
        if (query == null || !query.contains("token=")) {
            System.out.println("Missing token in query parameters");
            return false; // Reject the handshake
        }

        // Extract the token
        String token = query.split("token=")[1];
        if (token.contains("&")) {
            token = token.split("&")[0]; // Handle additional query parameters
        }

        // Validate the token
        try{
            jwtService.validateToken(token);
        } catch (Exception e) {
            System.out.println("Invalid token");
            return false; // Reject the handshake if validation fails
        }

        // Optionally, store validated attributes in the session
        attributes.put("deviceId", extractDeviceIdFromRequest(request));
        attributes.put("token", token);

        return true; // Accept the handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Optionally handle post-handshake logic
        System.out.println("Handshake completed successfully");
    }

    private String extractDeviceIdFromRequest(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return path.substring(path.lastIndexOf("/") + 1); // Extract device ID from URL
    }
}
