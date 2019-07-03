package com.example.spring02.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;

@Controller
@RequestMapping("shop/cart/*")
public class CartController {
	
	@Inject
	CartService cartService;
	
	@RequestMapping("list.do")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		String userid = (String)session.getAttribute("userid");
		Map<String, Object> map = new HashMap<>();
		
		if(userid != null) {	//로그인 한 경우
			List<CartDTO> list = cartService.listCart(userid);
			
			int sumMoney = cartService.sumMoney(userid);	//총액
			int fee = sumMoney >= 30000 ? 0 : 2500;			//배송료 2500, 총액 30000 이상은 무료
			
			map.put("list", list);
			map.put("count", list.size());
			map.put("sumMoney", sumMoney);
			map.put("fee", fee);
			map.put("sum", sumMoney + fee);
			
			mav.setViewName("shop/cart_list");
			mav.addObject("map", map);

			return mav;
			
		} else {
			
			return new ModelAndView("member/login", "", null);
		}
		
	}
	
	
	@RequestMapping("insert.do")
	public String insert(HttpSession session, @ModelAttribute CartDTO dto) {
		String userid = (String)session.getAttribute("userid");
		
		/*
		 * if(userid == null) { //로그인이 안 됐다면? return "redirect:/member/login.do"; } else
		 * {
		 */	
			dto.setUserid(userid);
			cartService.insert(dto);
			
			return "redirect:/shop/cart/list.do";
		
		
	}
	
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam int cart_id, HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		
		if(userid == null) {	//로그인이 안 됐다면?
			return "redirect:/member/login.do";
		} else {
			cartService.delete(cart_id);
			
			return "redirect:/shop/cart/list.do";
		}
		
	}
	
	
	@RequestMapping("deleteAll.do")
	public String deleteAll(HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		
		if(userid == null) {	//로그인이 안 됐다면?
			return "redirect:/member/login.do";
		} else {
			cartService.deleteAll(userid);
			
			return "redirect:/shop/cart/list.do";
		}
		
	}
	
	
	@RequestMapping("update.do")
	public String update(@RequestParam int[] amount, @RequestParam int[] cart_id, HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		
		if(userid == null) {	//로그인이 안 됐다면?
			return "redirect:/member/login.do";
		} else {
			//cart_id, amount는 배열값으로 전달되며 ArrayList또는 배열로 처리해야함.
			
			for(int i=0; i<cart_id.length; i++) {
				if(amount[i] == 0) {	//수량이 0이면? 레코드 삭제
					cartService.delete(cart_id[i]);
				} else {
					CartDTO dto = new CartDTO();
					dto.setUserid(userid);
					dto.setCart_id(cart_id[i]);
					dto.setAmount(amount[i]);
					
					cartService.modifyCart(dto);
				}
			}
			
			return "redirect:/shop/cart/list.do";
		}
	}
	
}
