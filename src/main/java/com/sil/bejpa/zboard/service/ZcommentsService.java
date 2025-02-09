package com.sil.bejpa.zboard.service;

import com.sil.bejpa.common.exception.CustomException;
import com.sil.bejpa.common.response.ResponseCode;
import com.sil.bejpa.common.util.UtilCommon;
import com.sil.bejpa.common.util.UtilMessage;
import com.sil.bejpa.zboard.dto.ZcommentsCreateDto;
import com.sil.bejpa.zboard.dto.ZcommentsModifyDto;
import com.sil.bejpa.zboard.dto.ZcommentsResponseDto;
import com.sil.bejpa.zboard.entity.Zboard;
import com.sil.bejpa.zboard.entity.Zcomments;
import com.sil.bejpa.zboard.repository.ZboardRepository;
import com.sil.bejpa.zboard.repository.ZcommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-24
 * Description :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ZcommentsService {

    private final UtilMessage utilMessage;
    private final ZcommentsRepository commentsRepository;
    private final ZboardRepository boardRepository;

    /**
     * 댓글리스트
     */
    public Page<ZcommentsResponseDto> commentsList(Long boardSeq, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        List<ZcommentsResponseDto> resultContents = new ArrayList<>();
        Map<Long, ZcommentsResponseDto> imsiMap = new HashMap<>();

        // 댓글 부모 밑에 자식 붙이기
        Page<ZcommentsResponseDto> page = commentsRepository.findCommentsAll(boardSeq, pageable);
        List<ZcommentsResponseDto> imsiResultList = page.getContent();
        imsiResultList.forEach(item -> {
            imsiMap.put(item.getCommentsSeq(), item);
            if(UtilCommon.isEmpty(item.getParentsCommentsSeq())){
                resultContents.add(item);
            }else{
                imsiMap.get(item.getParentsCommentsSeq()).getReComments().add(item);
            }
        });

        Page<ZcommentsResponseDto> resultPage = new PageImpl<>(resultContents, pageable, resultContents.size());

        return page;
    }

    /**
     * 댓글 등록
     */
    @Transactional
    public ZcommentsResponseDto commentsCreate(ZcommentsCreateDto zcommentsCreateDto) {
        Zboard board = boardRepository.findByBoardSeq(zcommentsCreateDto.getBoardSeq());
        zcommentsCreateDto.setBoard(board);
        Zcomments entity = commentsRepository.save(zcommentsCreateDto.toEntity());
        return ZcommentsResponseDto.toDto(entity);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ZcommentsResponseDto commentsModify(Long boardSeq, ZcommentsModifyDto zcommentsModifyDto) {
        Zcomments zcomments = commentsRepository.findById(boardSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.modify.nodata", null)));
        zcommentsModifyDto.modifyComments(zcomments);
        return ZcommentsResponseDto.toDto(zcomments);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ZcommentsResponseDto commentsDelete(Long commentsSeq) {
        Zcomments entity = commentsRepository.findById(commentsSeq).orElseThrow(() -> new CustomException(ResponseCode.EXCEPTION_GET_NODATA, utilMessage.getMessage("exception.delete.nodata", null)));
        commentsRepository.delete(entity);
        return ZcommentsResponseDto.toDto(entity);
    }
}
