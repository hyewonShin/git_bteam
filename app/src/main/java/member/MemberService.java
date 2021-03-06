package member;

import java.util.HashMap;

public interface MemberService {
	//회원가입시 회원정보 저장
	int member_insert(MemberVO vo);
	//로그인처리
	MemberVO member_login(HashMap<String, String> map);
	//마이페이지에서 회원정보변경저장
	int member_update(MemberVO vo);
	//회원탈퇴
	int member_delete(String m_tel);
	//회원가입시 중복 확인
	int member_join_select(HashMap<String, String> map);
	//SNS 계정으로 로그인
	MemberVO member_sns_login(String snsId);
	//SNS 계정 추가 연동
	int member_sns_update(MemberVO vo);
	//이메일 찾기
	String member_find_email(String m_tel);
	//비밀번호 찾기
	int member_find_pw(HashMap<String, String> map);
}
