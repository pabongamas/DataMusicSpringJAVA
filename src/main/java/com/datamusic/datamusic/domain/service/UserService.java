package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.User;
import com.datamusic.datamusic.domain.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> getUserById(Long idUser) {
        return userRepository.getUserById(idUser);
    }

    public User saveUser(User user) {
        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

}
