/**
 * 
 */
package com.coupon.cpms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coupon.cpms.domain.AttachDTO;
import com.coupon.cpms.domain.BoardDTO;

/**
 * @author admin-PC
 *
 */
public interface BoardService {
	public boolean registerBoard(BoardDTO params);
	
	public boolean registerBoard(BoardDTO params, MultipartFile[] files);

	public BoardDTO getBoardDetail(Long bbsUno);

	public boolean deleteBoard(Long bbsUno);

	public List<BoardDTO> getBoardList(BoardDTO params);
	
	public List<AttachDTO> getAttachFileList(Long bbsUno);
	
	public AttachDTO getAttachDetail(Long attachUno);
}
