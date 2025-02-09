package com.sil.bejpa.zboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sil.bejpa.zboard.dto.ZcommentsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sil.bejpa.zboard.entity.QZcomments.zcomments;

@RequiredArgsConstructor
public class ZcommentsRepositoryImpl implements ZcommentsRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ZcommentsResponseDto> findCommentsAll(Long boardSeq, Pageable pageable) {
		List<ZcommentsResponseDto> query = queryFactory.select(
				Projections.bean(ZcommentsResponseDto.class
				, zcomments.commentsSeq
				, zcomments.parentsCommentsSeq
				, zcomments.comments
				, zcomments.createdBy
				, zcomments.createdDttm
				, zcomments.modifiedBy
				, zcomments.modifiedDttm
			))
			.from(zcomments)
			.where(zcomments.board.boardSeq.eq(boardSeq))
			.orderBy(
					zcomments.parentsCommentsSeq.asc().nullsFirst()
					, zcomments.commentsSeq.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(zcomments.count())
				.from(zcomments)
				.where(zcomments.board.boardSeq.eq(boardSeq))
				;
		
		return PageableExecutionUtils.getPage(query, pageable, countQuery::fetchOne);
	}
}
