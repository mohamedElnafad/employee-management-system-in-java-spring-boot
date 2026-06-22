package com.quickStart.quickStart.repositories;

import com.quickStart.quickStart.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}


