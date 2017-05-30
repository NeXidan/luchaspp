package org.bsuir.labs.controllers;

import org.bsuir.labs.controllers.contracts.BasicController;
import org.bsuir.labs.entities.UsersEntity;
import org.bsuir.labs.repositories.UsersRepository;
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
    private UsersRepository userRepository;

    private static UsersEntity current;

    private void setCurrent(UsersEntity user) {
        UserController.current = user;
    }

    private UsersEntity getCurrent() {
        if (UserController.current == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            setCurrent(userRepository.findByUsername(name));
        }

        return UserController.current;
    }

    @Transactional
    @GetMapping("/me")
    public ResponseEntity<UsersEntity> me() {
        return this.success(this.getCurrent());
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<UsersEntity> update(@PathVariable("id") Integer id, @RequestBody UsersEntity entity) {
        UsersEntity dbEntity = this.userRepository.findOne(id);
        if (dbEntity == null) {
            return this.error(HttpStatus.NOT_FOUND);
        }

        dbEntity.setFullName(entity.getFullName());
        dbEntity.setPhoneNumber(entity.getPhoneNumber());
        dbEntity.setPassword(entity.getPassword());
        dbEntity.setUsername(entity.getUsername());

        this.userRepository.save(dbEntity);

        return this.success(dbEntity);
    }

}

