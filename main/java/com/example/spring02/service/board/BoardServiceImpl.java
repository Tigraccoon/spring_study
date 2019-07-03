package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring02.model.board.dao.BoardDAO;
import com.example.spring02.model.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	BoardDAO boardDao;
	
	@Override
	public void deleteFile(String fullName) {
		
		boardDao.deleteFile(fullName);
	}

	@Override
	public List<String> getAttach(int bno) {
		
		return boardDao.getAttach(bno);
	}
	
	//1.글쓰기 - 게시물 번호 생성
	//2.첨부파일 등록 - 게시물 번호 사용
	//위 두개가 하나의 트랜젝션 단위로 처리
	@Transactional
	@Override
	public void create(BoardDTO dto) throws Exception {
		//board 테이블에 레코드 추가
		boardDao.create(dto);
		//attach 테이블에 레코드 추가
		String[] files = dto.getFiles();
		if(files == null) return;
		for(String name : files) {
			boardDao.addAttach(name);
		}
			
	}
	
	@Transactional
	@Override
	public void update(BoardDTO dto) throws Exception {
		
		boardDao.update(dto);
		String[] files = dto.getFiles();
		if(files == null) return;
		for(String name : files) {
			boardDao.updateAttach(name, dto.getBno());
		}
		
	}
	
	@Transactional
	@Override
	public void delete(int bno) throws Exception {
		//board, attach, reply
		
		boardDao.delete(bno);
	}

	@Override
	public List<BoardDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {
		
		return boardDao.listAll(search_option, keyword, start, end);
	}

	@Override
	public void increateViewcnt(int bno, HttpSession session) throws Exception {
		long update_time = 0;
		if(session.getAttribute("update_time_" + bno) != null) {
			//최근에 조회수를 올린 시간
			update_time = (long)session.getAttribute("update_time_" + bno); 
		}
		
		long current_time = System.currentTimeMillis();
		//5초당 조회수 1회
		if(current_time - update_time >= 5 * 1000) {
			//조회수 증가
			boardDao.increateViewcnt(bno);
			//조회수 증가시간 저장
			session.setAttribute("update_time_" + bno, current_time);
		}
		
	}

	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		
		return boardDao.countArticle(search_option, keyword);
	}

	@Override
	public BoardDTO read(int bno) throws Exception {
		
		return boardDao.read(bno);
	}

}
