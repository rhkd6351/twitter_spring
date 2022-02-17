package com.yemin.twitter.controller;

import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.member.MemberDTO;
import com.yemin.twitter.service.member.MemberSignUpService;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    MemberSignUpService memberSignUpService;


    @PostMapping("/member")
    public ResponseEntity<MemberDTO> getSignUp(@RequestBody @Validated(ValidationGroups.memberSignUpGroup.class) MemberDTO requestMemberDTO)
            throws DuplicateMemberException, NotFoundException {

        MemberDTO memberDTO = memberSignUpService.signUp(requestMemberDTO);

        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

}
