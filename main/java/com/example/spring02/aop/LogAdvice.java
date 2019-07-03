package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //스프링에서 관리하는 범용 bean

@Aspect //Aspect bean 등록
public class LogAdvice {
	
	//로깅을 위한 변수
	private static final Logger logger
	=LoggerFactory.getLogger(LogAdvice.class);
	
	//@Before(핵심업무 전), @After(핵심업무 후), @Around(전,후 모두 사용)
	
	//..은 모든 하위패키지를 의미  (..)모든 메소드를 의미
//	@Around(
//	"execution(* com.example.spring02.controller..*Controller.*(..))"
//	+ " or execution(* com.example.spring02.service..*Impl.*(..))"
//	+ " or execution(* com.example.spring02.model..dao.*Impl.*(..))")
	
	public Object logPrint(ProceedingJoinPoint joinPoint) 
			throws Throwable { 
		long start=System.currentTimeMillis();//시스템의 밀리세컨드 값
		Object result=joinPoint.proceed();
		//호출한 클래스 이름(getDeclaringTypeName)
		String type=joinPoint.getSignature().getDeclaringTypeName();
		String name="";
		if(type.indexOf("Controller") > -1) {
			name="Controller \t:";//탭처리
		}else if(type.indexOf("Service") > -1) {
			name="ServiceImpl \t:";
		}else if(type.indexOf("DAO") > -1) {
			name="DAOImpl \t:";
		}
		//호출한 클래스, method 정보를 Logger에 저장
		logger.info(name+type+"."+joinPoint.getSignature().getName()+"()");
		//method에 전달되는 매개변수들을 Logger에 저장
		logger.info(Arrays.toString(joinPoint.getArgs()));
		long end=System.currentTimeMillis();
		long time=end-start;
		logger.info("실행시간:"+time);
		return result;
	}
}
