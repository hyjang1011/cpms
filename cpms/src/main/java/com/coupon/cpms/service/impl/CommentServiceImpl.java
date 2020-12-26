/**
 * 
 */
package com.coupon.cpms.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.cpms.domain.CommentDTO;
import com.coupon.cpms.mapper.CommentMapper;
import com.coupon.cpms.service.CommentService;

/**
 * @author admin-PC
 *
 */

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public boolean registerComment(CommentDTO params) {
		int queryResult = 0;

		if (params.getCommentUno() == null) {
			queryResult = commentMapper.insertComment(params);
		} else {
			queryResult = commentMapper.updateComment(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean deleteComment(Long commentUno) {
		int queryResult = 0;

		CommentDTO comment = commentMapper.selectCommentDetail(commentUno);

		if (comment != null && "N".equals(comment.getDeleteYn())) {
			queryResult = commentMapper.deleteComment(commentUno);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<CommentDTO> getCommentList(CommentDTO params) {
		List<CommentDTO> commentList = Collections.emptyList();

		int commentTotalCount = commentMapper.selectCommentTotalCount(params);
		if (commentTotalCount > 0) {
			commentList = commentMapper.selectCommentList(params);
		}

		return commentList;
	}

}
