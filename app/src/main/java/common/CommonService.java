package common;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class CommonService {
	
	//파일 디렉토리 생성
	public void makeDir(HttpServletRequest req, String category){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources/upload/" + category));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
	
	//SMS 전송
	public int sendSms(String m_telin, String randomNum) {
		int succ = -100;
		
		JSONObject obj = null;
		String api_key = "NCSH7PJQL6DZNESC";
	    String api_secret = "IVCP6BVIKLJOBHNLXINHHHUVODJGBSNT";
	    Message coolsms = new Message(api_key, api_secret);
	    
	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", m_telin);
	    params.put("from", "01099287824");
	    params.put("type", "SMS");
	    params.put("text", "펫과함께 인증번호는 [" + randomNum + "]입니다.");
	    params.put("app_version", "펫과함께 1.0.0"); // application name and version

	    try {
	      obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	      
	      String success_count = obj.get("success_count").toString();
	      String error_count = obj.get("error_count").toString();
	      System.out.println("succ:" + success_count + "err : " + error_count);
	      
	      if(success_count.equals("1")) {
	    	  succ = 1;
	      }
	      
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	    
	    return succ;
	}
}
