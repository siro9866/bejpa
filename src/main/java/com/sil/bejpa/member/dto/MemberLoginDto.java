package com.sil.bejpa.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 DTO
 */
@Getter
@Setter
@ToString
public class MemberLoginDto {
	private String memberId;
	private String password;
}
