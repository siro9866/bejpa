package com.sil.bejpa.zboard.controller;

import com.sil.bejpa.common.response.ApiResponse;
import com.sil.bejpa.zboard.dto.ZboardCreateDto;
import com.sil.bejpa.zboard.dto.ZboardModifyDto;
import com.sil.bejpa.zboard.dto.ZboardResponseDto;
import com.sil.bejpa.zboard.dto.ZboardSearchDto;
import com.sil.bejpa.zboard.service.ZboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-23
 * Description :
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 API")
@Slf4j
@RequestMapping("/sample/board")
public class ZboardController {

    private final ZboardService boardService;

    @Operation(summary = "게시판 리스트", description = "게시판 리스트")
    @GetMapping("")
    public ApiResponse<Page<ZboardResponseDto>> boardList(
            ZboardSearchDto zboardSearchDto
            , @PageableDefault(sort = "priorityYn", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(boardService.boardList(zboardSearchDto, pageable));
    }

    @Operation(summary = "게시판 상세", description = "게시판 상세")
    @GetMapping("/{boardSeq}")
    public ApiResponse<ZboardResponseDto> boardDetail(@PathVariable Long boardSeq) {
        return new ApiResponse<>(boardService.boardDetail(boardSeq));
    }

    @Operation(summary = "게시판 등록", description = "게시판을 등록한다")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ZboardResponseDto> boardCreate(
            @Valid @RequestPart(name = "zboardCreateDto") ZboardCreateDto zboardCreateDto,
            @RequestPart(name = "files", required = false) MultipartFile[] files) throws Exception {
        log.debug("등록파람: {}", zboardCreateDto.toString());
        return new ApiResponse<>(boardService.boardCreate(zboardCreateDto, files));
    }

    @Operation(summary = "게시판 수정", description = "게시판을 수정한다")
    @PatchMapping(value = "/{boardSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ZboardResponseDto> boardModify(@PathVariable Long boardSeq,
                                              @Valid @RequestPart(name = "zboardModifyDto") ZboardModifyDto zboardModifyDto,
                                              @RequestPart(name = "files", required = false) MultipartFile[] files) throws Exception{
        return new ApiResponse<>(boardService.boardModify(boardSeq, zboardModifyDto, files));
    }

    @Operation(summary = "게시판 삭제", description = "게시판을 삭제한다")
    @DeleteMapping("/{boardSeq}")
    public ApiResponse<ZboardResponseDto> boardCreate(@PathVariable Long boardSeq) throws Exception{
        boardService.boardDelete(boardSeq);
        return new ApiResponse<>(boardService.boardDelete(boardSeq));
    }
}
