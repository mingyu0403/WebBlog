package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Protocol.PostUsernameProtocol;
import kr.hs.dgsw.web02blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/getAllPosts")
    public List<PostUsernameProtocol> getAllPosts(){
        return this.postService.getAllPosts();
    }

    @GetMapping("/getPost/{postId}")
    public PostUsernameProtocol getPost(@PathVariable Long postId){
        return this.postService.getPost(postId);
    }

    @PostMapping("/addPost")
    public PostUsernameProtocol addPost(@RequestBody Post post){
        return this.postService.addPost(post);
    }

    @PutMapping("/updatePost")
    public PostUsernameProtocol updatePost(@RequestBody Post post){
        return this.postService.updatePost(post);
    }

    @DeleteMapping("/deletePost/{postId}")
    public boolean deletePost(@PathVariable Long postId){
        return this.postService.deletePost(postId);
    }

}
