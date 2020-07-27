/**
 * 첨부파일 관련 처리
 */

/*파일을 선택했을때 파일명이 보이게 처리*/

$('#attach-file').on('change',function(){
	$('#file-name').text( this.files[0].name );
	$('#delete-file').css('display', 'inline');
});

/*선택된 첨부파일을 삭제하고 첨부하지 않는 형태로 처리*/
$('#delete-file').on('click', function(){
	$('#file-name').text('');
	$('#delete-file').css('display', 'none');
	$('#attach-file').val('');
});