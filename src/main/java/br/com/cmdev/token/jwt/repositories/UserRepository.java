package br.com.cmdev.token.jwt.repositories;

import br.com.cmdev.token.jwt.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<User, Integer> {
    UserDetails findByEmail(String email);
}

