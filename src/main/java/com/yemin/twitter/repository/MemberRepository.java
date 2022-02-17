package com.yemin.twitter.repository;

import com.yemin.twitter.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Long> {

    public boolean existsByEmail(String email);

    public Optional<MemberVO> findOneByEmail(String email);

}