package com.sil.bejpa.member.controller;

import com.sil.bejpa.common.response.ApiResponse;
import com.sil.bejpa.member.dto.MemberResponseDto;
import com.sil.bejpa.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-21
 * Description :
 */
@Tag(name = "회원", description = "회원 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;


    /**
     * 회원상세
     */
    @Operation(summary = "회원상세", description = "회원상세")
    @GetMapping("/{memberSeq}")
    public ApiResponse<MemberResponseDto> memberDetail(@PathVariable("memberSeq") Long memberSeq) throws Exception {
        return new ApiResponse<>(memberService.getMember(memberSeq));
    }
}
