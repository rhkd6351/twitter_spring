package com.yemin.twitter.repository;

import com.yemin.twitter.domain.CommentVO;
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

    Optional<CommentVO>findByPost(PostVO post, CommentVO comment);
    //특정 게시글애 작성한 단일 댓글 조회
}