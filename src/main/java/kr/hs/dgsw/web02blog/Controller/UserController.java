package kr.hs.dgsw.web02blog.Controller;


import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
import kr.hs.dgsw.web02blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/getAllUsers")
    public ResponseFormat getAllUsers(){
        return new ResponseFormat(
                ResponseType.USER_GET_ALL,
                this.userService.getAllUsers()
        );
    }

    @GetMapping("/getUser/{userId}")
    public ResponseFormat getUser(@PathVariable Long userId){
        return new ResponseFormat(
                ResponseType.USER_GET,
                this.userService.getUser(userId)
        );
    }

    @PostMapping("/addUser")
    public ResponseFormat addUser(@RequestBody User user){
        return new ResponseFormat(
                ResponseType.USER_ADD,
                this.userService.addUser(user)
        );
    }

    @PutMapping("/updateUser")
    public ResponseFormat updateUser(@RequestBody User user){
        return new ResponseFormat(
                ResponseType.USER_UPDATE,
                this.userService.updateUser(user)
        );
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseFormat deleteUser(@PathVariable Long userId){
        return new ResponseFormat(
                ResponseType.USER_DELETE,
                this.userService.deleteUser(userId)
        );
    }

}
