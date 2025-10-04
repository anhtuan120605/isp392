package bin.spring.demo.demologin.controller;


import bin.spring.demo.demologin.model.User;
import bin.spring.demo.demologin.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        userService.register(user);
        return "User registered successfully!";
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //Login
    @PostMapping("/login")
    public String login(@RequestBody User user){
        Optional<User> foundUser = userService.login(user.getUsername(), user.getPassword());
        if(foundUser.isPresent()){
            return "Login successful! Welcome "+ foundUser.get().getUsername();
        } else {
            return "Invalid username or password";
        }
    }


}
