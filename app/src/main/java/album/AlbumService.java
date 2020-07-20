package album;

import java.util.List;

public interface AlbumService {
	//앨범 리스트 가져오기
	List<AlbumVO> album_list(String a_pet);
	//앨범 상세 정보 가져오기
	AlbumVO album_detail(int a_num);
	//앨범 정보 추가하기
	int album_insert(AlbumVO vo);
	//앨범 정보 수정하기
	int album_update(int a_num);
	//앨범 정보 삭제하기
	int album_delete(int a_num);
}
