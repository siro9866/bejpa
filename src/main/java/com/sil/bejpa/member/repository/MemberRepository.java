package com.sil.bejpa.member.repository;

import com.sil.bejpa.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByMemberId(String memberId);
	
}
