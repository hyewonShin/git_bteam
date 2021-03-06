package member;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO implements MemberService {
	@Autowired private SqlSession sql;
	
	@Override
	public int member_insert(MemberVO vo) {
		return sql.insert("member.mapper.join", vo);
	}

	@Override
	public MemberVO member_login(HashMap<String, String> map) {
		return sql.selectOne("member.mapper.login", map);
	}

	@Override
	public int member_update(MemberVO vo) {
		return sql.update("member.mapper.update", vo);
	}

	@Override
	public int member_delete(String m_tel) {
		return sql.delete("member.mapper.delete", m_tel);
	}

	@Override
	public int member_join_select(HashMap<String, String> map) {
		return (Integer)sql.selectOne("member.mapper.join_select", map);
	}

	@Override
	public MemberVO member_sns_login(String snsId) {
		return sql.selectOne("member.mapper.snsLogin", snsId);
	}

	@Override
	public int member_sns_update(MemberVO vo) {
		return sql.update("member.mapper.snsUpdate", vo);
	}

	@Override
	public String member_find_email(String m_tel) {
		return (String)sql.selectOne("member.mapper.findEmail", m_tel);
	}

	@Override
	public int member_find_pw(HashMap<String, String> map) {
		return sql.update("member.mapper.findPw", map);
	}

}
