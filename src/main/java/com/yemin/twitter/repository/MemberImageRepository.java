package com.yemin.twitter.repository;

import com.yemin.twitter.domain.MemberImageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImageVO, Long> {

    public Optional<MemberImageVO> findByName(String name);

}