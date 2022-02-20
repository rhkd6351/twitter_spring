package com.yemin.twitter.controller.comment;


import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.MessageDTO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.comment.CommentDTO;
import com.yemin.twitter.dto.comment.PageCommentDTO;

import com.yemin.twitter.dto.post.PagePostDTO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.service.comment.CommentFindService;
import com.yemin.twitter.service.comment.CommentUpdateService;
import com.yemin.twitter.service.post.PostFindService;
import org.springframework.http.ResponseEntity;
import javassist.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.function.Supplier;

@Log
@RestController
@RequestMapping("/api")
public class CommentController {

    CommentFindService commentFindService;
    CommentUpdateService commentUpdateService;
    PostFindService postFindService;
    public CommentController(CommentFindService commentFindService, CommentUpdateService commentUpdateService,PostFindService postFindService) {
        this.commentFindService = commentFindService;
        this.commentUpdateService = commentUpdateService;
        this.postFindService=postFindService;
    }

    @GetMapping("/posts/comments") // 전체 댓글 조회
    public ResponseEntity<PageCommentDTO> getPostComments(
            @PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable) {

        PageCommentDTO page = commentFindService.findAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/posts/comments/{comment-idx}") // 단일 댓글 조회
    public ResponseEntity<CommentDTO> getCommentByIdx(
            @PathVariable(value = "comment-idx") Long idx) throws NotFoundException {

        CommentVO comment = commentFindService.findByIdx(idx);
        CommentDTO dto = comment.dto(false, false);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //post별 댓글리스트 가져오기
    @GetMapping("/posts/{post-idx}/comments")
    public ResponseEntity<PageCommentDTO> getCommentsByPost(
            @PathVariable (value = "post-idx")Long idx,
            @PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable
    )throws NotFoundException{
        PostVO post=postFindService.findByIdx(idx);
        PageCommentDTO page = commentFindService.findByPost(post,pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //내가 쓴 댓글 리스트 가져오기
    @GetMapping("/members/comments")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PageCommentDTO> getAllMyComments(

            @PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable)throws
            AuthException
    {
        PageCommentDTO page=commentFindService.findAllWithAuth(pageable);
        return new ResponseEntity<>(page,HttpStatus.OK);
    }

    @PostMapping("/posts/{post-idx}/comments") // 댓글 생성
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable(value = "post-idx") Long idx,
            @RequestBody @Validated(ValidationGroups.commentSaveGroup.class) CommentDTO commentDTO) throws NotFoundException, AuthException {

        CommentDTO savedComment = commentUpdateService.save(idx, commentDTO);

        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }


    @PutMapping("/posts/comments/{comment-idx}") // 댓글 수정
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentDTO> modifyComment(
            @PathVariable(value = "comment-idx") Long idx,
            @RequestBody @Validated(ValidationGroups.commentUpdateGroup.class) CommentDTO commentDTO
    ) throws AuthException, NotFoundException {

        CommentDTO updatedComment = commentUpdateService.update(idx, commentDTO);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    @DeleteMapping("/posts/comments/{comment-idx}") // 댓글 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageDTO> deleteComment(
            @PathVariable(value = "comment-idx") Long idx)
            throws AuthException, NotFoundException {

        commentUpdateService.delete(idx);

        return new ResponseEntity<>(new MessageDTO("delete success"), HttpStatus.OK);
    }

}