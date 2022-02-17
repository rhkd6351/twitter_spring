package com.yemin.twitter.repository;

import com.yemin.twitter.domain.PostImageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImageVO, Integer> {

}