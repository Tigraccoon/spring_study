package com.example.spring02.controller.message;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.message.dto.MessageDTO;
import com.example.spring02.service.message.MessageService;

@RestController //스프링 4.0부터 사용가능(@Contoller + @ResponseBody)
@RequestMapping("messages/*") //공통적인 url mapping
public class MessageController {
	@Inject
	MessageService messageService;
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageDTO dto){
	//@RequestBody : 클라이너트=>서버(json 데이터가 입력될 때) 리턴값이 json
		ResponseEntity<String> entity=null;
		try {
			messageService.addMessage(dto);
			//ResponseEntity => 에러메시지+에러코드
			entity=new ResponseEntity<>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);//400 에러 : 상호간 변수등이 안맞을때
		}
		return entity;
	}
}
