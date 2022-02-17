package com.yemin.twitter.service.member;


import com.yemin.twitter.domain.AuthType;
import com.yemin.twitter.domain.AuthVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.dto.member.MemberDTO;
import com.yemin.twitter.repository.MemberRepository;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberSignUpService {

    MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    AuthFindService authFindService;

    public MemberSignUpService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthFindService authFindService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authFindService = authFindService;
    }

    @Transactional
    public MemberDTO signUp(MemberDTO memberDTO) throws DuplicateMemberException, NotFoundException {

        if (memberRepository.existsByEmail(memberDTO.getEmail()))
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");

        AuthVO auth = authFindService.getByName(AuthType.USER.getName());
        MemberVO member = memberDTO.toEntity(passwordEncoder.encode(memberDTO.getPassword()), auth);

        return memberRepository.save(member).dto();
    }
}
