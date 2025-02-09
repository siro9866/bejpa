package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zfile;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시판
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZfileResponseDto {

	private Long fileSeq;			// 파일순번
	private Long boardSeq;			// 게시글순번
	private String uploadPath;		// 업로드경로
	private String orgFileName;		// 원본파일명
	private String sysFileName;		// 실제파일명

	private String createdBy;
	private LocalDateTime createdDttm;
	private String modifiedBy;
	private LocalDateTime modifiedDttm;
	
	// Entity -> DTO 로 변환
	public static ZfileResponseDto toDto(Zfile item) {
		return ZfileResponseDto.builder()
				.fileSeq(item.getFileSeq())
				.boardSeq(item.getBoard().getBoardSeq())
				.uploadPath(item.getUploadPath())
				.orgFileName(item.getOrgFileName())
				.sysFileName(item.getSysFileName())
				.createdBy(item.getCreatedBy())
				.createdDttm(item.getCreatedDttm())
				.modifiedBy(item.getModifiedBy())
				.modifiedDttm(item.getModifiedDttm())
				.build();
	}
}
