package com.sil.bejpa.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원수정
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberModifyDto {

	@NotBlank
	@Email(message = "{validation.email}")
	@Size(max = 50)
	private String memberEmail;
	
	@NotBlank
	@Size(max = 50)
	private String memberName;

}
