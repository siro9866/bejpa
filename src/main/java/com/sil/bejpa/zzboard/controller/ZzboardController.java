package com.sil.bejpa.zzboard.controller;

import com.sil.bejpa.common.response.ApiResponse;
import com.sil.bejpa.zboard.dto.ZboardResponseDto;
import com.sil.bejpa.zboard.dto.ZboardSearchDto;
import com.sil.bejpa.zzboard.service.ZzboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-23
 * Description : mybatis 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 API")
@Slf4j
@RequestMapping("/sample/board2")
public class ZzboardController {

    private final ZzboardService boardService;

    @Operation(summary = "게시판 리스트(Mybatis)", description = "게시판 리스트")
    @GetMapping("")
    public ApiResponse<List<ZboardResponseDto>> boardList(
            ZboardSearchDto zboardSearchDto
            , @PageableDefault(sort = "priorityYn", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ApiResponse<>(boardService.boardList(zboardSearchDto, pageable));
    }
}
