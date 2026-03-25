package br.gov.caixa.services;

import br.gov.caixa.model.Course;
import br.gov.caixa.model.User;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @PostConstruct
    void init() {
        Log.info("Inicializando agora...");
    }

    @Transactional
    public User createUser(User user) {
        Log.info("Passing through " + this.getClass().getName() + " with course: " + user.toString());
        user.persist();
        return user;
    }


}
