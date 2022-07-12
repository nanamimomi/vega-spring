package com.uvic.venus.repository;

import com.uvic.venus.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInfo, String> {
}
