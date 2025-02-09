package com.sil.bejpa.zboard.repository;

import com.sil.bejpa.zboard.entity.Zcomments;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글
 */
public interface ZcommentsRepository extends JpaRepository<Zcomments, Long>, ZcommentsRepositoryCustom {

}
