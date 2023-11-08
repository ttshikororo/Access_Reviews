package za.co.univen.its.reviews.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestTemplateService {
    RestTemplate restTemplate = new RestTemplate();


    private static final String getAllMenus_URL = "http://localhost:8989/api/access_reviews/4919";
    public ResponseEntity<String> getAccessMenus()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("itsadmin","Univen@8498");

        HttpEntity<String> entity = new HttpEntity<String>("paramaters",headers);

        ResponseEntity<String> response = restTemplate.exchange(getAllMenus_URL, HttpMethod.GET,entity ,String.class);


        return response;

    }
}
