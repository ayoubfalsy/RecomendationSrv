package com.javatechie.springsecurityjwtexample.services;

import com.javatechie.springsecurityjwtexample.entities.Post;
import com.javatechie.springsecurityjwtexample.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;


    public Page<Post> findbyUserId(long lngId, int lngPage, int lngSize) {
        return postRepository.findByAppUsers_IdOrderByPostIdDesc(lngId, PageRequest.of(lngPage, lngSize));
    }

    //pays !type
    public Page<Post> allPostsByUser(long lngId, String strPays, String strType, int lngPage, int lngSize) {
        return postRepository.allPostsByUser(lngId, strPays, strType, PageRequest.of(lngPage, lngSize));
    }

    // pays et type
    public Page<Post> allPostsByUserType(long lngId, String strPays, String strType, int lngPage, int lngSize) {
        return postRepository.allPostsByUserType(lngId, strPays, strType, PageRequest.of(lngPage, lngSize));
    }

    // !pays o type
    public Page<Post> allPostsByUserDifPay(long lngId, String strPays, String strType, int lngPage, int lngSize) {
        return postRepository.allPostsByUserDifPay(lngId, strPays, strType, PageRequest.of(lngPage, lngSize));
    }

    //!pays !type
    public Page<Post> allPostsByUserPaysDifType(long lngId, String strPays, String strType, int lngPage, int lngSize) {
        return postRepository.allPostsByUserPaysDifType(lngId, strPays, strType, PageRequest.of(lngPage, lngSize));
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(long kngPostid) {
        postRepository.deleteById(kngPostid);
    }
}
