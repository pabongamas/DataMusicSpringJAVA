package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.User;

public interface UserRepository {
    List<User> getAll();

    Optional<User> getUserById(Long idUser);

    User save(User user);
    void delete(Long userId);

    //metodos para paginar
    Page<User> getAllPage(Pageable pageable);
}
