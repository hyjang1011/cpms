/**
 * 
 */
package com.coupon.cpms.service;

import java.util.List;

import com.coupon.cpms.domain.CommentDTO;

/**
 * @author admin-PC
 *
 */
public interface CommentService {
	public boolean registerComment(CommentDTO params);

	public boolean deleteComment(Long commentUno);

	public List<CommentDTO> getCommentList(CommentDTO params);
}
