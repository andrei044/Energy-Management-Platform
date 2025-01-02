package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.RequestFailedException;
import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceUserBuilder;

import java.util.UUID;

@Service
public class DeviceUserService {
    @Value("${device.service.url}")
    private String deviceServiceUrl;
    @Value("${device.service.key}")
    private String API_KEY;
    private static final String API_KEY_HEADER = "X-Api-Key";

    public void addUser(UserDTO userDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String url = deviceServiceUrl + "/user";
        DeviceUserDTO deviceUserDTO = DeviceUserBuilder.toUserDTO(userDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(API_KEY_HEADER , API_KEY);

        HttpEntity<DeviceUserDTO> requestEntity = new HttpEntity<>(deviceUserDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RequestFailedException("devices-service");
        }
    }
    public void deleteUser(UUID userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = deviceServiceUrl + "/user/"+userId.toString();
//        DeviceUserDTO userDTO = DeviceUserDTO.builder().id(userId).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY_HEADER , API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

//        HttpEntity<DeviceUserDTO> requestEntity = new HttpEntity<>(userDTO, headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RequestFailedException("devices-service");
//        }
    }
}
