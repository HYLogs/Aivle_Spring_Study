package com.example.demo.Service;

import com.example.demo.Dto.MemberDto;
import com.example.demo.Entity.Member;
import com.example.demo.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void register(MemberDto memberDto){
        Member member = new Member();
        member.setName(memberDto.getMemberName());
        member.setPassword(memberDto.getMemberPassword());
        member.setEmail(memberDto.getMemberEmail());

        memberRepository.save(member);
    }

    public boolean getEmailCheck(String email){
        boolean check = memberRepository.existsByEmail(email);
        return check;
    }

    public MemberDto getMemberDto(String email){
        Member member = memberRepository.findByEmail(email);

        MemberDto memberDto = new MemberDto();
        memberDto.setMemberEmail(member.getEmail());
        memberDto.setMemberName(member.getName());
        memberDto.setMemberPassword(member.getPassword());

        return memberDto;
    }

}
