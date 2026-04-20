package br.gov.caixa.repository;

import br.gov.caixa.model.UserAuth;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository  implements PanacheRepository<UserAuth> {

    public UserAuth findByEmail(String email) {
        return (UserAuth) find("email", email).firstResult();
    }

    public UserAuth findByName(String name) {
        return (UserAuth) find("username", name).firstResult();
    }
}
