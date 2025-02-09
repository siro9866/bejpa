package com.sil.bejpa.zboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 게시판
 */
@Getter
@Setter
@ToString
public class ZboardSearchDto {
	private String searchValue;
	private LocalDate startDate;
	private LocalDate endDate;
}
