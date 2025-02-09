package com.sil.bejpa.zboard.entity;

import com.sil.bejpa.common.entity.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

/**
 * 게시판파일
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ZFILE")
public class Zfile extends Base {

	@Comment("파일순번")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_SEQ", nullable = false)
	private Long fileSeq;

	@Comment("업로드경로")
	@Column(name = "UPLOAD_PATH", nullable = false, length = 50)
	private String uploadPath;
	
	@Comment("원본파일명")
	@Column(name = "ORG_FILE_NAME", nullable = false, length = 200)
	private String orgFileName;
	
	@Comment("실제파일명")
	@Column(name = "SYS_FILE_NAME", nullable = false, length = 200)
	private String sysFileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_SEQ")
	private Zboard board;
}
