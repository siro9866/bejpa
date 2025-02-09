package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zboard;
import com.sil.bejpa.zboard.entity.Zcomments;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 댓글
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ZcommentsCreateDto {

	@Positive
	private Long boardSeq;						// 게시글순번
	private Long parentsCommentsSeq;	// 상위댓글순번
	@NotBlank
	private String comments;						// 댓글내용

	@Hidden	// 파라미터에 포함시키지 않음
	private Zboard board;


	// DTO -> Entity 로 변환
	public Zcomments toEntity() {
		return Zcomments.builder()
				.parentsCommentsSeq(getParentsCommentsSeq())
				.comments(getComments())
				.board(board)
				.build();
	}

}
