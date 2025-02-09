package com.sil.bejpa.member.dto;

import com.sil.bejpa.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 회원조회
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto{

	private Long memberSeq;
	private String memberId;
	private String memberEmail;
	private String memberName;
	private LocalDateTime joinDttm;
	private LocalDateTime signDttm;
	private LocalDateTime createDttm;
	private LocalDateTime modifyDttm;
	
	// Entity -> DTO 로 변환
	public static MemberResponseDto toDto(Member item) {
		return MemberResponseDto.builder()
				.memberSeq(item.getMemberSeq())
				.memberId(item.getMemberId())
				.memberEmail(item.getMemberEmail())
				.memberName(item.getMemberName())
				.joinDttm(item.getJoinDttm())
				.signDttm(item.getSignDttm())
				.createDttm(item.getCreatedDttm())
				.modifyDttm(item.getModifiedDttm())
				.build();
	}
}
