package com.yemin.twitter.repository;

import com.yemin.twitter.domain.MemberImageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImageVO, Long> {

}