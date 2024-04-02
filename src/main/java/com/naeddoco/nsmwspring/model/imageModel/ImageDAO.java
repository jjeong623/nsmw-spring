package com.naeddoco.nsmwspring.model.imageModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("imageDAO")
@Slf4j
public class ImageDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 최근에 추가된 하나의 데이터를 가져오는 쿼리문
	private static final String SELECTALL_GET_LAST_ONE = "SELECT IMAGE_ID FROM IMAGE ORDER BY IMAGE_ID DESC LIMIT 1";

	// 이미지 데이터를 가져오는 쿼리문
	private static final String SELECTONE_IMAGE = "SELECT IMAGE_ID, IMAGE_PATH FROM IMAGE WHERE IMAGE_ID = ?";
	
	// 쿠폰 이미지 삭제 후 다운로드 쿠폰 UPDATE시 필요
	private static final String SELECTONE_IMAGE_ID = "SELECT IMAGE_ID FROM IMAGE WHERE IMAGE_PATH  = ? ";
	
	// 이미지 경로 데이터를 추가하는 쿼리문
	private static final String INSERT = "INSERT INTO IMAGE (IMAGE_PATH) VALUES (?)";
	
	private static final String UPDATE = "";
	
	// 이미지ID로 데이터 삭제
	private static final String DELETE = "DELETE FROM IMAGE WHERE IMAGE_ID = ?";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	
	public List<ImageDTO> selectAll(ImageDTO imageDTO) {
		
		log.debug("selectAll 진입");
		
		if(imageDTO.getSearchCondition().equals("getLastOne")) {
			
			log.debug("getLastOne 진입");
		
			try {
			
				return jdbcTemplate.query(SELECTALL_GET_LAST_ONE, new getLastOneRowMapper());
		
			} catch (Exception e) {
				
				log.debug("getLastOne 예외 발생");

				return null;

			} 	
		
		} 
			
		log.debug("selectAll 실패");
		
		return null;
		
	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public ImageDTO selectOne(ImageDTO imageDTO) {
		
		log.debug("selectOne 진입");

		if(imageDTO.getSearchCondition().equals("deleteAdminProductImageDatas")) {
			
			log.debug("deleteAdminProductImageDatas 진입");
			
			Object[] args = { imageDTO.getImageID() };
			
			try {
			
				return jdbcTemplate.queryForObject(SELECTONE_IMAGE, args, new iamgeRowMapper());
		
			} catch (Exception e) {
				
				log.debug("deleteAdminProductImageDatas 예외 발생");

				return null;

			} 	
			
		}
		
		log.debug("selectOne 실패");
		
		return null;
			
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean insert(ImageDTO imageDTO) {
		
		log.trace("insert 진입");
		
		int result = 0;
		
		try {
	
			result = jdbcTemplate.update(INSERT, imageDTO.getImagePath());
			
			if(result <= 0) {
				
				log.error("insert 실패");
			
				return false;
			
			}
		
		} catch (Exception e) {
			
			log.error("insert 예외 발생");

			return false;

		} 	
		
		log.trace("insert 성공");
		
		return true;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean update(ImageDTO imageDTO) {

//		int result = jdbcTemplate.update(UPDATE);
		
//		if(result <= 0) {
		
			return false;
			
//		}
			
//		log.debug("update 성공");
			
//		return true;
			
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean delete(ImageDTO imageDTO) {
		
		int result = 0;
		
		if(imageDTO.getSearchCondition().equals("deleteAdminProductImageDatas")) {
			
			try {
				
				result = jdbcTemplate.update(DELETE, imageDTO.getImageID());
				
				if(result <= 0) {
					
					log.debug("deleteAdminProductImageDatas 실패");
			
					return false;
				
				}
			
			} catch (Exception e) {
				
				log.debug("deleteAdminProductImageDatas 예외 발생");
				
				return false;
				
			}
			
		}	
			
		log.debug("delete 성공");
			
		return true;
			
	}	
	
}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
class getLastOneRowMapper implements RowMapper<ImageDTO> {
	
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setImageID(rs.getInt("IMAGE_ID"));
		
		return imageDTO;
		
	}
	
}

class iamgeRowMapper implements RowMapper<ImageDTO> {
	
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setImageID(rs.getInt("IMAGE_ID"));
		imageDTO.setImagePath(rs.getString("IMAGE_PATH"));
		
		return imageDTO;
		
	}
	
}
