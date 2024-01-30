package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.User;
import com.datamusic.datamusic.domain.repository.UserRepository;
import com.datamusic.datamusic.persistence.crud.UsuarioCrudRepository;
import com.datamusic.datamusic.persistence.entity.Usuario;
import com.datamusic.datamusic.persistence.mapper.UserMapper;

@Repository
public class UsuarioRepository implements UserRepository {

    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> getAll() {
       List<Usuario> usuarios=(List<Usuario>) usuarioCrudRepository.findAll();
       return mapper.toUser(usuarios);
    }

    @Override
    public Optional<User> getUserById(Long idUser) {
        return usuarioCrudRepository.findById(idUser).map(user->mapper.toUser(user));
    }

    @Override
    public User save(User user) {
        Usuario usuario=mapper.toUsuario(user);
        return mapper.toUser(usuarioCrudRepository.save(usuario));
    }
    
}
