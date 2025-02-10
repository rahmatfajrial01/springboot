package com.backend.aji.repository;

import com.backend.aji.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
//    Optional<User> findByEmail(String email);
}