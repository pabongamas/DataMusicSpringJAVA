package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import com.datamusic.datamusic.domain.User;

public interface UserRepository {
    List<User> getAll();

    Optional<User> getUserById(Long idUser);

    User save(User user);
}
