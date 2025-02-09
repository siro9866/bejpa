package com.sil.bejpa.token.repository;


import com.sil.bejpa.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{

	// 메서드 확인(키값등 재설계)
	
	Boolean existsByRefreshToken(String refresh);
	
	@Transactional
	void deleteByMemberId(String memberId);
	
	@Transactional
	void deleteByRefreshToken(String refresh);
	
}
