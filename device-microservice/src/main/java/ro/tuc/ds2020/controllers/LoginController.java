package ro.tuc.ds2020.controllers;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
public class LoginController {
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/login";

        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username); // replace with actual username
        formData.add("password", password); // replace with actual password

        // Create the request entity with headers and body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResourceNotFoundException("users-service");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
