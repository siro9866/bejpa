package com.sil.bejpa.zzboard.mapper;

import com.sil.bejpa.zboard.dto.ZboardResponseDto;
import com.sil.bejpa.zboard.dto.ZboardSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-06
 * Description :
 */
@Mapper
public interface ZzboardMapper {
    List<ZboardResponseDto> boardList(ZboardSearchDto zboardSearchDto);
}
