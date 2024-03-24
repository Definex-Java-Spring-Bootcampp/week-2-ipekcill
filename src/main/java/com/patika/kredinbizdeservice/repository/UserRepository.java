package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final AtomicLong idCounter = new AtomicLong();
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        user.setId(idCounter.incrementAndGet());
        users.add(user);
        return user;
    }


    public List<User> getAll() {
        return users;
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Optional<User> findById(Long id) {

        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

}
