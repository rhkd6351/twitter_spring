package com.yemin.twitter.service.post;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.repository.PostRepository;
import com.yemin.twitter.service.member.MemberFindService;
import com.yemin.twitter.service.member.MemberUpdateService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;

@Service
public class PostUpdateService {

    PostRepository postRepository;

    PostFindService postFindService;
    MemberFindService memberFindService;
    MemberUpdateService memberUpdateService;

    public PostUpdateService(PostRepository postRepository, PostFindService postFindService, MemberFindService memberFindService, MemberUpdateService memberUpdateService) {
        this.postRepository = postRepository;
        this.postFindService = postFindService;
        this.memberFindService = memberFindService;
        this.memberUpdateService = memberUpdateService;
    }

    public PostVO save(PostVO post){
        return postRepository.save(post);
    }

    //TODO image 등록 로직 추가해야함
    @Transactional
    public PostDTO save(PostDTO postDTO) throws AuthException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();

        PostVO post = postDTO.toEntity();
        member.addPost(post);
        memberUpdateService.save(member);

        return post.dto(false, false, false);
    }

    //title, content만 수정
    //TODO image 수정 로직도 추가해야함
    @Transactional
    public PostDTO update(Long idx, PostDTO postDTO) throws AuthException, NotFoundException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();
        PostVO post = postFindService.findByIdx(idx);

        if(post.getMember() != member)
            throw new AuthException("not your own post");

        post.update(postDTO.getTitle(), postDTO.getContent());
        this.save(post);

        return post.dto(false, false, false);
    }

    @Transactional
    public void delete(Long idx) throws NotFoundException, AuthException {
        MemberVO member = memberFindService.getMyUserWithAuthorities();
        PostVO post = postFindService.findByIdx(idx);

        if(post.getMember() != member)
            throw new AuthException("not your own post");

        postRepository.delete(post);
    }
}