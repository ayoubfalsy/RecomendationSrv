package com.javatechie.springsecurityjwtexample.controllers;

import com.javatechie.springsecurityjwtexample.configKafka.KafkaConstants;
import com.javatechie.springsecurityjwtexample.entities.AppUser;
import com.javatechie.springsecurityjwtexample.entities.Message;
import com.javatechie.springsecurityjwtexample.entities.Post;
import com.javatechie.springsecurityjwtexample.entitiesStatus.Posts;
import com.javatechie.springsecurityjwtexample.services.PostService;
import com.javatechie.springsecurityjwtexample.services.UserService;
import com.javatechie.springsecurityjwtexample.services.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@RequestMapping(value = "/posts")
@RestController
public class PostController {
    @Autowired
    KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;


    @GetMapping("/getPosts/{lngId}/{lngPage}/{lngSize}")
    public ResponseEntity<?> getPostbyId(@PathVariable long lngId, @PathVariable int lngPage, @PathVariable int lngSize) {
        Page<Post> lstPosts = null;
        try {

            lstPosts = postService.findbyUserId(lngId, lngPage, lngSize);
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Page<Post>>(lstPosts, HttpStatus.OK);
    }

    @PostMapping("/getAllPosts")
//    @SendTo("/topic/group")
    public ResponseEntity<?> allPostsByUser(@RequestBody Posts posts) {
        Page<Post> lstPosts = null;
        AppUser clsAppUSer = new AppUser();
//        User clsAUser = new User("ayb", "ab", 120L);
        try {
//            kafkaTemplate.send(KafkaConstants.TOPIC_PRODUCER, new Message("ayb", "ab", "test"));
            clsAppUSer = userService.findById(posts.getLngId());
            //pays !type
            if (posts.getStrPays().equals(clsAppUSer.getPays()) && posts.getStrType().equals("all")) {
                lstPosts = postService.allPostsByUser(posts.getLngId(), posts.getStrPays(), posts.getStrType(), posts.getLngPage(), posts.getLngSize());
            }
            // pays et type
            else if (posts.getStrPays().equals(clsAppUSer.getPays()) && !posts.getStrType().equals("all")) {
                lstPosts = postService.allPostsByUserType(posts.getLngId(), posts.getStrPays(), posts.getStrType(), posts.getLngPage(), posts.getLngSize());
            }
            //!pays type
            else if (!posts.getStrPays().equals(clsAppUSer.getPays()) && posts.getStrType().equals("all")) {
                lstPosts = postService.allPostsByUserPaysDifType(posts.getLngId(), clsAppUSer.getPays(), posts.getStrType(), posts.getLngPage(), posts.getLngSize());
            }
            // !pays o !type
            else if (!posts.getStrPays().equals(clsAppUSer.getPays()) && !posts.getStrType().equals("all")) {
                lstPosts = postService.allPostsByUserDifPay(posts.getLngId(), clsAppUSer.getPays(), posts.getStrType(), posts.getLngPage(), posts.getLngSize());
            } else {
                System.out.println("trrrrrrrrrrrrst");
            }


//            lstPosts = postService.allPostsByUser(posts.getLngId(), posts.getStrPays(), posts.getStrType(), posts.getLngPage(), posts.getLngSize());
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Page<Post>>(lstPosts, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        Post clsPost = null;
        try {

            clsPost = postService.addPost(post);
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Post>(clsPost, HttpStatus.OK);

    }

    @DeleteMapping("/deletePost/{lngId}")
    public ResponseEntity<?> deletePostbyId(@PathVariable long lngId) {
        boolean clsPost = true;
        try {
            postService.deletePost(lngId);
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
        return new ResponseEntity<Boolean>(clsPost, HttpStatus.OK);
    }
}
