package com.sil.bejpa.zboard.controller;

import com.sil.bejpa.common.response.ApiResponse;
import com.sil.bejpa.zboard.dto.ZcommentsCreateDto;
import com.sil.bejpa.zboard.dto.ZcommentsModifyDto;
import com.sil.bejpa.zboard.dto.ZcommentsResponseDto;
import com.sil.bejpa.zboard.service.ZcommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-24
 * Description :
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 댓글 API")
@Slf4j
@RequestMapping("/sample/board/comments")
public class ZcommentsController {

    private final ZcommentsService zcommentsService;

    @Operation(summary = "댓글 리스트", description = "댓글 리스트")
    @GetMapping("/")
    public ApiResponse<Page<ZcommentsResponseDto>> commentsList(
            @RequestParam(value = "boardSeq") Long boardSeq
            , @PageableDefault(sort = "commentsSeq", direction = Sort.Direction.DESC)Pageable pageable) throws Exception{
        // TODO: 대댓글 개념이라 페이징 상이할 수 있음. 업무에 따라 개선이 필요함
        return new ApiResponse<>(zcommentsService.commentsList(boardSeq, pageable));
    }

    @Operation(summary = "댓글 등록", description = "댓글을 등록한다")
    @PostMapping("/")
    public ApiResponse<ZcommentsResponseDto> commentsCreate(@Valid @RequestBody ZcommentsCreateDto paramDto) throws Exception{
        log.debug("등록파람: {}", paramDto.toString());
        return new ApiResponse<>(zcommentsService.commentsCreate(paramDto));
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정한다")
    @PatchMapping("/{commentsSeq}")
    public ApiResponse<ZcommentsResponseDto> scommentsModify(@PathVariable Long commentsSeq, @RequestBody ZcommentsModifyDto paramDto) throws Exception{
        return new ApiResponse<>(zcommentsService.commentsModify(commentsSeq, paramDto));
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다")
    @DeleteMapping("/{commentsSeq}")
    public ApiResponse<ZcommentsResponseDto> scommentsCreate(@PathVariable Long commentsSeq) throws Exception{
        return new ApiResponse<>(zcommentsService.commentsDelete(commentsSeq));
    }
}
