package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.repository.MemberRepository;
import com.yemin.twitter.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;
import java.util.Optional;

@Service
public class MemberFindService {

    @Autowired
    MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberVO getMyUserWithAuthorities() throws AuthException {
        Optional<MemberVO> userVO = SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneByEmail);

        if(userVO.isEmpty())
            throw new AuthException("invalid user or token");

        return userVO.get();
    }
}