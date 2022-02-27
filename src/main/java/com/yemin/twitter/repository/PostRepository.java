package com.yemin.twitter.repository;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostVO, Long> {

    public Page<PostVO> findAllByMember(MemberVO member, Pageable pageable);

    public Page<PostVO> findAllByContentContaining(String content, Pageable pageable);

    public Page<PostVO> findAll(Pageable pageable);

}