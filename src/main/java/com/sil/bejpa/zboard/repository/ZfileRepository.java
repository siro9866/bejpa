package com.sil.bejpa.zboard.repository;

import com.sil.bejpa.zboard.entity.Zfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  파일
 */
public interface ZfileRepository extends JpaRepository<Zfile, Long>{
	// 게시글의 파일리스트
//	List<Zfile> findAllByBoardSeq(Long boardSeq);
	
	// 게시글의 파일리스트 삭제
//	void deleteAllByBoardSeq(Long boardSeq);
	
}
