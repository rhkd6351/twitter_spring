package com.yemin.twitter.repository;

import com.yemin.twitter.domain.CommentVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentVO, Integer> {

}