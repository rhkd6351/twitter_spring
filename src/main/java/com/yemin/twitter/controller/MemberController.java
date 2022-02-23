package com.yemin.twitter.controller;

import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.dto.MessageDTO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.member.MemberDTO;
import com.yemin.twitter.service.member.MemberFindService;
import com.yemin.twitter.service.member.MemberImageService;
import com.yemin.twitter.service.member.MemberSignUpService;
import com.yemin.twitter.service.member.MemberUpdateService;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/api")
public class MemberController {


    MemberSignUpService memberSignUpService;
    MemberUpdateService memberUpdateService;
    MemberImageService memberImageService;
    MemberFindService memberFindService;

    public MemberController(MemberSignUpService memberSignUpService, MemberUpdateService memberUpdateService, MemberImageService memberImageService, MemberFindService memberFindService) {
        this.memberSignUpService = memberSignUpService;
        this.memberUpdateService = memberUpdateService;
        this.memberImageService = memberImageService;
        this.memberFindService = memberFindService;
    }

    @PostMapping("/member")
    public ResponseEntity<MemberDTO> getSignUp(@RequestBody @Validated(ValidationGroups.memberSignUpGroup.class) MemberDTO requestMemberDTO)
            throws DuplicateMemberException, NotFoundException {

        MemberDTO memberDTO = memberSignUpService.signUp(requestMemberDTO);

        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PutMapping("/member/image")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageDTO> updateProfile(
            @RequestPart(value = "img", required = true) MultipartFile mf) throws AuthException, IOException, NotSupportedException {

        MemberVO memberVO = memberUpdateService.saveProfile(mf);
        return new ResponseEntity<>(new MessageDTO("success"), HttpStatus.OK);
    }

    @GetMapping(value = "/member/image/{image-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getMemberImage(
            @PathVariable(value = "image-name")String imageName) throws NotFoundException, IOException {

        byte[] imageByte = memberImageService.getByteByName(imageName);

        return new ResponseEntity<>(imageByte, HttpStatus.OK);
    }

    @GetMapping("/member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MemberDTO> getMember() throws AuthException {

        MemberVO member = memberFindService.getMyUserWithAuthorities();

        return new ResponseEntity<>(member.dto(true), HttpStatus.OK);
    }


}









