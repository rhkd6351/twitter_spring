package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.dto.member.PageMemberDTO;
import com.yemin.twitter.repository.MemberRepository;
import com.yemin.twitter.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.message.AuthException;
import java.util.Optional;

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
    public Page<PageMemberDTO>findMember(String username,Pageable pageable)
    {
        Page <MemberVO> memberList=memberRepository.findAllByUsername(username,pageable);
        Page<PageMemberDTO> memberPagingList=memberList.map(
                member->new PageMemberDTO(
                        member.getIdx(),
                        member.getUsername(),
                        member.getMemberImage()

                )
        );
        return memberPagingList;

    }



}
