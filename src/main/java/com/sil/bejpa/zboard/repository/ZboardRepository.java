package com.sil.bejpa.zboard.repository;

import com.sil.bejpa.zboard.entity.Zboard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  게시판
 */
public interface ZboardRepository extends JpaRepository<Zboard, Long>, ZboardRepositoryCustom {

    Zboard findByBoardSeq(Long id);

}
