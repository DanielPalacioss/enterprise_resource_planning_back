package com.erp.accesscontrol.service;

import com.erp.accesscontrol.dto.UpdatePasswordDTO;
import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.RoleRepository;
import com.erp.accesscontrol.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel getUser(String username) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username " + username + " not found", "404-Not found"));
        logger.info("Starting search by user id");
        return user;
    }

    @Override
    public void saveUser(UserModel user) {
        if(roleRepository.count() < 1) throw new RequestException("No role created","404-Not Found");
        if (user.getId() == null) {
            user.setCreationDate(LocalDateTime.now());
            user.setUpdateDate(null);
            user.setIsEnabled(true);
            user.setIsAccountNonLocked(true);
            user.setValidationQuestionsCompleted(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            logger.info("Start the creation of user");
            userRepository.save(user);
        } else throw new RequestException("The user id must be null", "400-Bad Request");
    }

    @Override
    public void updateUser(UserModel updateUser) {
        UserModel user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new RequestException("Username " + updateUser.getUsername() + " not found", "404-Not found"));
        logger.info("Start the update of user");
        if (!updateUser.getUsername().equals(user.getUsername()) && user.getRole().getName().equals("SUPPORT")) {
            userRepository.save(updateUsers(updateUser, user));
        } else if (user.getRole().getName().equals("ADMIN")) {
            userRepository.save(updateUsers(updateUser, user));
        } else throw new RequestException("It's not authorized", "400-Bad Request");
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePassword) {
        UserModel user = userRepository.findByUsername(updatePassword.username()).orElseThrow(() -> new RequestException("Username " + updatePassword.username() + " not found", "404-Not found"));
        if (passwordEncoder.matches(updatePassword.password(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(updatePassword.newPassword()));
            user.setUpdateDate(LocalDateTime.now());
            user.setIsAccountNonLocked(true);
            logger.info("Start the update password of user");
            userRepository.save(user);
        } else throw new RequestException("The old password is not correct", "400-Bad Request");
    }

    @Override
    public String changePasswordFromSupport(String username) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username " + username + " not found", "404-Not found"));
        String password = RandomStringUtils.random(11, true, true) + "*";
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdateDate(LocalDateTime.now());
        user.setIsAccountNonLocked(true);
        user.setIsEnabled(true);
        //enviar al correo del user la contraseña generada
        logger.info("Start the change password of user");
        userRepository.save(user);
        return password;
    }

    @Override
    public void deleteUser(String username) {//no se pone token porque solo tiene acceso al metodo soporte y admin
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username " + username + " not found", "404-Not found"));
        user.setIsEnabled(false);
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);
    }

    private UserModel updateUsers(UserModel updateUser, UserModel user) {
        user.setEmail(updateUser.getEmail());
        user.setUsername(updateUser.getUsername());
        user.setFullName(updateUser.getFullName());
        user.setLastName(updateUser.getLastName());
        user.setRole(updateUser.getRole());
        user.setUpdateDate(LocalDateTime.now());
        return user;
    }
}
