package com.sil.bejpa.zboard.entity;

import com.sil.bejpa.common.entity.Base;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

/**
 * 댓글
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE ZCOMMENTS SET DELETED_YN = 'Y' WHERE COMMENTS_SEQ = ?")
@SQLRestriction("DELETED_YN = 'N'")
@DynamicInsert
@DynamicUpdate
@Table(name = "ZCOMMENTS")
public class Zcomments extends Base {

	@Comment("댓글순번")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENTS_SEQ", nullable = false)
	private Long commentsSeq;
	
	@Comment("상위댓글순번")
	@Column(name = "PARENTS_COMMENTS_SEQ")
	private Long parentsCommentsSeq;

	@Comment("댓글내용")
	@Column(name = "COMMENTS", nullable = false, length = 20000)
	private String comments;
	
	@Comment("삭제여부")
	@ColumnDefault("'N'")
	@Column(name = "DELETED_YN", nullable = false, length = 1)
	private String deletedYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_SEQ")
	private Zboard board;
}
