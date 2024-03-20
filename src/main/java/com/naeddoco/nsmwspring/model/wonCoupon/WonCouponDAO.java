package com.naeddoco.nsmwspring.model.wonCoupon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("wonCouponDAO")
@Slf4j
public class WonCouponDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
//------------------------------------------------------------------------------------------------
	public List<WonCouponDTO> selectAll(WonCouponDTO wonCouponDTO) {
		log.debug("selectAll start");
		return (List<WonCouponDTO>)jdbcTemplate.query(SELECTALL, new WonCouponRowMapper());
	}

	
	public WonCouponDTO selectOne(WonCouponDTO wonCouponDTO) {

//		Object[] args = { wonCouponDTO.getWonCouponID()};
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new WonCouponRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(WonCouponDTO wonCouponDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(WonCouponDTO wonCouponDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(WonCouponDTO wonCouponDTO) {
		
//		int result = jdbcTemplate.update(DELETE);
//		if(result <= 0) {
//			log.debug("delete 성공");
			return false;
//		}
//		log.debug("delete 성공");
//		return true;
	}	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class WonCouponRowMapper implements RowMapper<WonCouponDTO> {
	@Override
	public WonCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		WonCouponDTO data = new WonCouponDTO();
		
		data.setWonCouponID(rs.getInt("WON_COUPON_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCouponDiscountAmount(rs.getInt("COUPON_DISCOUNT_AMOUNT"));
		data.setMinOrderAmount(rs.getInt("MIN_ORDER_AMOUNT"));
			
		log.debug(Integer.toString(rs.getInt("WON_COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_DISCOUNT_AMOUNT")));
		log.debug(Integer.toString(rs.getInt("MIN_ORDER_AMOUNT")));
		
		return data;
	}
	
}