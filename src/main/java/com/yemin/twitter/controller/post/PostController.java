package com.yemin.twitter.controller.post;


import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.MessageDTO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.post.PagePostDTO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.service.post.PostFindService;
import com.yemin.twitter.service.post.PostUpdateService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/api")
public class PostController {


    PostFindService postFindService;
    PostUpdateService postUpdateService;

    public PostController(PostFindService postFindService, PostUpdateService postUpdateService) {
        this.postFindService = postFindService;
        this.postUpdateService = postUpdateService;
    }

    @GetMapping("/posts")
    public ResponseEntity<PagePostDTO> getAllPosts(@PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){

        PagePostDTO page = postFindService.findAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/posts/{post-idx}")
    public ResponseEntity<PostDTO> getPostByIdx(@PathVariable(value = "post-idx")Long idx) throws NotFoundException {

        PostVO post = postFindService.findByIdx(idx);
        PostDTO dto = post.dto(false, false, false);//TODO 댓글, 이미지 구현 후 comments 및 postImages true로 바꿔줘야함

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/member/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PagePostDTO> getAllMyPosts(@PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable) throws AuthException {

        PagePostDTO page = postFindService.findAllWithAuth(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/member/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> savePost(@RequestBody @Validated(ValidationGroups.postSaveGroup.class) PostDTO postDTO) throws AuthException {

        PostDTO savedPost = postUpdateService.save(postDTO);

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/member/posts/{post-idx}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> modifyPost(
            @RequestBody @Validated(ValidationGroups.postUpdateGroup.class) PostDTO postDTO,
            @PathVariable(value = "post-idx")Long idx) throws AuthException, NotFoundException {

        PostDTO updatedPost = postUpdateService.update(idx, postDTO);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/member/posts/{post-idx}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageDTO> modifyPost(
            @PathVariable(value = "post-idx")Long idx) throws AuthException, NotFoundException {

        postUpdateService.delete(idx);

        return new ResponseEntity<>(new MessageDTO("post deleted"), HttpStatus.OK);
    }






}







