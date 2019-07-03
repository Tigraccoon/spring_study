package com.example.spring02.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring02.model.board.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public void deleteFile(String fullName) {
		
		sqlSession.delete("board.deleteFile", fullName);
	}

	@Override
	public List<String> getAttach(int bno) {
		
		return sqlSession.selectList("board.getAttach", bno);
	}

	@Override
	public void addAttach(String fullName) {
		
		sqlSession.insert("board.addAttach", fullName);
	}

	@Override
	public void updateAttach(String fullName, int bno) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("fullName", fullName);	//첨부파일 이름
		map.put("bno", bno);			//글번호
		
		sqlSession.insert("board.updateAttach", map);
		//게시물 board는 update지만 첨부파일 attach는 기존 파일이 있다고 하더라도 기존의 파일은 그대로 두고 새 파일을 올려서 수정하는 것이므로
		//update처럼 보이지만 insert이다.
	}

	@Override
	public void create(BoardDTO dto) throws Exception {
		
		sqlSession.insert("board.insert", dto);
	}

	@Override
	public void update(BoardDTO dto) throws Exception {
		
		sqlSession.update("board.update", dto);
	}

	@Override
	public void delete(int bno) throws Exception {
		
		sqlSession.delete("board.delete", bno);
	}

	@Override
	public List<BoardDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("search_option", search_option);
		map.put("keyword", "%" + keyword + "%");
		
		return sqlSession.selectList("board.listAll", map);
	}

	@Override
	public void increateViewcnt(int bno) throws Exception {
		
		sqlSession.update("board.increaseViewcnt", bno);
	}

	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("keyword", "%" + keyword + "%");
		
		return sqlSession.selectOne("board.countArticle", map);
	}

	@Override
	public BoardDTO read(int bno) throws Exception {
		
		return sqlSession.selectOne("board.read", bno);
	}

}
