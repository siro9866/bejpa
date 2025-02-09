package com.sil.bejpa.common.security;

import com.sil.bejpa.member.entity.Member;
import com.sil.bejpa.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memberRepository;

	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

		Member entity = memberRepository.findByMemberId(memberId);
		
		if(entity != null) {
			return new CustomUserDetails(entity);
		}
		
		return null;
	}

}
