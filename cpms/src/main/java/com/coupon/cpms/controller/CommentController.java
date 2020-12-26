/**
 * 
 */
package com.coupon.cpms.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.cpms.adapter.GsonLocalDateTimeAdapter;
import com.coupon.cpms.domain.CommentDTO;
import com.coupon.cpms.service.CommentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author admin-PC
 *
 */

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@RequestMapping(value = { "/comments", "/comments/{commentUno}" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public JsonObject registerComment(@PathVariable(value = "commentUno", required = false) Long commentUno, @RequestBody final CommentDTO params) {

		JsonObject jsonObj = new JsonObject();

		try {
			/*
			 * if (commentUno != null) { params.setCommentUno(commentUno); }
			 */

			boolean isRegistered = commentService.registerComment(params);
			jsonObj.addProperty("result", isRegistered);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/comments/{bbsUno}")
	public JsonObject getCommentList(@PathVariable("bbsUno") Long bbsUno, @ModelAttribute("params") CommentDTO params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDTO> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
			//JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}
	
	@DeleteMapping(value = "/comments/{commentUno}")
	public JsonObject deleteComment(@PathVariable("commentUno") final Long commentUno) {

		JsonObject jsonObj = new JsonObject();

		try {
			boolean isDeleted = commentService.deleteComment(commentUno);
			jsonObj.addProperty("result", isDeleted);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}

}
