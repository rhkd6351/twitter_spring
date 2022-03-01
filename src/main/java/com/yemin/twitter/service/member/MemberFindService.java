package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.FileInfo;
import com.yemin.twitter.domain.MemberImageVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostImageVO;
import com.yemin.twitter.dto.member.MemberDTO;
import com.yemin.twitter.dto.member.PageMemberDTO;
import com.yemin.twitter.repository.MemberImageRepository;
import com.yemin.twitter.repository.MemberRepository;
import com.yemin.twitter.util.SecurityUtil;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MemberFindService {

    @Autowired
    MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public MemberVO getMyUserWithAuthorities() throws AuthException {
        Optional<MemberVO> memberVO = SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneByEmail);

        if(memberVO.isEmpty())
            throw new AuthException("invalid member or token");

        return memberVO.get();
    }

    @Transactional(readOnly = true)
    public PageMemberDTO getMembersByUsername(String username, Pageable pageable){

        Page<MemberVO> memberPage = memberRepository.findAllByUsernameContaining(username, pageable);

        List<MemberDTO> memberDTOList = memberPage.getContent().stream().map(i -> {
            return i.dto(true);
        }).collect(Collectors.toList());

        return PageMemberDTO.builder()
                .memberDTOList(memberDTOList)
                .currentPage(pageable.getPageNumber())
                .totalPage(memberPage.getTotalPages() - 1)
                .build();
    }
}
