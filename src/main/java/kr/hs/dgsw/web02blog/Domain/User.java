package kr.hs.dgsw.web02blog.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String account;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /*
    public void setPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes(), 0, password.getBytes().length);
            this.password =  new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            Logger logger = LoggerFactory.getLogger(User.class);
            logger.warn((e.getMessage()));
        }
    }
    */

    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String profilePath;
    private String profileName;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;

    // 서버 시작 시 만들어놓기 위해 필수 데이터들로만 생성
    public User(String account, String password, String name){
        this.account = account;
        this.password = password;
        this.name = name;
    }

}
