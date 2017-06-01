package org.bsuir.labs.spp.web.rest.vm;

import org.bsuir.labs.spp.config.Constants;
import org.bsuir.labs.spp.domain.Authority;
import org.bsuir.labs.spp.domain.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserVM {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    private Set<Authority> authorities;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public UserVM() {

    }

    public UserVM(User user) {
        this(user.getId(), user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getAuthorities());
    }

    public UserVM(Long id, String login, String password, String firstName, String lastName,
                   String email, boolean activated, Set<Authority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.authorities = authorities;
    }


    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getActivated() {
        return activated;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserVM{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", authorities=" + authorities +
            "}";
    }
}
