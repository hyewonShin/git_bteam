<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--json형태를 사용 하기 위해서 import --%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%--model를 사용 하기 위해서 import --%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>


<%@page import="com.csslect.app.dto.DiagnosisDTO"%>

<%
	Gson gson = new Gson();
	String json = gson.toJson((ArrayList<DiagnosisDTO>) request.getAttribute("anDiagnosisGet"));

	out.println(json);
	System.out.println(json);
%>