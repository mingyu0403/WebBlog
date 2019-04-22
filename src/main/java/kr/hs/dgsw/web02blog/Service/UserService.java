package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUser(Long userId);
    User addUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long userId);

}
