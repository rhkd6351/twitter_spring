package com.yemin.twitter.repository;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.dto.member.PageMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Long> {

    public boolean existsByEmail(String email);

    public Optional<MemberVO> findOneByEmail(String email);

    @Query(
            value = "SELECT m FROM MemberVO m WHERE m.username LIKE %:username%",
            countQuery = "SELECT COUNT(m.username) FROM MemberVO m WHERE m.username LIKE %:username%"
    )
    Page<MemberVO> findAllByUsername(String username, Pageable pageable);


}