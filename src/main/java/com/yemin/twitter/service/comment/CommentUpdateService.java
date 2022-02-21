package com.yemin.twitter.service.comment;


import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.comment.CommentDTO;
import com.yemin.twitter.repository.CommentRepository;
import com.yemin.twitter.service.member.MemberFindService;
import com.yemin.twitter.service.post.PostFindService;
import com.yemin.twitter.service.post.PostUpdateService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;

@Service
public class CommentUpdateService {
    CommentRepository commentRepository;

    CommentFindService commentFindService;
    PostFindService postFindService;
    PostUpdateService postUpdateService;
    MemberFindService memberFindService;

    public CommentUpdateService(CommentRepository commentRepository, CommentFindService commentFindService, PostFindService postFindService, PostUpdateService postUpdateService, MemberFindService memberFindService) {
        this.commentRepository = commentRepository;
        this.commentFindService = commentFindService;
        this.postFindService = postFindService;
        this.postUpdateService = postUpdateService;
        this.memberFindService = memberFindService;
    }

    public CommentVO save(CommentVO comment) {

        return commentRepository.save(comment);
    }

    @Transactional
    public CommentDTO save(Long idx, CommentDTO commentDTO) throws NotFoundException, AuthException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();
        PostVO post = postFindService.findByIdx(idx);

        CommentVO comment = commentDTO.toEntity();
        post.addComment(comment);
        member.addComment(comment);

        this.save(comment);

        return comment.dto(false, false);
    }

    @Transactional
    public CommentDTO update(Long idx, CommentDTO commentDTO) throws AuthException, NotFoundException {
        MemberVO member = memberFindService.getMyUserWithAuthorities();
        CommentVO comment = commentFindService.findByIdx(idx);

        if (comment.getMember() != member)
            throw new AuthException("not your own comment");

        comment.update(commentDTO.getContent());
        this.save(comment);

        return comment.dto(false, false);
    }

    @Transactional
    public void delete(Long idx) throws NotFoundException, AuthException {
        MemberVO member = memberFindService.getMyUserWithAuthorities();

        CommentVO comment = commentFindService.findByIdx(idx);

        if (comment.getMember() != member)
            throw new AuthException("not your own comment");

        commentRepository.delete(comment);


    }


}
