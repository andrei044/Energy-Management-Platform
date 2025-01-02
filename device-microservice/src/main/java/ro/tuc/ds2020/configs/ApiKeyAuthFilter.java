//package ro.tuc.ds2020.configs;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import ro.tuc.ds2020.dtos.UserDTO;
//import ro.tuc.ds2020.services.CookieService;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Optional;
//@Slf4j
//@Component
//public class ApiKeyAuthFilter implements Filter {
//    private static final String API_KEY_HEADER = "X-Api-Key";
//    @Value("${device.service.key}")
//    private String API_KEY;
//
//    private final CookieService cookieService;
//
//    @Autowired
//    public ApiKeyAuthFilter(CookieService cookieService) {
//        this.cookieService = cookieService;
//    }
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//
//        String headerValue = httpRequest.getHeader(API_KEY_HEADER);
//
//        if (headerValue != null && headerValue.equals(API_KEY)) {
//            // Skip authentication by clearing SecurityContext and continuing
//            SecurityContextHolder.clearContext();
//            filterChain.doFilter(httpRequest, httpResponse);
//            return;
//        }
//
////        if(httpRequest.getRequestURI().equals("/")){
////            filterChain.doFilter(servletRequest, servletResponse);
////            return;
////        }
////        if (httpRequest.getRequestURI().startsWith("/session") || (httpRequest.getRequestURI().contains("/user") && !httpRequest.getRequestURI().contains("/device"))) {
////        String apiKey = httpRequest.getHeader(API_KEY_HEADER);
////        if (apiKey!=null) {
////            if(!API_KEY.equals(apiKey)){
////                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////                log.error("Unauthorized access with API key: " + apiKey);
////                return;
////            }
////        }
//
////        }
////        else{
////            if(httpRequest.getCookies()==null || httpRequest.getCookies().length==0){
////                log.error("Unauthorized access with no cookies");
////                httpResponse.setStatus(402);
////                return;
////            }
////            Optional<Cookie> jsessionid = Arrays.stream(httpRequest.getCookies()).filter(cookie -> cookie.getName().equals("JSESSIONID")).findFirst();
////            if (jsessionid.isEmpty()) {
////                log.error("Unauthorized access with no JSESSIONID cookie");
////                httpResponse.setStatus(403);
////                return;
////            }else{
////                UserDTO userDTO = cookieService.verifyCookie(jsessionid.get().getValue());
////                if(httpRequest.getRequestURI().equals("/device") && !userDTO.getRole().toString().equals("ROLE_ADMIN")){
////                    log.error("Unauthorized access to /device endpoint");
////                    httpResponse.setStatus(404);
////                    return;
////                }
////            }
////        }
//        filterChain.doFilter(httpRequest, httpResponse);
//    }
//
//}