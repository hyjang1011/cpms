package com.coupon.cpms.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.coupon.cpms.constant.Method;
import com.coupon.cpms.domain.AttachDTO;
import com.coupon.cpms.domain.BoardDTO;
import com.coupon.cpms.service.BoardService;
import com.coupon.cpms.util.UiUtils;

@Controller
public class BoardController extends UiUtils {
	
	@SuppressWarnings("unused")
	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "bbsUno", required = false) Long bbsUno, Model model) {
		if (bbsUno == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
			BoardDTO board = boardService.getBoardDetail(bbsUno);
			if (board == null || "Y".equals(board.getDeleteYn())) {
				return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
			}
			model.addAttribute("board", board);
			
			List<AttachDTO> fileList = boardService.getAttachFileList(bbsUno);
			model.addAttribute("fileList", fileList);
		}

		return "board/write";
	}
	
	@PostMapping(value = "/board/register.do")
	//public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
	public String registerBoard(final BoardDTO params, final MultipartFile[] files, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		
		try {
			boolean isRegistered = boardService.registerBoard(params, files);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);

		return "board/list";
	}

	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "bbsUno", required = false) Long bbsUno, Model model) {
		if (bbsUno == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}

		BoardDTO board = boardService.getBoardDetail(bbsUno);
		if (board == null || "Y".equals(board.getDeleteYn())) {
			// TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
		}
		model.addAttribute("board", board);
		
		List<AttachDTO> fileList = boardService.getAttachFileList(bbsUno);
		model.addAttribute("fileList", fileList);

		return "board/view";
	}

	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "bbsUno", required = false) Long bbsUno, Model model) {
		if (bbsUno == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
		
		try {
			boolean isDeleted = boardService.deleteBoard(bbsUno);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	@GetMapping("/board/download.do")
	public void downloadAttachFile(@RequestParam(value = "attachUno", required = false) final Long attachUno, Model model, HttpServletResponse response) {

		if (attachUno == null) throw new RuntimeException("올바르지 않은 접근입니다.");

		AttachDTO fileInfo = boardService.getAttachDetail(attachUno);
		if (fileInfo == null || "Y".equals(fileInfo.getDeleteYn())) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}

		String uploadDate = fileInfo.getRegistDt().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get("C:", "develop", "upload", uploadDate).toString();

		String filename = fileInfo.getOriginalName();
		File file = new File(uploadPath, fileInfo.getSaveName());

		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}	
}
