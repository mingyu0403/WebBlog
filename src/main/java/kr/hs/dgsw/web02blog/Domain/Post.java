package kr.hs.dgsw.web02blog.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> pictures;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;


    // 서버 시작 시 만들어놓기 위해 필수 데이터들로만 생성
    public Post(Long userId, String title, String content){
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post(Post p){
        this.id = p.getId();
        this.userId = p.getUserId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.created = p.getCreated();
        this.modified = p.getModified();
    }

}
