package com.yemin.twitter.service.comment;

import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.comment.CommentDTO;
import com.yemin.twitter.dto.comment.PageCommentDTO;
import com.yemin.twitter.repository.CommentRepository;
import com.yemin.twitter.repository.PostRepository;
import com.yemin.twitter.service.member.MemberFindService;
import com.yemin.twitter.service.post.PostFindService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentFindService {

    //하나의 서비스에는 하나의 레파지토리만 가짐
    // 이후 다른 서비스 이용할 수 있다.
    CommentRepository commentRepository;
    MemberFindService memberFindService;
    PostFindService postFindService;


    public CommentFindService(CommentRepository commentRepository, MemberFindService memberFindService, PostFindService postFindService) {
        this.commentRepository = commentRepository;
        this.memberFindService = memberFindService;
        this.postFindService = postFindService;


    }

    @Transactional(readOnly = true)
    public CommentVO findByIdx(Long commentIdx) throws NotFoundException {
        //게시글의 댓글 단건 조회

        Optional<CommentVO> comment = commentRepository.findById(commentIdx);

        //만약에 comment가 존재하지 않다면, 생성되지 않은 댓글의 idx라고 예외 반환
        if (comment.isEmpty())
            throw new NotFoundException("invalid idx of comment");

        return comment.get();
    }

    @Transactional(readOnly = true)
    public PageCommentDTO findAll(Pageable pageable) {

        Page<CommentVO> page = commentRepository.findAll(pageable);

        //페이징 값이 담겨있는 page를 dto로 변환
        List<CommentDTO> comments = page.stream().map(i -> i.dto(true, false)).collect(Collectors.toList());

        return PageCommentDTO.builder()
                .comments(comments)
                .currentPage(pageable.getPageNumber())
                .totalPage(page.getTotalPages() - 1)
                .build();

    }

    @Transactional(readOnly = true)
    public PageCommentDTO findByPost(PostVO post,Pageable pageable) {

        Page<CommentVO> page = commentRepository.findByPost(post,pageable);

        //페이징 값이 담겨있는 page를 dto로 변환
        List<CommentDTO> comments = page.stream().map(i -> i.dto(true, false)).collect(Collectors.toList());

        return PageCommentDTO.builder()
                .comments(comments)
                .currentPage(pageable.getPageNumber())
                .totalPage(page.getTotalPages() - 1)
                .build();

    }


    @Transactional(readOnly = true)
    public PageCommentDTO findAllWithAuth(Pageable pageable)throws AuthException {
        MemberVO member = memberFindService.getMyUserWithAuthorities();

        Page<CommentVO> page = commentRepository.findAllByMember(member, pageable);
        List<CommentDTO> comments = page.stream().map(i -> i.dto(true, true)).collect(Collectors.toList());
        return PageCommentDTO.builder()
                .comments(comments)
                .currentPage(pageable.getPageNumber())
                .totalPage(page.getTotalPages() - 1)
                .totalPage(page.getTotalPages()-1)
                .build();

    }

}
