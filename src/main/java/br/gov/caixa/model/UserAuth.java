package br.gov.caixa.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "user_auth")
@UserDefinition
public class UserAuth extends PanacheEntity {

    @Username
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Password
    private String password;

    @Roles
    private String role;

    protected UserAuth() {
    }

    public UserAuth(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static void add (String username,String email, String password, String role){
        UserAuth userAuth = new UserAuth();
        userAuth.username = username;
        userAuth.email = email;
        userAuth.password = BcryptUtil.bcryptHash(password);
        userAuth.role = role;
        userAuth.persist();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
