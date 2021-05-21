package com.mfi.service;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfi.model.BlackList;
import com.mfi.repository.BlackListRepository;

@Service
@Transactional
public class BlackListService {

	@Autowired
	private BlackListRepository bkRep;
	
	public List<BlackList> selectAllBk(){
		return bkRep.findAll();
		
	}
	
//	public void saveBk(BlackList bk) {
//		bkRep.save(bk);
//	}
//	
	
	public void saveBk( String name, String nrc, String address, String fatherName, int createdUser,
			Date createdDate, int updateUser, Date updateDate,String remark) {
		BlackList blackList=new BlackList();
		blackList.setName(name);
		blackList.setNrc(nrc);
		blackList.setAddress(address);
		blackList.setFatherName(fatherName);
		blackList.setCreatedUser(createdUser);
		blackList.setCreatedDate(createdDate);
		blackList.setUpdateUser(updateUser);
		blackList.setUpdateDate(updateDate);
		blackList.setRemark(remark);
		bkRep.save(blackList);
	}
	
	
	
	
	public void updateBk(BlackList bk) {
		bkRep.save(bk);
	}
	
	public void deleteBk(int id) {
		bkRep.deleteById(id);
	}
	
	public BlackList selectOneBk(int id) {
		return bkRep.findbyBkid(id);
	}
	public List<BlackList> getbkName(String name){
		return bkRep.findByNameOrNrc(name);
	}
	
	public BlackList findblByNRC(String nrc){
		return bkRep.findByNrc(nrc);
	}
	
	
}









