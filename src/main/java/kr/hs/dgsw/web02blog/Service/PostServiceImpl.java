package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Protocol.PostUsernameProtocol;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @PostConstruct
    private void init(){
        User u = this.userRepository.save(
                new User("정민규계정","정민규비번", "정민규")
        );
        this.postRepository.save(new Post(u.getId(), "게시글 1","안녕하세요 1번입니다."));
        this.postRepository.save(new Post(u.getId(), "게시글 2","반갑습니다 2번입니다."));
        this.postRepository.save(new Post(u.getId(), "게시글 3","Dear moon, my moon\n" +
                "가까워지지 않아\n" +
                "잰걸음으로 따라가도 닿지 않는 달처럼\n" +
                "\n" +
                "Oh moon, like moon\n" +
                "왜 사라지지 않아\n" +
                "뒤돌아 등지고 도망쳐 봐도\n" +
                "따라오는 저 달처럼,\n" +
                "\n" +
                "넌,\n" +
                "우연일까\n" +
                "눈 맞추던 순간\n" +
                "나에게 말을 걸어오는 \n" +
                "낮은 목소리가 들린 것 같아\n" +
                "\n" +
                "답을 한다\n" +
                "망설이던 대답\n" +
                "아스라이 거기 너를\n" +
                "왜인지 난, 다 알 것 같다고\n" +
                "\n" +
                "Oh moon\n" +
                "My moon\n" +
                "안으려는 게 아냐\n" +
                "내 품에 안기엔 턱없이 커다란 걸 알아\n" +
                "\n" +
                "Oh moon\n" +
                "My moon\n" +
                "가지려는 게 아냐\n" +
                "네가 나에게 이리 눈 부신 건\n" +
                "내가 너무나 짙은 밤이기 때문인 걸\n" +
                "\n" +
                "우연일까\n" +
                "하얀 얼굴 어딘가\n" +
                "너에겐 어울리지 않는 \n" +
                "그늘진 얼룩을 본 것만 같아\n" +
                "\n" +
                "손을 흔든다 \n" +
                "널 부르는 수화\n" +
                "여기 너와 몹시 닮은\n" +
                "외톨이의 존재가 있다고\n" +
                "\n" +
                "\n" +
                "잘 살아지지 않아\n" +
                "\n" +
                "My only moon\n" +
                "가닿지 않을 만큼\n" +
                "깊어진 밤까지\n" +
                "하얀 빛을 그 고요를\n" +
                "\n" +
                "오늘 밤도 잠들지 않을게\n"));
    }

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
    public Post getUserRecentPost(Long userId) {
        return this.postRepository
                .findTopByUserIdOrderByIdDesc(userId)
                .orElse(null);
    }

    @Override
    public PostUsernameProtocol getPost(Long postId) {
        PostUsernameProtocol pup = null;
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isPresent()){
            Optional<User> user = this.userRepository.findById(post.get().getUserId());
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
                    p.setTitle(Optional.ofNullable(post.getTitle()).orElse(p.getTitle()));
                    p.setContent(Optional.ofNullable(post.getContent()).orElse(p.getContent()));
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
    public boolean deletePost(Long postId) {
        try {
            this.postRepository.deleteById(postId);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
