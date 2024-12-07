package com.group11.repository;

import com.group11.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAllByRoleName(String roleName, Pageable pageable);
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);

    @Query("SELECT u FROM UserEntity u " +
            "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<UserEntity> searchUser(@Param("keyword") String keyword, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.active = :active WHERE u.userID = :userId")
    int updateUserStatus(@Param("userId") Long userId, @Param("active") Boolean active);
}

