package com.example.spring02.model.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring02.model.memo.dto.MemoDTO;

public interface MemoDAO {
	
	//Mybatis Interface mapper
	//어노테이션 Select를 통해 sql문을 DAO에서 사용 
	@Select("SELECT * FROM memo ORDER BY idx DESC")
	public List<MemoDTO> list();
	
	@Insert("INSERT INTO memo (idx,writer,memo) "
			+ "VALUES ((SELECT nvl(max(idx)+1,1) FROM memo),#{writer},#{memo})")
	public void insert(@Param("writer") String writer, @Param("memo") String memo);
	
	
	@Select("SELECT * FROM memo WHERE idx=#{idx}")
	public MemoDTO memo_view(int idx);
	
	@Update("UPDATE memo SET writer=#{writer}, memo=#{memo} WHERE idx=#{idx}")
	public void update(MemoDTO dto);
	
	@Delete("DELETE FROM memo WHERE idx=#{idx}")
	public void delete(int idx);
}
