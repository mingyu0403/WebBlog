package kr.hs.dgsw.web02blog.Controller;


import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable Long userId){
        return this.userService.getUser(userId);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return this.userService.addUser(user);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user){
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public Boolean deleteUser(@PathVariable Long userId){
        return this.userService.deleteUser(userId);
    }

}
