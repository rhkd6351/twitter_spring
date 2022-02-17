package com.yemin.twitter.repository;

import com.yemin.twitter.domain.AuthVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthVO, String> {

}