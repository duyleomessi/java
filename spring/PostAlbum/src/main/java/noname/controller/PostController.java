package noname.controller;

import noname.model.Post;
import noname.service.PostClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostClientImpl postClientImpl = new PostClientImpl();

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postClientImpl.getPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getWeather(@PathVariable String id) {
        return postClientImpl.getPostById(id);
    }

}
