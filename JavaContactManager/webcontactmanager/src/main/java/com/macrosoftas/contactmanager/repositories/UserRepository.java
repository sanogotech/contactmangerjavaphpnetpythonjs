package com.macrosoftas.contactmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macrosoftas.contactmanager.entities.User;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long> {
}
