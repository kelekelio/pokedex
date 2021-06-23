package com.grzegorznowakowski.pokedex.user.controller;

import com.grzegorznowakowski.pokedex.user.entity.User;
import com.grzegorznowakowski.pokedex.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
@RequestMapping(value = "/users")
public class UserController
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //TODO: limit to ADMINs only
    //.antMatchers("/user/delete/**").hasAnyRole("ADMIN")
    @DeleteMapping("/user/delete/{name}")
    public void deleteUser(@RequestParam String name) {
        User user = userRepository.findByUsername(name);
        userRepository.delete(user);
    }

}