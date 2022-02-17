package com.yemin.twitter.repository;

import com.yemin.twitter.domain.PostVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostVO, Integer> {

}