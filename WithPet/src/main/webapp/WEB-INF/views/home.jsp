<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
<style type="text/css">
body { margin: 0; }
#animation_canvas {
	width: 1400px;	height: 520px;	margin: 0px auto 10px auto;
	overflow: hidden; position: relative; }
.slider_panel{	width: 9600px;	position: relative; }
.slider_image{	float: left; width: 1920px;	height: 520px; }
.slider_text_panel { position: absolute; top: 300px; left: 900px; }
.slider_text {	position: absolute;	width: 350px; height: 150px; color: white; text-align: left;}
.control_panel { position: absolute; top: 490px; left: 46%; height: 20px;	overflow: hidden; }
.control_button {
	width: 20px; height: 60px; position: relative; float: left;
	cursor: pointer; background: url('img/point_button.png');
}
.control_button.active { top: -40px; }
</style>
<script type="text/javascript" src="js/home.js"></script>
</head>
<body>
<div id="animation_canvas">
	<div class="slider_panel">
		<a href="http://pawinhand.kr/" target="_blank"><img src="img/ani_img1.jpg" class="slider_image" /></a> 
		<a href="https://post.naver.com/viewer/postView.nhn?volumeNo=27991932&memberNo=21959512" target="_blank"><img src="img/ani_img2.jpg" class="slider_image" /></a> 
		<a href="https://post.naver.com/viewer/postView.nhn?volumeNo=27940123&memberNo=44895236" target="_blank"><img src="img/ani_img3.jpg" class="slider_image" /></a> 
		<a href="https://blog.naver.com/africaamc/221907418741" target="_blank"><img src="img/ani_img4.jpg" class="slider_image" /></a> 
		<a href="https://post.naver.com/viewer/postView.nhn?volumeNo=27991201&memberNo=21959512" target="_blank"><img src="img/ani_img5.jpg" class="slider_image" /></a>
	</div>
	<div class="slider_text_panel">
		<div class="slider_text">
			<h1>반려동물,<br />사지말고 입양하세요</h1>
			<p>3년간 8만 5천여 마리의 유기동물이<br />안락사되고 있습니다</p>
		</div>
		<div class="slider_text">
			<h1>반려동물 털 제거에는?</h1>
			<p>세탁기에 OOO 2장!</p>
		</div>
		<div class="slider_text">
			<h1>우리집 말썽쟁이<br />고양이!</h1>
			<p>고양이를 봉인하는 방법이 있다면?</p>
		</div>
		<div class="slider_text">
			<h1>생야채 vs 삶은 야채</h1>
			<p>강아지에게 무엇이 좋을까?</p>
		</div>
		<div class="slider_text">
			<h1>왔냥?</h1>
			<p>집사 맞이하는 고양이 유형 6</p>
		</div>
	</div>
	<div class="control_panel">
		<div class="control_button"></div>
		<div class="control_button"></div>
		<div class="control_button"></div>
		<div class="control_button"></div>
		<div class="control_button"></div>
	</div>
</div>
</body>
</html>
