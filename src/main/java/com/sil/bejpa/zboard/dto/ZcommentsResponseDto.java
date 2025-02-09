package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zcomments;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZcommentsResponseDto {

	private Long commentsSeq;				// 댓글순번
	private Long parentsCommentsSeq;	// 상위댓글순번
	private Long boardSeq;						// 게시글순번
	private String comments;						// 댓글내용
	private String deletedYn;						// 삭제여부

	private String createdBy;
	private LocalDateTime createdDttm;
	private String modifiedBy;
	private LocalDateTime modifiedDttm;
	private final List<ZcommentsResponseDto> reComments = new ArrayList<>();	// 자식댓글

	// Entity -> DTO 로 변환
	public static ZcommentsResponseDto toDto(Zcomments item) {
		return ZcommentsResponseDto.builder()
				.commentsSeq(item.getCommentsSeq())
				.boardSeq(item.getBoard().getBoardSeq())
				.parentsCommentsSeq(item.getParentsCommentsSeq())
				.comments(item.getComments())
				.deletedYn(item.getDeletedYn())
				.createdBy(item.getCreatedBy())
				.createdDttm(item.getCreatedDttm())
				.modifiedBy(item.getModifiedBy())
				.modifiedDttm(item.getModifiedDttm())
				.build();
	}
}
