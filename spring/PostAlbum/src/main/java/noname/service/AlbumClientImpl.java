package noname.service;

import noname.model.Album;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class AlbumClientImpl {
    private RestTemplate restTemplate = new RestTemplate();

    // private final String REST_URL = "https://jsonplaceholder.typicode.com";

    @Value("${api.url:http://jsonplaceholder.typicode.com/albums}")
    URI uri;

    private HttpHeaders headers = new HttpHeaders();

    public List<Album> getAlbums() {
        String plainCredentials = "1234" + ":" + "1234"; // (1)
        String base64Credentials = Base64.getEncoder()
                .encodeToString(plainCredentials.getBytes(StandardCharsets.UTF_8)); // (2)


        RequestEntity requestEntity = RequestEntity
                .get(uri)
                .header("Authorization", "Basic" + base64Credentials)
                .build();

        // HttpEntity<Album[]> entity = new HttpEntity<Album[]>(headers);

        // ResponseEntity<Album[]> responseEntity = restTemplate.exchange(REST_URL + "/albums", HttpMethod.GET, entity, Album[].class);
         ResponseEntity<Album[]> responseEntity = restTemplate.exchange(requestEntity , Album[].class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("Response status code: " + statusCode);

        // status code
        if (statusCode == HttpStatus.OK) {
            Album[] albums = responseEntity.getBody();
            return Arrays.asList(albums);
        } else {
            return null;
        }
    }
}
