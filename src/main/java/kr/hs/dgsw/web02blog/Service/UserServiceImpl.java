package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User user) {
        Optional<User> found = this.userRepository.findByAccount(user.getAccount());
        if(found.isPresent()){
            return null;
        }
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.findById(user.getId())
                .map(u -> {
                    u.setAccount(Optional.ofNullable(user.getAccount()).orElse(u.getAccount()));
                    u.setEmail(Optional.ofNullable(user.getEmail()).orElse(u.getEmail()));
                    u.setPassword(Optional.ofNullable(user.getPassword()).orElse(u.getPassword()));
                    u.setPhone(Optional.ofNullable(user.getPhone()).orElse(u.getPhone()));
                    u.setName(Optional.ofNullable(user.getName()).orElse(u.getName()));
                    u.setProfilePath(Optional.ofNullable(user.getProfilePath()).orElse(u.getProfilePath()));
                    u.setProfileName(Optional.ofNullable(user.getProfileName()).orElse(u.getProfileName()));
                    return this.userRepository.save(u);
                })
                .orElse(null);
    }

    @Override
    public boolean deleteUser(Long userId) {
        User found = this.getUser(userId);
        if(found != null){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
