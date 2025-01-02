//package ro.tuc.ds2020.services;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import ro.tuc.ds2020.controllers.handlers.exceptions.model.RequestFailedException;
//import ro.tuc.ds2020.dtos.DeviceUserDTO;
//import ro.tuc.ds2020.dtos.SessionPayloadDTO;
//import ro.tuc.ds2020.dtos.UserDTO;
//
//import java.net.InetSocketAddress;
//
//@Service
//public class SessionService {
//    @Value("${device.service.url}")
//    private String deviceServiceUrl;
//    @Value("${device.service.key}")
//    private String API_KEY;
//    private static final String API_KEY_HEADER = "X-Api-Key";
//
//    public void registerSession(String sessionId, String userId, UserDTO.RoleDTO role) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = deviceServiceUrl + "/session";
//        SessionPayloadDTO sessionPayloadDTO = SessionPayloadDTO.builder()
//                .sessionId(sessionId)
//                .userId(userId)
//                .role(role)
//                .build();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set(API_KEY_HEADER , API_KEY);
//
//        HttpEntity<SessionPayloadDTO> requestEntity = new HttpEntity<>(sessionPayloadDTO, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RequestFailedException("devices-service");
//        }
//
//    }
//    public void unregisterSession(String sessionId) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = deviceServiceUrl + "/session";
//        try{
//            restTemplate.delete(url,sessionId);
//        }catch (Exception e){
//            throw new RequestFailedException("devices-service");
//        }
//    }
//}
