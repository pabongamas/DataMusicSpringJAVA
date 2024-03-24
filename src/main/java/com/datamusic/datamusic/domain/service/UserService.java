package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public User saveUser(User user, boolean create) {
        if (create) {
            Optional<User> existsUserByEmail = userRepository.getUserByEmail(user.getEmail());
            if (!existsUserByEmail.isEmpty()) {
                throw new DataIntegrityViolationException("Este email ya se encuentra registrado,este debe ser unico.");
            }
        }

        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public boolean delete(Long userId) {
        return getUserById(userId).map(user -> {
            userRepository.delete(userId);
            return true;
        }).orElse(false);
    }

    public Page<User> getAllByPage(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return userRepository.getAllPage(pageRequest);
    }

}
