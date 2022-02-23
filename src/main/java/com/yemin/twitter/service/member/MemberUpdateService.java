package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.repository.MemberRepository;
import com.yemin.twitter.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.Optional;

@Service
public class MemberUpdateService {


    MemberRepository memberRepository;

    MemberImageService memberImageService;
    MemberFindService memberFindService;

    public MemberUpdateService(MemberRepository memberRepository, MemberImageService memberImageService, MemberFindService memberFindService) {
        this.memberRepository = memberRepository;
        this.memberImageService = memberImageService;
        this.memberFindService = memberFindService;
    }

    @Transactional
    public MemberVO save(MemberVO vo) {
        return memberRepository.save(vo);
    }

    @Transactional
    public MemberVO saveProfile(MultipartFile mf) throws AuthException, IOException, NotSupportedException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();
        memberImageService.save(mf, member);

        return member;
    }
}
