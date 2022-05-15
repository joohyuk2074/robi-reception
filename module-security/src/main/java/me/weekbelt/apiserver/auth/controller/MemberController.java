package me.weekbelt.apiserver.auth.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.auth.dto.MemberResponse;
import me.weekbelt.apiserver.auth.dto.MemberSaveRequest;
import me.weekbelt.apiserver.auth.dto.RoleResponse;
import me.weekbelt.apiserver.auth.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/v1/auth/roles")
    @ResponseStatus(value = HttpStatus.CREATED)
    public RoleResponse createRole(@RequestParam String role) {
        return memberService.save(role);
    }

    @PostMapping("/v1/auth/members")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MemberResponse join(@RequestBody MemberSaveRequest memberSaveRequest) {
        return memberService.save(memberSaveRequest);
    }

    @GetMapping("/v1/auth/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok().body(memberService.getMembers());
    }
}
