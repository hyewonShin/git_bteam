package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {
	
	
	public String json_list(StringBuilder url) {
		JSONObject json = null;
		try {
			json = (JSONObject)new JSONParser().parse( xml_list(url) );
			json = (JSONObject)json.get("response");
			json = (JSONObject)json.get("body");
			//items 가 데이터를 갖고 있어서 JSONDbiect 타입으로 형변환가능한 경우만
			int count = json.get("totalCount") == null ? 0 :Integer.parseInt(json.get("totalCount").toString());
			if(json.get("items") instanceof JSONObject ) {
				json = (JSONObject)json.get("items");				
			}
			json.put("count", count);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return json.toJSONString();
	}
	
	//공공데이터 REST API 요청처리
	public String xml_list(StringBuilder url) {
		String result = url.toString();
		try {
			HttpURLConnection conn = (HttpURLConnection)new URL( result ).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"utf-8"));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        
	        result = sb.toString();
	        
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(result);
			return result;
	}
	
	
	public void sendEmail(String email, String name, HttpSession session) {
		//기본이메일 전송처리
		//sendSimple(email, name);
		
		//2.첨부파일있는 이메일 전송처리
		//sendAttach(email, name, session);
		
		//3.HTML 태그 이메일로 전송처리
		sendHtml( email, name, session);
	}
	
	private void sendHtml(String email, String name, HttpSession session) {
		HtmlEmail mail = new HtmlEmail();
		
		mail.setHostName("smtp.naver.com");		//메일전송 서버지정
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		mail.setAuthentication("qorentjdbds", "100entjd");
		mail.setSSLOnConnect(true);
		
		try {
			mail.setFrom("qorentjdbds@naver.com", "Donald Trump");	//메일송신자
			mail.addTo(email,name);
			
			mail.setSubject("한울 IoT과정 - HTML");
			
			StringBuffer msg= new StringBuffer();
			msg.append("<html>");
			msg.append("<body>");
			msg.append("<a href='https://www.youtube.com/?gl=KR'><img src='https://www.youtube.com/?gl=KR'/></a>");
			msg.append("<hr>");
			msg.append("<h3>한울 IoT 과정 가입축하</h3>");
			msg.append("<p>가입을 축하드립니다.</p>");
			msg.append("<p>다시 집에가라 애송아.</p>");
			msg.append("</body>");
			msg.append("</html>");
			
			mail.setHtmlMsg(msg.toString());
			
			EmailAttachment file = new EmailAttachment();
			file.setPath(session.getServletContext().getRealPath("resources/css/common.css"));
			mail.attach(file);
			
			file = new EmailAttachment();
			file.setURL(new URL("https://www.youtube.com/?gl=KR"));
			mail.attach(file);
			
			mail.send();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void sendAttach(String email, String name, HttpSession session) {
		MultiPartEmail mail = new MultiPartEmail();
		
		mail.setHostName("smtp.naver.com");		//메일전송 서버지정
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		mail.setAuthentication("qorentjdbds", "100entjd");
		mail.setSSLOnConnect(true);
		
		
		try {
			mail.setFrom("qorentjdbds@naver.com", "내가누구게");	//메일송신자
			mail.addTo(email,name);
			
			mail.setSubject("한울 IoT과정 - 첨부파일");
			mail.setMsg("한울 IoT과정 가입 축하!!! 첨부파일 확인 요망!");
			
			//파일 첨부하기
			EmailAttachment file = new EmailAttachment();
			//물리적 디스크네 파일 첨부
			file.setPath("C:\\Users\\hanul\\Desktop\\aaa.gif");
			mail.attach(file);
			//프로젝트 내의 파일첨부
			file = new EmailAttachment();
			file.setPath(session.getServletContext().getRealPath("resources/images/hanul.logo.png"));
			mail.attach(file);
			
			
			//URL을 통해 파일 첨부
			file = new EmailAttachment();
			file.setURL(new URL("https://www.youtube.com/channel/UCn9mJ4htO64-1osMWYu9k5Q"));
			mail.attach(file);
			
			mail.send();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//이메일 
	private void sendSimple(String email, String name) {
		SimpleEmail mail= new SimpleEmail();
		
		mail.setHostName("smtp.naver.com");		//메일전송 서버지정
		mail.setCharset("utf-8");
		mail.setDebug(true);
		//메일 인증
		mail.setAuthentication("qorentjdbds", "100entjd");
		mail.setSSLOnConnect(true);
		
		try {
			mail.setFrom("qorentjdbds@naver.com", "관리자");	//메일송신자
			mail.addTo(email,name);								//메일 수신자
			
			mail.setSubject("한울 IoT과정");					//메일 제목
			mail.setMsg(name + "님 IoT과정 회원가입을 축하합니다!");
			
			
			mail.send();										//발송버튼 클릭
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//첨부파일 업로드 처리

	public String upload(String category, MultipartFile file, HttpSession session) {
		//업로드할 서버의 물리적인 위치
		//D:\Study_Spring\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\IoT\resources
		String resources = session.getServletContext().getRealPath("resources");
		String upload = resources + "/upload";
		
		//업로드할 파일의 형태: ../upload/notice/2020/07/13/abc.txt
		String folder = upload + "/" + category + "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		
		//폴더가 없다면 폴더를 생성
		File f = new File(folder);
		if(!f.exists()) f.mkdirs();	//폴더 없으면 폴더 생성
		
		//동시다발적 동일명의 파일 업로드를 위한 고유 ID부여 : ex)dsaf123_abc.txt
		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo( new File(folder, uuid));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// ~~/upload/ .../dsaf123_abc.txt
		//folder.replace(resources,"")도 가능하다
		return folder.substring(resources.length()) + "/" + uuid;
		
	}
	
	//첨부파일 다운로드 처리
	public File download(String filename, String filepath, HttpSession session, HttpServletResponse response) {
		File file = new File(session.getServletContext().getRealPath("resources/") + filepath);
		String mine = session.getServletContext().getMimeType(filename);
		
		response.setContentType(mine);
		
		try {
			filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20" );
			response.setHeader("content-disposition", "attachment; filename=" + filename);
			
			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();
			
		} catch (Exception e) {
			e.getMessage();
		}
		return file;
	}
	
	
	
}
