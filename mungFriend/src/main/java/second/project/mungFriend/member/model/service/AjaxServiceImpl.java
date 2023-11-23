package second.project.mungFriend.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.member.model.dao.AjaxDAO;

@Service
public class AjaxServiceImpl implements AjaxService{
	
	@Autowired
	private AjaxDAO dao;

	// 아이디 중복검사
	@Override
	public int checkId(String id) {
		
		return dao.checkId(id);
	}
	
	

}
