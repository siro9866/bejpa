package com.sil.bejpa.zboard.dto;

import com.sil.bejpa.zboard.entity.Zcomments;
import jakarta.validation.constraints.NotBlank;
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
public class ZcommentsModifyDto {
	@NotBlank
	private String comments;							// 댓글내용

	public void modifyComments(Zcomments zcomments) {
		zcomments.setComments(comments);
	}
}
