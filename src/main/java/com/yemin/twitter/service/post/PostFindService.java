package com.yemin.twitter.service.post;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.post.PagePostDTO;
import com.yemin.twitter.dto.post.PostDTO;
import com.yemin.twitter.repository.PostRepository;
import com.yemin.twitter.service.member.MemberFindService;
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
public class PostFindService {

    //하나의 서비스에는 하나의 repository만 가짐
    //이후 다른 서비스 이용할 수 있음
    PostRepository postRepository;
    MemberFindService memberFindService;

    public PostFindService(PostRepository postRepository, MemberFindService memberFindService) {
        this.postRepository = postRepository;
        this.memberFindService = memberFindService;
    }

    @Transactional(readOnly = true)
    public PostVO findByIdx(Long idx) throws NotFoundException {

        Optional<PostVO> post = postRepository.findById(idx);

        if(post.isEmpty())
            throw new NotFoundException("invalid idx of post");

        return post.get();
    }

    @Transactional(readOnly = true)
    public PostDTO findByIdxDTO(Long idx) throws NotFoundException {

        PostVO post = this.findByIdx(idx);

        return post.dto(true, false, true);
    }

    @Transactional(readOnly = true)
    public PagePostDTO findAll(Pageable pageable){

        Page<PostVO> page = postRepository.findAll(pageable);
        List<PostDTO> posts = page.stream().map(i -> i.dto(true, false, true)).collect(Collectors.toList());

        return PagePostDTO.builder()
                .posts(posts)
                .currentPage(pageable.getPageNumber())
                .totalPage(page.getTotalPages() - 1)
                .build();
    }

    @Transactional(readOnly = true)
    public PagePostDTO findAllWithAuth(Pageable pageable) throws AuthException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();

        Page<PostVO> page = postRepository.findAllByMember(member, pageable);
        List<PostDTO> posts = page.stream().map(i -> i.dto(true, false, true)).collect(Collectors.toList());

        return PagePostDTO.builder()
                .posts(posts)
                .currentPage(pageable.getPageNumber())
                .totalPage(page.getTotalPages() - 1)
                .build();
    }

}
