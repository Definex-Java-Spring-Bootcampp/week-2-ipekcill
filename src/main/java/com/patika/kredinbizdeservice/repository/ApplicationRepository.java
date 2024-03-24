package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.Application;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ApplicationRepository {
    private final List<Application> applications = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Application save(Application application) {
        application.setId(idCounter.incrementAndGet());
        applications.add(application);
        return application;
    }

    public List<Application> getAll() {
        return applications;
    }

    public Optional<Application> findById(Long id) {
        return applications.stream()
                .filter(application -> application.getId().equals(id))
                .findFirst();

    }
}
