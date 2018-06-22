package noname.service;

import noname.Application;
import noname.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostClientImpl implements PostClient {


    private RestTemplate restTemplate = new RestTemplate();


    private final String REST_URL="https://jsonplaceholder.typicode.com";

    public List<Post> getPosts() {
        // ResponseEntity<Post[]> response = restTemplate.getForEntity(REST_URL, Post[].class);
        // return Arrays.asList(response.getBody());

        Post[] posts = restTemplate.getForObject(REST_URL + "/posts", Post[].class);
        List<Post> postsList = Arrays.asList(posts);
        return postsList;
    }


    public Post getPostById(String id) {
        Post post = restTemplate.getForObject(REST_URL + "/posts/" + id, Post.class);
        return post;
    }
}
