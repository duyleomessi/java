package noname.service;

import noname.model.Post;

import java.util.List;

public interface PostClient {
    List<Post> getPosts();
    Post getPostById(String id);
}
