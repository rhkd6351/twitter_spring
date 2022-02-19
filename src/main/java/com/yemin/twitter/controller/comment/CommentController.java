package com.yemin.twitter.controller.comment;


import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.comment.CommentDTO;
import com.yemin.twitter.dto.comment.PageCommentDTO;

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

    public CommentController(CommentUpdateService commentUpdateService, CommentFindService commentFindService,PostFindService postFindService) {
        this.commentFindService = commentFindService;
        this.commentUpdateService = commentUpdateService;
        this.postFindService= postFindService;
    }


    @GetMapping("/comments") // 전체 댓글 조회
    public ResponseEntity<CommentDTO>getPostComments(
            @PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC)Pageable pageable)
    {

       PageCommentDTO page=commentFindService.findAll(pageable);

       return new ResponseEntity<>(page,HttpStatus.OK);
    }


    @GetMapping("/posts/{post-idx}comments/{comment-idx}") //단일 댓글 조회
    public ResponseEntity<CommentDTO> getCommentByIdx(@PathVariable(value = "post-idx") Long postIdx,@PathVariable(value = "comment-idx")Long idx) throws NotFoundException {

        CommentVO comment = commentFindService.findByPost(postIdx,idx);
        CommentDTO dto = comment.dto(false, false);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PostMapping("/post/{post-idx}/comments") // 댓글 생성
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable (value = "post-idx")Long idx,
            @RequestBody @Validated(ValidationGroups.commentSaveGroup.class)CommentDTO commentDTO) throws NotFoundException {

        CommentDTO savedComment=commentUpdateService.save(idx,commentDTO);
            return  new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }


    @PutMapping("/posts/{post-idx}/comments/{comment-idx}") // 댓글 수정
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentDTO> modifyComment(
            @PathVariable(value = "post-idx")Long postIdx,
            @PathVariable(value = "comment-idx") Long idx,
            @RequestBody @Validated(ValidationGroups.commentUpdateGroup.class) CommentDTO commentDTO
             )throws AuthException ,NotFoundException {

        CommentDTO updatedComment=commentUpdateService.update(postIdx,idx,commentDTO);

        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }


    @DeleteMapping("/posts/{post-idx}/comments/{comment-idx}") //댓글 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity <CommentDTO> deleteComment(
            @PathVariable(value = "post-idx") Long postIdx,
            @PathVariable(value = "comment-idx") Long idx,@RequestBody CommentDTO commentDTO)
            throws AuthException ,NotFoundException
    {
        CommentDTO deletedComment = commentUpdateService.delete(postIdx,idx,commentDTO);
        return new ResponseEntity<>(deletedComment,HttpStatus.OK);
    }

}