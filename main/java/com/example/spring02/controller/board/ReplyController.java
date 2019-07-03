package com.example.spring02.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.ReplyDTO;
import com.example.spring02.service.board.ReplyService;

//@ResponseBody를 붙이지 않아도 뷰가 아닌 데이터를 리턴 가능!
@RestController	//Spring 4.0부터 사용가능
@RequestMapping("reply/*")
public class ReplyController {
	
	@Inject
	ReplyService replyService;
	
	@RequestMapping("insert.do")
	public void insert(ReplyDTO dto, HttpSession session) {
		//세션에 저장된 댓글 작성자 아이디 호출
		String userid = (String)session.getAttribute("userid");
		dto.setReplyer(userid);
		
		//댓글이 테이블에 저장
		replyService.create(dto);
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(int bno, ModelAndView mav) {
		
		List<ReplyDTO> list = replyService.list(bno);
		
		mav.setViewName("board/reply_list");
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping("list_json.do")
	public List<ReplyDTO> list_json(int bno){
		
		return replyService.list(bno);
	}
	
	
}
