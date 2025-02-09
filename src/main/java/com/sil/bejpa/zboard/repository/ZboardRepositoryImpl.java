package com.sil.bejpa.zboard.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sil.bejpa.common.util.UtilCommon;
import com.sil.bejpa.common.util.UtilDate;
import com.sil.bejpa.common.util.UtilQueryDsl;
import com.sil.bejpa.zboard.dto.ZboardResponseDto;
import com.sil.bejpa.zboard.dto.ZboardSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.sil.bejpa.zboard.entity.QZboard.zboard;
import static com.sil.bejpa.zboard.entity.QZcomments.zcomments;
import static com.sil.bejpa.zboard.entity.QZfile.zfile;

@RequiredArgsConstructor
public class ZboardRepositoryImpl implements ZboardRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<ZboardResponseDto> findBoardAll(ZboardSearchDto zboardSearchDto, Pageable pageable) {
		List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);
		
		List<ZboardResponseDto> query = queryFactory.select(
				Projections.bean(ZboardResponseDto.class
				, zboard.boardSeq
				, zboard.boardTitle
				, zboard.boardContents
				, zboard.viewCount
				, zboard.priorityYn
				, zboard.deletedYn
				, zboard.createdBy
				, zboard.createdDttm
				, zboard.modifiedBy
				, zboard.modifiedDttm
				, ExpressionUtils.as(
						select(zcomments.count())
						.from(zcomments)
						.where(zcomments.board.eq(zboard))
					, "commentCount")
				, ExpressionUtils.as(
						select(zfile.count())
								.from(zfile)
								.where(zfile.board.eq(zboard)), "fileCount")
			))
			.from(zboard)
			.where(Objects.requireNonNull(createdDttmBetween(zboardSearchDto.getStartDate(), zboardSearchDto.getEndDate()))
					.and(searchValueAllCondition(zboardSearchDto.getSearchValue()))
			)
			.orderBy(ORDERS.toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		
		JPAQuery<Long> countQuery = queryFactory
				.select(zboard.count())
				.from(zboard)
				.where(searchValueAllCondition(zboardSearchDto.getSearchValue()));
		
		return PageableExecutionUtils.getPage(query, pageable, countQuery::fetchOne);
	}

	/**
	 * sort
	 */
	private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier> ORDERS = new ArrayList<>();
		if (UtilCommon.isNotEmpty(pageable.getSort())) {
			for (Sort.Order order : pageable.getSort()) {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                if (order.getProperty().equals("boardSeq")) {
                    OrderSpecifier<?> orderId = UtilQueryDsl.getSortedColumn(direction, zboard, "boardSeq");
                    ORDERS.add(orderId);
                }
			}
		}
		return ORDERS;
	}

	/**
	 * 기간조건
	 */
	private BooleanExpression createdDttmBetween(LocalDate startDate, LocalDate endDate) {
		if (UtilCommon.isEmpty(startDate) && UtilCommon.isEmpty(endDate)) {
			return null;
		}
		return zboard.createdDttm.between(UtilDate.startDatetime(startDate), UtilDate.endDatetime(endDate));
	}

	/**
	 * 검색조건
	 */
	private BooleanBuilder searchValueAllCondition(String searchValue) {
		BooleanBuilder builder = new BooleanBuilder();
		return builder
				.and(titleContains(searchValue))
				.or(contentsContains(searchValue));

	}

	/**
	 * 제목포함
	 */
	private BooleanExpression titleContains(String searchValue) {
		return UtilCommon.isEmpty(searchValue) ? null : zboard.boardTitle.containsIgnoreCase(searchValue);
	}
	
	/**
	 * 내용포함
	 */
	private BooleanExpression contentsContains(String searchValue) {
		return UtilCommon.isEmpty(searchValue) ? null : zboard.boardContents.containsIgnoreCase(searchValue);
	}

}
