package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.UserDTO;
@Service
public class JwtService {
    @Value("${user.service.url}")
    private String USER_SERVICE_URL;
    public UserDTO validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = USER_SERVICE_URL+"/token";

        HttpEntity<String> requestEntity = new HttpEntity<>(token);

        ResponseEntity<UserDTO> response = restTemplate.postForEntity(url, requestEntity, UserDTO.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResourceNotFoundException("users-service");
        }
        return response.getBody();
    }
}
