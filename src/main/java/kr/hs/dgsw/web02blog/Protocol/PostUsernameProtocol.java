package kr.hs.dgsw.web02blog.Protocol;

import kr.hs.dgsw.web02blog.Domain.Post;
import lombok.Data;

@Data
public class PostUsernameProtocol extends Post {

    private String username;

    public PostUsernameProtocol(Post post, String username){
        super(post);
        this.username = username;
    }

}
