<%@page import="calendar.DiagnosisDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Gson gson = new Gson();
String json = gson.toJson(request.getAttribute("list"), new TypeToken<List<DiagnosisDTO>>(){}.getType()); 
out.print(json);
%>