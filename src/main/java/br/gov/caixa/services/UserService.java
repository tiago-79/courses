package br.gov.caixa.services;

import br.gov.caixa.model.UserAuth;
import br.gov.caixa.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @PostConstruct
    void init() {
        Log.info("Inicializando agora...");
    }

    @Transactional
    public UserAuth createUser(UserAuth userAuth) {
        userAuth.persist();
        return userAuth;
    }

    public UserAuth authenticate(String email, String password) {
        UserAuth user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        if (!BcryptUtil.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    public UserAuth findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
