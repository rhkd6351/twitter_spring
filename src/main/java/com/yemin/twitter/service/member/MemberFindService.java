package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.FileInfo;
import com.yemin.twitter.domain.MemberImageVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostImageVO;
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
import java.util.Optional;


@Service
public class MemberFindService {

    @Autowired
    MemberRepository memberRepository;
    MemberImageRepository memberImageRepository;
    MemberImageService memberImageService;

    @Transactional(readOnly = true)
    public MemberVO getMyUserWithAuthorities() throws AuthException {
        Optional<MemberVO> memberVO = SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneByEmail);

        if(memberVO.isEmpty())
            throw new AuthException("invalid member or token");

        return memberVO.get();
    }




    }
