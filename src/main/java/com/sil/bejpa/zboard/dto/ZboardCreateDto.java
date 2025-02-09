package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.common.validation.YnCode;
import com.sil.bejpa.zboard.entity.Zboard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZboardCreateDto {

	@NotBlank
	@Size(min=1, max = 200)
	private String boardTitle;		// 게시글제목
	@NotBlank
	private String boardContents;	// 게시글내용
	@YnCode
	private String priorityYn;		// 중요여부

	// DTO -> Entity 로 변환
	public Zboard toEntity() {
		return Zboard.builder()
				.boardTitle(boardTitle)
				.boardContents(boardContents)
				.priorityYn(priorityYn)
				.build();
	}
}
