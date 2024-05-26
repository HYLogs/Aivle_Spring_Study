package com.example.demo.Controller;

import com.example.demo.Dto.MemberDto;
import com.example.demo.Entity.Member;
import com.example.demo.Security.Auth.CustomUserDetails;
import com.example.demo.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/test/register")
    public ResponseEntity<MemberDto> register(@RequestBody MemberDto MemberDto){
        boolean check = memberService.getEmailCheck(MemberDto.getMemberEmail());

        if (!check){
            MemberDto createdMemberDto = memberService.register(MemberDto);
            if (createdMemberDto != null){
                System.out.println("가입 성공");
                return ResponseEntity.ok(createdMemberDto);
            }
        }
        System.out.println("가입 실패");
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/check/register/{Email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String Email){
        Boolean check = memberService.getEmailCheck(Email);

        if (check){
            return ResponseEntity.ok(check);
        }
        else{
            return ResponseEntity.badRequest().body(check);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/test/login")
    public ResponseEntity<String> login(@RequestBody MemberDto MemberDto){
        boolean check = memberService.getEmailCheck(MemberDto.getMemberEmail());

        String result = "로그인 실패";
        if (check){
            MemberDto findMemberDto = memberService.getMemberDto(MemberDto.getMemberEmail());
            if (findMemberDto.getMemberPassword().equals(MemberDto.getMemberPassword())){
                return ResponseEntity.ok("로그인 성공");
            }
        }
        return ResponseEntity.badRequest().body("로그인 실패");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check")
    public String checkEmail(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Member member = customUserDetails.getMember();

        return member.getEmail();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    public String admin(){

        return "admin";
    }

}
