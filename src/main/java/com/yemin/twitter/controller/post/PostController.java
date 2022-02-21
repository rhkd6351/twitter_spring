package com.yemin.twitter.controller.post;


import com.yemin.twitter.domain.PostImageVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.MessageDTO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.post.PagePostDTO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.service.post.PostFindService;
import com.yemin.twitter.service.post.PostImageService;
import com.yemin.twitter.service.post.PostUpdateService;
import javassist.NotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {


    PostFindService postFindService;
    PostUpdateService postUpdateService;
    PostImageService postImageService;

    public PostController(PostFindService postFindService, PostUpdateService postUpdateService, PostImageService postImageService) {
        this.postFindService = postFindService;
        this.postUpdateService = postUpdateService;
        this.postImageService = postImageService;
    }

    @GetMapping("/posts")
    public ResponseEntity<PagePostDTO> getAllPosts(@PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){

        PagePostDTO page = postFindService.findAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/posts/{post-idx}")
    public ResponseEntity<PostDTO> getPostByIdx(@PathVariable(value = "post-idx")Long idx) throws NotFoundException {

        PostDTO dto = postFindService.findByIdxDTO(idx);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/member/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PagePostDTO> getAllMyPosts(@PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable) throws AuthException {

        PagePostDTO page = postFindService.findAllWithAuth(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(path = "/member/posts", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> savePost(
            @RequestPart(value = "img", required = false) List<MultipartFile> mfList,
            @RequestPart(value = "post") @Validated(ValidationGroups.postSaveGroup.class) PostDTO postDTO) throws AuthException, IOException, NotSupportedException {

        PostDTO savedPost = postUpdateService.save(postDTO, mfList);

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping(path = "/member/posts/{post-idx}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDTO> modifyPost(
            @RequestPart(value = "post") @Validated(ValidationGroups.postUpdateGroup.class) PostDTO postDTO,
            @RequestPart(value = "img", required = false) List<MultipartFile> mfList,
            @PathVariable(value = "post-idx")Long idx) throws AuthException, NotFoundException, IOException, NotSupportedException {

        PostDTO updatedPost = postUpdateService.update(idx, postDTO, mfList);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/member/posts/{post-idx}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageDTO> deletePost(
            @PathVariable(value = "post-idx")Long idx) throws AuthException, NotFoundException {

        postUpdateService.delete(idx);

        return new ResponseEntity<>(new MessageDTO("post deleted"), HttpStatus.OK);
    }


    @GetMapping(value = "/posts/images/{image-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPostImage(
            @PathVariable(value = "image-name")String imageName) throws NotFoundException, IOException {

        byte[] imageByte = postImageService.getByteByName(imageName);

        return new ResponseEntity<>(imageByte, HttpStatus.OK);
    }







}







