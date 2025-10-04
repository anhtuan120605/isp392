package bin.spring.demo.demologin.service;

import bin.spring.demo.demologin.model.User;
import bin.spring.demo.demologin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Login
    public Optional<User> login(String username, String password){
        Optional<User> foundUser = userRepository.findByUsername(username);
        if(foundUser.isPresent()){
            if(passwordEncoder.matches(password, foundUser.get().getPassword())){
                return foundUser;
            }
        }
        return Optional.empty();
    }

}
