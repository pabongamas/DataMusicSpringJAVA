package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.repository.UserRepository;
import com.datamusic.datamusic.persistence.crud.UsuarioCrudRepository;
import com.datamusic.datamusic.persistence.entity.Usuario;
import com.datamusic.datamusic.persistence.entity.UsuarioRol;
import com.datamusic.datamusic.persistence.mapper.UserMapper;
import com.datamusic.datamusic.persistence.pageableAndSort.UserPagSortRepository;

@Repository
public class UsuarioRepository implements UserRepository {

    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;
    @Autowired
    private UserPagSortRepository userPagSortRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public List<UserEntity> getAll() {
        List<Usuario> usuarios = (List<Usuario>) usuarioCrudRepository.findAll();
        return mapper.toUser(usuarios);
    }

    @Override
    public Optional<UserEntity> getUserById(Long idUser) {
        return usuarioCrudRepository.findById(idUser).map(user -> {
            return mapper.toUser(user);
        });
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        Optional<Usuario> usuario = usuarioCrudRepository.findByCorreoElectronico(email);
        return usuario.map(user -> mapper.toUser(user));
    }

    @Override
    public UserEntity save(UserEntity user) {
        Usuario usuario = mapper.toUsuario(user);
        return mapper.toUser(usuarioCrudRepository.save(usuario));
    }

    @Override
    public void delete(Long userId) {
        usuarioCrudRepository.deleteById(userId);
    }

    @Override
    public Page<UserEntity> getAllPage(Pageable pageable) {
        Page<Usuario> userByPage = userPagSortRepository.findAll(pageable);
        List<Usuario> listUsuario = userByPage.getContent();
        List<UserEntity> listUserMapped = mapper.toUser(listUsuario);
        return new PageImpl<>(listUserMapped, pageable, userByPage.getTotalElements());
    }

}
