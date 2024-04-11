package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UpdateEmail;
import com.erp.accesscontrol.model.UpdatePassword;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserModel user) {
        if(user.getId() == null)
        {
            user.setCreationDate(LocalDateTime.now());
            user.setUpdateDate(null);
            user.setIsEnabled(false);
            user.setIsAccountNonLocked(false);
            user.setEmailIsConfirmed(false);
            logger.info("Start the creation of user");
            //envio de confirmacion al correo
            userRepository.save(user);
        }
        else throw new RequestException("The user id must be null","400-Bad Request");
    }

    @Override
    public void updateUser(UserModel updateUser, String token) {
        jwtService.validate(token);
        String username = jwtService.extractUsername(token);
        UserModel supportOrAdmin =  userRepository.findByUsername(jwtService.extractUsername(token)).orElseThrow(() -> new RequestException("Username "+jwtService.extractUsername(token)+" not found","404-Not found"));
        UserModel user =  userRepository.findById(updateUser.getId()).orElseThrow(() -> new RequestException("Username "+jwtService.extractUsername(token)+" not found","404-Not found"));
        if(!updateUser.getUsername().equals(username) && supportOrAdmin.getRole().getName().equals("support"))
        {
            logger.info("Start the update of user");
            userRepository.save(updateUsers(updateUser, user));
        } else if (supportOrAdmin.getRole().getName().equals("admin")) {
            logger.info("Start the update of user");
            userRepository.save(updateUsers(updateUser, user));
        }
        else throw new RequestException("It's not authorized","400-Bad Request");
    }

    @Override
    public void updateEmail(UpdateEmail updateEmail) {

        if(updateEmail.username().equals(jwtService.extractUsername(updateEmail.token())))
        {
            UserModel user = userRepository.findByUsername(updateEmail.username()).orElseThrow(() -> new RequestException("Username "+updateEmail.username()+" not found","404-Not found"));
            user.setEmail(updateEmail.newEmail());
            user.setEmailIsConfirmed(false);
            user.setUpdateDate(LocalDateTime.now());
            //enviar confirmacion de correo
            logger.info("Start the update email of user");
            userRepository.save(user);
        }
        else throw new RequestException("It's not authorized","400-Bad Request");
    }

    @Override
    public void updatePassword(UpdatePassword updatePassword) {
        jwtService.validate(updatePassword.token());
        String username = jwtService.extractUsername(updatePassword.token());
        if(updatePassword.username().equals(username))
        {
            UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username "+username+" not found","404-Not found"));
            if(passwordEncoder.matches(updatePassword.password(), user.getPassword()))
            {
                user.setPassword(passwordEncoder.encode(updatePassword.newPassword()));
                user.setUpdateDate(LocalDateTime.now());
                user.setIsAccountNonLocked(true);
                logger.info("Start the update password of user");
                userRepository.save(user);
            }
            else throw new RequestException("The old password is not correct","400-Bad Request");
        } else throw new RequestException("It's not authorized","400-Bad Request");
    }

    @Override
    public void changePasswordFromSupport(String username) { //no lleva token porque los unicos que tendran permiso de usar el metodo seran los de soporte o admin
        String password = RandomStringUtils.random(11,true,true)+"*";
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username "+username+" not found","404-Not found"));
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdateDate(LocalDateTime.now());
        user.setIsAccountNonLocked(true);
        //enviar al correo del user la contraseña generada
        logger.info("Start the change password of user");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {//no se pone token porque solo tiene acceso al metodo soporte y admin
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Username "+username+" not found","404-Not found"));
        user.setIsEnabled(false);
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);
    }

    private UserModel updateUsers(UserModel updateUser, UserModel user)
    {
        if(!user.getEmail().equals(updateUser.getEmail()))
        {
            user.setEmail(updateUser.getEmail());
            user.setEmailIsConfirmed(false);
            //envio de confirmacion al correo
        }
        user.setUsername(updateUser.getUsername());
        user.setFullName(updateUser.getFullName());
        user.setLastName(updateUser.getLastName());
        user.setRole(updateUser.getRole());
        user.setUpdateDate(LocalDateTime.now());
        return user;
    }
}
