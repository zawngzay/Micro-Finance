package com.mfi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfi.model.BlackList;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList,Integer> {
//	@Query(value = "SELECT * FROM black_list bl WHERE bl.name=?1 or bl.nrc=?2", nativeQuery = true)
	
	
	@Query("select bk from BlackList bk where bk.name=?1 or bk.nrc=?1 ")
	public List<BlackList> findByNameOrNrc(String name);
	
	@Query("select bk from BlackList bk where bk.blackListId=?1 ")
	public BlackList findbyBkid (int id);
	
	public BlackList findByNrc(String nrc);

}
