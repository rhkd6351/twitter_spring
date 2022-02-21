package com.yemin.twitter.repository;

import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.comment.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentVO, Long> {

    public Page<CommentVO> findAll(Pageable pageable);


public Page <CommentVO> findByPost(PostVO post,Pageable pageable);


    public Page<CommentVO> findAllByMember(MemberVO member, Pageable pageable);


}