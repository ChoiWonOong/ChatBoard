package com.example.Board.contoller;

import com.example.Board.dto.UserDTO;
import com.example.Board.entity.user.UserEntity;
import com.example.Board.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserController(
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("login")
    public String login(){
        return "redirect:/";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/";
    }
    @GetMapping("signup")
    public String signUp(){
        return "signup";
    }
    @PostMapping("signup")
    public String signupPost(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("password_check")String passwordCheck){
        if(!password.equals(passwordCheck)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // Make New User
        UserEntity newUser = new UserEntity();
        // Set User Information
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        // Save Into DB
        userRepository.save(newUser);

        return "redirect:/";
    }
}
