package com.fnb.usermanagement.repository;

import com.fnb.usermanagement.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<Credentials, UUID> {
}
