package com.sil.bejpa.zboard.entity;

import com.sil.bejpa.common.entity.Base;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 샘플게시판
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE ZBOARD SET DELETED_YN = 'Y' WHERE BOARD_SEQ = ?")
@SQLRestriction("DELETED_YN = 'N'")
@DynamicInsert
@DynamicUpdate
@Table(name = "ZBOARD")
public class Zboard extends Base {

    @Comment("게시글순번")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ", nullable = false)
    private Long boardSeq;

    @Comment("게시글제목")
    @Column(name = "BOARD_TITLE", nullable = false, length = 200)
    private String boardTitle;

    @Comment("게시글내용")
    @Column(name = "BOARD_CONTENTS", nullable = false, length = 20000)
    private String boardContents;

    @Comment("조회수")
    @ColumnDefault("0")
    @Column(name = "VIEW_COUNT")
    private Long viewCount;

    @Comment("중요여부")
    @ColumnDefault("'N'")
    @Column(name = "PRIORITY_YN", nullable = false, length = 1)
    private String priorityYn;

    @Comment("삭제여부")
    @ColumnDefault("'N'")
    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    @OneToMany(mappedBy = "board")
    private final List<Zcomments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<Zfile> files = new ArrayList<>();

}