package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zboard;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시판
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZboardResponseDto {

	private Long boardSeq;
	private String boardTitle;		// 게시글제목
	private String boardContents;	// 게시글내용
	private Long viewCount;			// 조회수
	private String priorityYn;		// 중요여부
	private String deletedYn;		// 삭제여부

	private String createdBy;
	private LocalDateTime createdDttm;
	private String modifiedBy;
	private LocalDateTime modifiedDttm;
	
	private Long commentCount;			// 댓글수
	private Long fileCount;				// 파일수
	private List<ZfileResponseDto> files;	// 파일리스트
	
	// Entity -> DTO 로 변환
	public static ZboardResponseDto toDto(Zboard item) {
		return ZboardResponseDto.builder()
				.boardSeq(item.getBoardSeq())
				.boardTitle(item.getBoardTitle())
				.boardContents(item.getBoardContents())
				.viewCount(item.getViewCount())
				.priorityYn(item.getPriorityYn())
				.deletedYn(item.getDeletedYn())
				.createdBy(item.getCreatedBy())
				.createdDttm(item.getCreatedDttm())
				.modifiedBy(item.getModifiedBy())
				.modifiedDttm(item.getModifiedDttm())
				.build();
	}
}
