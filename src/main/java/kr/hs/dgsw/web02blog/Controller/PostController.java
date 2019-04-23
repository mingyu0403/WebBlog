package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Protocol.PostUsernameProtocol;
import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
import kr.hs.dgsw.web02blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/getAllPosts")
    public ResponseFormat getAllPosts(){
        return new ResponseFormat(
                ResponseType.POST_GET_ALL,
                this.postService.getAllPosts()
        );
    }

    @GetMapping("/getPost/{postId}")
    public ResponseFormat getPost(@PathVariable Long postId){
        return new ResponseFormat(
                ResponseType.POST_GET,
                this.postService.getPost(postId)
        );
    }

    @PostMapping("/addPost")
    public ResponseFormat addPost(@RequestBody Post post){
        return new ResponseFormat(
                ResponseType.POST_ADD,
                this.postService.addPost(post)
        );
    }

    @PutMapping("/updatePost")
    public ResponseFormat updatePost(@RequestBody Post post){
        return new ResponseFormat(
                ResponseType.POST_UPDATE,
                this.postService.updatePost(post)
        );
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseFormat deletePost(@PathVariable Long postId){
        return new ResponseFormat(
                ResponseType.POST_DELETE,
                this.postService.deletePost(postId)
        );
    }

}
