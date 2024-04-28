package com.example.demo.Controller;

import com.example.demo.Dto.MemberDto;
import com.example.demo.Entity.Member;
import com.example.demo.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/test/register")
    public Member register(@RequestBody MemberDto MemberDto){
        boolean check = memberService.getEmailCheck(MemberDto.getMemberEmail());

        String result;
        if(!check){
            memberService.register(MemberDto);
            result = "가입 성공";
        }
        else{
            result = "이메일이 이미 존재";
        }
        System.out.println(result);

        return null;
    }

    @GetMapping("/check/register/{Email}")
    public boolean checkEmail(@PathVariable String Email){
        boolean check = memberService.getEmailCheck(Email);
        return check;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/test/login")
    public String login(@RequestBody MemberDto MemberDto){
        boolean check = memberService.getEmailCheck(MemberDto.getMemberEmail());

        String result = "로그인 실패";
        if(check){
            MemberDto findMemberDto = memberService.getMemberDto(MemberDto.getMemberEmail());
            if (findMemberDto.getMemberPassword().equals(MemberDto.getMemberPassword())){
                result = "로그인 성공";
            }
        }

        return result;
    }

}
