package com.example.spring02.controller.memo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.memo.dto.MemoDTO;
import com.example.spring02.service.memo.MemoService;

@Controller
@RequestMapping("memo/*")	//클래스에서 공통적인 url패턴을 정의
public class MemoController {
	
	@Inject
	MemoService memoService;
	
	@RequestMapping("list.do")	//메소드에서 세부적인 url패턴을 정의
	public ModelAndView list(ModelAndView mav) {
		List<MemoDTO> list = memoService.list();
		
		mav.setViewName("memo/memo_list");	//포워딩할 뷰의 이름
		mav.addObject("list", list);		//전달 데이터(모델)
		
		return mav;
		//return new ModelAndView("memo/memo_list", "list", list);와 같음
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute MemoDTO dto) {
		
		memoService.insert(dto);
		
		return "redirect:/memo/list.do";
	}
	
	@RequestMapping("view/{idx}")
	public ModelAndView view(@PathVariable int idx, ModelAndView mav) {
		
		mav.setViewName("memo/view");
		mav.addObject("dto", memoService.memo_view(idx));
		
		return mav;
	}
	
	@RequestMapping("update/{idx}")
	public String Update(@PathVariable int idx, @ModelAttribute MemoDTO dto) {
		memoService.update(dto);
		
		return "redirect:/memo/list.do";
	}
	
	@RequestMapping("delete/{idx}")
	public String Delete(@PathVariable int idx) {
		memoService.delete(idx);
		
		return "redirect:/memo/list.do";
	}
}
