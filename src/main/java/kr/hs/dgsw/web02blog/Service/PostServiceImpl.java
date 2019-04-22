package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Protocol.PostUsernameProtocol;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<PostUsernameProtocol> getAllPosts() {
        List<Post> postList = this.postRepository.findAll();
        List<PostUsernameProtocol> pupList = new ArrayList<>();
        postList.forEach(post -> {
            Optional<User> found = this.userRepository.findById(post.getUserId());
            String username = null;
            if(found.isPresent())
                username = found.get().getName();
            pupList.add(new PostUsernameProtocol(post, username));
        });
        return pupList;
    }

    @Override
    public PostUsernameProtocol getPost(Long commentId) {
        PostUsernameProtocol pup = null;
        Optional<Post> post = this.postRepository.findById(commentId);
        if(post.isPresent()){
            Optional<User> user = this.userRepository.findById(post.get().getId());
            if(user.isPresent()){
                pup = new PostUsernameProtocol(post.get(), user.get().getName());
            }
        }
        return pup;
    }

    @Override
    public PostUsernameProtocol addPost(Post post) {
        PostUsernameProtocol pup = null;
        Optional<User> user = this.userRepository.findById(post.getUserId());
        if(user.isPresent()){
            pup = new PostUsernameProtocol(this.postRepository.save(post), user.get().getName());
        }
        return pup;
    }

    @Override
    public PostUsernameProtocol updatePost(Post post) {
        PostUsernameProtocol pup = null;

        Post found = this.postRepository.findById(post.getId())
                .map( p -> {
                    p.setUserId(Optional.ofNullable(post.getUserId()).orElse(p.getUserId()));
                    p.setContent(Optional.ofNullable(post.getContent()).orElse(p.getContent()));
                    p.setImagePath(Optional.ofNullable(post.getImagePath()).orElse(p.getImagePath()));
                    p.setImageName(Optional.ofNullable(post.getImageName()).orElse(p.getImageName()));
                    return this.postRepository.save(p);
                })
                .orElse(null);

        Optional<User> user = this.userRepository.findById(found.getUserId());
        if(user.isPresent()){
            pup = new PostUsernameProtocol(found, user.get().getName());
        }
        return pup;
    }

    @Override
    public boolean deletePost(Long commentId) {
        try {
            this.postRepository.deleteById(commentId);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
