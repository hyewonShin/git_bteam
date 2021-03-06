package member;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired private MemberDAO dao;
	
	@Override
	public int member_insert(MemberVO vo) {
		return dao.member_insert(vo);
	}

	@Override
	public MemberVO member_login(HashMap<String, String> map) {
		return dao.member_login(map);
	}

	@Override
	public int member_update(MemberVO vo) {
		return dao.member_update(vo);
	}

	@Override
	public int member_delete(String m_tel) {
		return dao.member_delete(m_tel);
	}

	@Override
	public int member_join_select(HashMap<String, String> map) {
		return dao.member_join_select(map);
	}

	@Override
	public MemberVO member_sns_login(String snsId) {
		return dao.member_sns_login(snsId);
	}
	
	@Override
	public int member_sns_update(MemberVO vo) {
		return dao.member_sns_update(vo);
	}

	@Override
	public String member_find_email(String m_tel) {
		return dao.member_find_email(m_tel);
	}

	@Override
	public int member_find_pw(HashMap<String, String> map) {
		return dao.member_find_pw(map);
	}
	

}
