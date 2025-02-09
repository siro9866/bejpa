package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zboard;
import com.sil.bejpa.zboard.entity.Zfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판파일
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZfileCreateDto {

	private String uploadPath;	// 업로드경로
	private String orgFileName;	// 원본파일명
	private String sysFileName;	// 실제파일명
	private Zboard board;

	// DTO -> Entity 로 변환
	public Zfile toEntity() {
		return Zfile.builder()
				.uploadPath(uploadPath)
				.orgFileName(orgFileName)
				.sysFileName(sysFileName)
				.board(board)
				.build();
	}

}
