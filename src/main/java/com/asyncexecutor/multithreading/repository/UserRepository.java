package com.asyncexecutor.multithreading.repository;

import com.asyncexecutor.multithreading.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
