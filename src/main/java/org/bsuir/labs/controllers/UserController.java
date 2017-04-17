package org.bsuir.labs.controllers;


import org.bsuir.labs.controllers.contracts.BasicController;
import org.bsuir.labs.entities.UsersEntity;
import org.bsuir.labs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/user")
public class UserController extends BasicController<UsersEntity> {

    @Autowired
    UserRepository userRepository;

    private static UsersEntity current;

    void setCurrent(UsersEntity user) {
        current = user;
    }

    public UsersEntity getCurrent() {
        if (current == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            setCurrent(userRepository.findByUsername(name));
        }

        return current;
    }

    @Transactional
    @GetMapping("/me")
    public ResponseEntity<UsersEntity> me() {
        return success(getCurrent());
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<UsersEntity> update(@PathVariable("id") Integer id, @RequestBody UsersEntity entity) {
        UsersEntity dbEntity = userRepository.findOne(id);
        if (null == dbEntity) {
            return error(HttpStatus.NOT_FOUND);
        }

        dbEntity.setFullName(entity.getFullName());
        dbEntity.setPhoneNumber(entity.getPhoneNumber());
        dbEntity.setPassword(entity.getPassword());
        dbEntity.setUsername(entity.getUsername());

        userRepository.save(dbEntity);

        return success(dbEntity);
    }

}

