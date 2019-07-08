team_user table
컬럼명		내용		속성
userid		아이디		pk
pwd			비밀번호	not null
name		이름		not null
email		이메일		unique, not null
tel			전화번호	not null
zipcode		우편번호	
address1	주소		
address2	상세주소	
joindate	가입일		default sysdate
lastlogin	마지막접속	default sysdate
user_group	회원분류 	default 1		0: 탈퇴, 1: 회원, 2: 강사, 3: 관리자
class_name	수강과목/강의과목s


페이지
기능		RequestMapping		페이지						이동 페이지
로그인		user/login.do		/user/login.jsp			->	index.jsp
로그아웃	user/logout.do		-						->	login.jsp
회원가입1	user/signup1.do		/user/signup1.jsp		->	signup2
회원가입2	user/signup2.do		/user/signup2.jsp		->	signup3
회원가입3	user/signup3.do		/user/signup3.jsp		->	login.jsp
id/pwd찾기	user/finduser.do	/user/finduser.jsp		->	finduserresult.jsp
pwd 체크	user/pwdcheck.do	/user/pwdcheck.jsp		->	updateuser.jsp
정보수정	user/updateuser.do	/user/updateuser.jsp	->	index.jsp
회원탈퇴	user/deleteuser.do	-						->	index.jsp






