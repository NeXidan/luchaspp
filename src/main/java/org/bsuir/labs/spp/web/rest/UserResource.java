package org.bsuir.labs.spp.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bsuir.labs.spp.config.Constants;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.repository.UserRepository;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.service.UserService;
import org.bsuir.labs.spp.web.rest.util.HeaderUtil;
import org.bsuir.labs.spp.web.rest.util.PaginationUtil;
import org.bsuir.labs.spp.web.rest.vm.UserVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final UserService userService;

    public UserResource(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody UserVM userVM) throws URISyntaxException {
        if (userVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        } else if (userRepository.findOneByLogin(userVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(userVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(new User(userVM));
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserVM userVM) {
        Optional<User> existingUser = userRepository.findOneByEmail(userVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(userVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<User> updatedUser = null;
        try {
            updatedUser = userService.updateUser(new User(userVM));
        } catch (IOException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "lastadminrole", "You cannot remove last admin role")).body(null);
        }

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + userVM.getLogin(), userVM.getLogin()));
    }

    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<User>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<User> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<User> getUser(@PathVariable String login) {
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(User::new));
    }

    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }
}
