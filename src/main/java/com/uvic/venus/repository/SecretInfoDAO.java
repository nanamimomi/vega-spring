package com.uvic.venus.repository;

import com.uvic.venus.model.SecretInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecretInfoDAO extends JpaRepository<SecretInfo, UUID> {
}
