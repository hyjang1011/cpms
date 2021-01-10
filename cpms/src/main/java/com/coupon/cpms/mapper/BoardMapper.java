/**
 * 
 */
package com.coupon.cpms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coupon.cpms.domain.BoardDTO;

/**
 * @author admin-PC
 *
 */

@Mapper
public interface BoardMapper {
	public int insertBoard(BoardDTO params);

	public BoardDTO selectBoardDetail(Long bbsUno);

	public int updateBoard(BoardDTO params);

	public int deleteBoard(Long bbsUno);

	public List<BoardDTO> selectBoardList(BoardDTO params);

	public int selectBoardTotalCount(BoardDTO params);
}
