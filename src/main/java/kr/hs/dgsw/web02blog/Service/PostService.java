package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Protocol.PostUsernameProtocol;

import java.util.List;

public interface PostService {
    List<PostUsernameProtocol> getAllPosts();
    Post getUserRecentPost(Long userId);
    PostUsernameProtocol getPost(Long postId);
    PostUsernameProtocol addPost(Post post);
    PostUsernameProtocol updatePost(Post post);
    boolean deletePost(Long postId);
}
