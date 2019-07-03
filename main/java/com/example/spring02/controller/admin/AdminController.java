package com.example.spring02.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.member.dto.MemberDTO;
import com.example.spring02.service.admin.AdminService;

@Controller
@RequestMapping("admin/*")
public class AdminController {
	
	@Inject
	AdminService adminService;
	
	@RequestMapping("login.do")
	public String login() {
		return "admin/login";
	}
	
	
	@RequestMapping("login_check.do")
	public ModelAndView login_check(MemberDTO dto, ModelAndView mav, HttpSession session) {
		String name = adminService.loginCheck(dto); 
		
		if(name != null) {	//로그인 성공하면?
			//관리자용 세션
			session.setAttribute("admin_userid", dto.getUserid());
			session.setAttribute("admin_name", name);
			//일반 세션
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("name", name);
			
			mav.setViewName("admin/admin");
		} else {
			mav.setViewName("admin/login");
			mav.addObject("message", "error");
		}
		
		return mav;
	}
	
	@RequestMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/admin/login.do";
	}
	
}
