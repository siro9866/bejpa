package com.sil.bejpa.member.dto;


import com.sil.bejpa.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 비밀번호변경
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberModifyPasswdDto {
	@NotBlank
	@Size(max = 20)
	private String password;
	
	// DTO -> Entity 로 변환
	public Member toEntity() {
		return Member.builder()
				.password(this.password)
				.build();
	}
}