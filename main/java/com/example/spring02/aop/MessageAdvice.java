package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component	//기타 범용 bean
@Aspect		//AOP Bean - 공통 업무를 지원하는 코드
public class MessageAdvice {
	private static final Logger logger = LoggerFactory.getLogger(MessageAdvice.class);
	
	@Before("execution(* com.example.spring02.service.message.MessageService*.*(..))")
	public void stratLog(JoinPoint jp) {
		//JoinPoint : @After, @Before일때 사용
		logger.info("핵심 업무 코드의 정보 : " + jp.getSignature());
		logger.info("method : " + jp.getSignature().getName());
		logger.info("매개변수 : " + Arrays.deepToString(jp.getArgs()));
	}
	
	@Around("execution(* com.example.spring02.service.message.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		//ProceedingJoinPoint는 @Around일때만 사용
		//호출 전
		long start = System.currentTimeMillis();
		Object result = pjp.proceed();
		
		//호출 후
		long end = System.currentTimeMillis();
		logger.info(pjp.getSignature().getDeclaringTypeName() + "걸린 시간 : " + (end-start) + " ms");
		logger.info("------------------------");
		
		return result;
	}
	
	
}
