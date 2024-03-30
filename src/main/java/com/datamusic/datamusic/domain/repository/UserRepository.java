package com.datamusic.datamusic.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.datamusic.datamusic.domain.UserEntity;

public interface UserRepository {
    List<UserEntity> getAll();

    Optional<UserEntity> getUserById(Long idUser);
    Optional<UserEntity> getUserByEmail(String email);


    UserEntity save(UserEntity user);
    void delete(Long userId);

    //metodos para paginar
    Page<UserEntity> getAllPage(Pageable pageable);
}
