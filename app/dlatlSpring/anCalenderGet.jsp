<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.csslect.app.dto.CalenderDTO"%>

<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>
					
<%		
	Gson gson = new Gson();
	String json = gson.toJson((ArrayList<CalenderDTO>)request.getAttribute("anCalenderGet"));
	
	out.println(json);
	System.out.println(json);
%>