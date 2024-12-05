package com.group11.repository;

import com.group11.entity.MediaRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRateRepository extends JpaRepository<MediaRateEntity, Long> {
}
