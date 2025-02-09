package com.sil.bejpa.zboard.repository;

import com.sil.bejpa.zboard.dto.ZcommentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZcommentsRepositoryCustom {


	/**
	 * 댓글글리스트
	 */
	Page<ZcommentsResponseDto> findCommentsAll(Long boardSeq, Pageable pageable);
}
