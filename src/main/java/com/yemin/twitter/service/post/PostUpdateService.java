package com.yemin.twitter.service.post;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostImageVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.repository.PostRepository;
import com.yemin.twitter.service.member.MemberFindService;
import com.yemin.twitter.service.member.MemberUpdateService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;

@Service
public class PostUpdateService {

    PostRepository postRepository;

    PostFindService postFindService;
    MemberFindService memberFindService;
    MemberUpdateService memberUpdateService;
    PostImageService postImageService;

    public PostUpdateService(PostRepository postRepository, PostFindService postFindService, MemberFindService memberFindService, MemberUpdateService memberUpdateService, PostImageService postImageService) {
        this.postRepository = postRepository;
        this.postFindService = postFindService;
        this.memberFindService = memberFindService;
        this.memberUpdateService = memberUpdateService;
        this.postImageService = postImageService;
    }

    public PostVO save(PostVO post) {
        return postRepository.save(post);
    }

    //TODO image 등록 로직 추가해야함
    @Transactional
    public PostDTO save(PostDTO postDTO, List<MultipartFile> mfList) throws AuthException, IOException, NotSupportedException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();

        PostVO post = postDTO.toEntity();
        post.setMember(member);

        PostVO savedPost = this.save(post);

        if (mfList != null) {
            for (MultipartFile mf : mfList) {
                PostImageVO savedPostImage = postImageService.save(mf, post);
                savedPost.addImage(savedPostImage);
            }
        }

        return savedPost.dto(false, false, true);
    }

    //title, content만 수정
    //TODO image 수정 로직도 추가해야함
    @Transactional
    public PostDTO update(Long idx, PostDTO postDTO, List<MultipartFile> mfList) throws AuthException, NotFoundException, IOException, NotSupportedException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();
        PostVO post = postFindService.findByIdx(idx);

        if (post.getMember() != member)
            throw new AuthException("not your own post");

        post.update(postDTO.getTitle(), postDTO.getContent());
        post.clearImages();

        if (mfList != null) {
            for (MultipartFile mf : mfList) {
                PostImageVO savedPostImage = postImageService.save(mf, post);
                post.addImage(savedPostImage);
            }
        }

        this.save(post);

        return post.dto(false, false, true);
    }

    @Transactional
    public void delete(Long idx) throws NotFoundException, AuthException {
        MemberVO member = memberFindService.getMyUserWithAuthorities();
        PostVO post = postFindService.findByIdx(idx);

        if (post.getMember() != member)
            throw new AuthException("not your own post");

        postRepository.delete(post);
    }
}