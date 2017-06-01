package org.bsuir.labs.spp.service;

import org.bsuir.labs.spp.config.Constants;
import org.bsuir.labs.spp.domain.Authority;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.repository.AuthorityRepository;
import org.bsuir.labs.spp.repository.UserRepository;
import org.bsuir.labs.spp.security.AuthoritiesConstants;
import org.bsuir.labs.spp.security.SecurityUtils;
import org.bsuir.labs.spp.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public User createUser(String login, String password, String firstName, String lastName, String email) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setActivated(false);
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        return newUser;
    }

    public User createUser(User userEntity) {
        User user = new User();
        user.setLogin(userEntity.getLogin());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        if (userEntity.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userEntity.getAuthorities().forEach(
                authority -> authorities.add(authority)
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setActivated(true);
        userRepository.save(user);
        return user;
    }

    public void updateUser(String firstName, String lastName, String email) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
        });
    }

    public Optional<User> updateUser(User userEntity) throws IOException {
        return Optional.of(userRepository
            .findOne(userEntity.getId()))
            .map(user -> {
                if (user.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toList()).contains(AuthoritiesConstants.ADMIN)
                    && !userEntity.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toList()).contains(AuthoritiesConstants.ADMIN)
                    && userRepository.findAllByAuthoritiesContains(new Authority(AuthoritiesConstants.ADMIN)).size() == 1) {
                    return null;
                }

                user.setLogin(userEntity.getLogin());
                user.setFirstName(userEntity.getFirstName());
                user.setLastName(userEntity.getLastName());
                user.setEmail(userEntity.getEmail());
                user.setActivated(userEntity.getActivated());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userEntity.getAuthorities().stream()
                    .forEach(managedAuthorities::add);
                return user;
            })
            .map(User::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent((User user) -> {
            userRepository.delete(user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
        });
    }

    @Transactional(readOnly = true)
    public Page<User> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(User::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }


    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            userRepository.delete(user);
        }
    }

    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }
}
