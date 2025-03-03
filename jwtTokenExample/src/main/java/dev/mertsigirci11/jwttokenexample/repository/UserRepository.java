package dev.mertsigirci11.jwttokenexample.repository;

import dev.mertsigirci11.jwttokenexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUserEmail(String email);
}
