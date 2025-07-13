package com.exam.prep.data_service.jpa;

import com.exam.prep.data_service.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationJpa extends JpaRepository<UserInformation, Long> {
}
