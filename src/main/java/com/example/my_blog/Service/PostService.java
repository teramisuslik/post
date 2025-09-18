package com.example.my_blog.Service;

import com.example.my_blog.Entity.Post;
import com.example.my_blog.Repocitory.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public  void addPost(Post post){
        postRepository.save(post);
    }
}
