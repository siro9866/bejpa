package com.sil.bejpa.member.dto;

import com.sil.bejpa.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원가입
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberCreateDto {
	
	@NotBlank
	@Size(max = 20)
	private String memberId;
	
	@NotBlank
	@Size(max = 50)
	private String memberName;
	
	@NotBlank
	@Size(max = 100)
	private String password;
	
	@Size(max = 50)
	private String memberRole;
	
	@NotBlank
	@Email(message = "{validation.email}")
	@Size(max = 100)
	private String memberEmail;
	
	@NotEmpty
	@Size(max = 100)
	private String address;

	// DTO -> Entity 로 변환
	public Member toEntity() {
		return Member.builder()
				.memberId(memberId)
				.memberName(memberName)
				.password(password)
				.memberRole(memberRole)
				.memberEmail(memberEmail)
				.build();
	}

	// List<DTO> -> List<Entity> 로 변환
	public List<Member> toEntityList(List<MemberCreateDto> item){
		return item.stream().map(MemberCreateDto::toEntity).collect(Collectors.toList());
	}
	
}
