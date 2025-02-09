package com.sil.bejpa.zboard.repository;

import com.sil.bejpa.zboard.dto.ZboardResponseDto;
import com.sil.bejpa.zboard.dto.ZboardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZboardRepositoryCustom {

	/**
	 * 게시글리스트
	 */
	Page<ZboardResponseDto> findBoardAll(ZboardSearchDto paramDto, Pageable pageable);

}
