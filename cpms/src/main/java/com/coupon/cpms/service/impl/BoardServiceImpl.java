/**
 * 
 */
package com.coupon.cpms.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.coupon.cpms.domain.AttachDTO;
import com.coupon.cpms.domain.BoardDTO;
import com.coupon.cpms.mapper.AttachMapper;
import com.coupon.cpms.mapper.BoardMapper;
import com.coupon.cpms.service.BoardService;
import com.coupon.cpms.util.Criteria;
import com.coupon.cpms.util.FileUtils;
import com.coupon.cpms.util.PaginationInfo;

/**
 * @author admin-PC
 *
 */

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private AttachMapper attachMapper;

	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;

		if (params.getBbsUno() == null) {
			queryResult = boardMapper.insertBoard(params);
		} else {
			queryResult = boardMapper.updateBoard(params);
			
			// 파일이 추가, 삭제, 변경된 경우
			if ("Y".equals(params.getChangeYn())) {
				attachMapper.deleteAttach(params.getBbsUno());

				// fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
				if (CollectionUtils.isEmpty(params.getFileIdxs()) == false) {
					attachMapper.undeleteAttach(params.getFileIdxs());
				}
			}
		}
		
		//return (queryResult == 1) ? true : false;
		return (queryResult > 0);
	}

	@Override
	public boolean registerBoard(BoardDTO params, MultipartFile[] files) {
		int queryResult = 1;

		if (registerBoard(params) == false) {
			return false;
		}

		List<AttachDTO> fileList = fileUtils.uploadFiles(files, params.getBbsUno());
		if (CollectionUtils.isEmpty(fileList) == false) {
			queryResult = attachMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
		}

		return (queryResult > 0);
	}
	
	@Override
	public BoardDTO getBoardDetail(Long bbsUno) {
		return boardMapper.selectBoardDetail(bbsUno);
	}

	@Override
	public boolean deleteBoard(Long bbsUno) {
		int queryResult = 0;

		BoardDTO board = boardMapper.selectBoardDetail(bbsUno);

		if (board != null && "N".equals(board.getDeleteYn())) {
			queryResult = boardMapper.deleteBoard(bbsUno);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList(BoardDTO params) {
		List<BoardDTO> boardList = Collections.emptyList();

		int boardTotalCount = boardMapper.selectBoardTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);
		
		params.setPaginationInfo(paginationInfo);
		
		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList(params);
		}

		return boardList;
	}
	
	@Override
	public List<AttachDTO> getAttachFileList(Long bbsUno) {

		int fileTotalCount = attachMapper.selectAttachTotalCount(bbsUno);
		if (fileTotalCount < 1) {
			return Collections.emptyList();
		}
		return attachMapper.selectAttachList(bbsUno);
	}
	
	@Override
	public AttachDTO getAttachDetail(Long attachUno) {
		return attachMapper.selectAttachDetail(attachUno);
	}	

}
