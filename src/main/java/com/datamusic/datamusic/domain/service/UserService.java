package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Iterator;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.UserRol;
import com.datamusic.datamusic.domain.repository.UserRepository;
import com.datamusic.datamusic.persistence.entity.Usuario;
import com.datamusic.datamusic.persistence.entity.UsuarioRol;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAll() {
        List<UserEntity> usersAll = userRepository.getAll();
        Iterator<UserEntity> iteratorUser = usersAll.iterator();

        while (iteratorUser.hasNext()) {
            UserEntity user = iteratorUser.next();
            user.setPassword(null);
            List<UserRol> userRol = user.getRols();
            for (UserRol userRolInd : userRol) {
                userRolInd.setUser(null);
            }
        }

        return usersAll;
    }

    public Optional<UserEntity> getUserById(Long idUser) {
        Optional<UserEntity> userById = userRepository.getUserById(idUser);
        userById.map(user -> {
            user.setPassword(null);
            List<UserRol> userRol = user.getRols();
            Iterator<UserRol> userRolIterator = userRol.iterator();
            while (userRolIterator.hasNext()) {
                UserRol userRoleIndividual = userRolIterator.next();
                userRoleIndividual.setUser(null);
            }
            return user;
        });
        return userById;
    }

    public UserEntity saveUser(UserEntity user, boolean create) {
        if (create) {
            Optional<UserEntity> existsUserByEmail = userRepository.getUserByEmail(user.getEmail());
            if (!existsUserByEmail.isEmpty()) {
                throw new DataIntegrityViolationException("Este email ya se encuentra registrado,este debe ser unico.");
            }
        }

        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        UserEntity userSaved = userRepository.save(user);
        userSaved.setPassword(null);
        return userSaved;
    }

    public boolean delete(Long userId) {
        return getUserById(userId).map(user -> {
            userRepository.delete(userId);
            return true;
        }).orElse(false);
    }

    public Page<UserEntity> getAllByPage(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        Page<UserEntity> pageUser = userRepository.getAllPage(pageRequest);
        //logica para no mostrar infoirmacion no necesaria en los enpoint de usuario
        Iterator<UserEntity> userIterator = pageUser.getContent().iterator();
        while (userIterator.hasNext()) {
            UserEntity user = userIterator.next();
            user.setPassword(null);
            List<UserRol> userRol = user.getRols();
            Iterator<UserRol> userRolIterator = userRol.iterator();
            while (userRolIterator.hasNext()) {
                UserRol userRoleIndividual = userRolIterator.next();
                userRoleIndividual.setUser(null);
            }
        }

        return pageUser;
    }

}
