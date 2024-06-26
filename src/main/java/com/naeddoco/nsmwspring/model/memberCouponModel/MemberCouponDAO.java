package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("memberCouponDAO")
@Slf4j
public class MemberCouponDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 마이페이지-쿠폰목록 조회
	// 해당 회원이 가지고있는 쿠폰 전체 조회
	// 미사용쿠폰 -> 사용 쿠폰, 만료일 최근순으로 정렬
	// 4/3 카테고리명 추가
	private static final String SELECTALL_MY_COUPON = "SELECT DISTINCT "
														+ "MC.MEMBER_COUPON_ID, "
														+ "MC.COUPON_ID, "
														+ "MC.COUPON_USAGE, "
														+ "C.COUPON_NAME, "
														+ "C.EXPIRATION_DATE, "
														+ "C.COUPON_TYPE, "
														+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
																+ "FROM CATEGORY CA "
																+ "JOIN COUPON_CATEGORY CC "
																+ "ON CA.CATEGORY_ID = CC.CATEGORY_ID "
																+ "WHERE C.COUPON_ID = CC.COUPON_ID)) AS CATEGORY_NAME, "
														+ "CASE "
															+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
															+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
														+ "END AS DISCOUNT, "
														+ "CASE "
															+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
															+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
														+ "END AS AMOUNT_LIMIT "
														+ "FROM "
															+ "MEMBER_COUPON MC "
														+ "JOIN "
															+ "COUPON C ON MC.COUPON_ID = C.COUPON_ID "
														+ "LEFT JOIN "
															+ "WON_COUPON W ON C.COUPON_ID = W.COUPON_ID "
														+ "LEFT JOIN "
															+ "PERCENTAGE_COUPON P ON C.COUPON_ID = P.COUPON_ID "
														+ "JOIN "
															+ "COUPON_CATEGORY CC ON C.COUPON_ID = CC.COUPON_ID "
														+ "JOIN "
															+ "CATEGORY CAT ON CAT.CATEGORY_ID = CC.CATEGORY_ID "
														+ "WHERE "
															+ "MC.MEMBER_ID = ? "
														+ "ORDER BY "
															+ "MC.COUPON_USAGE ASC, C.EXPIRATION_DATE ASC";
	
	// 구매페이지에서 사용가능한 쿠폰 조회시 사용
	// 카테고리, 쿠폰 사용여부 확인 필요
	//private static final String SELECTALL_MY_COUPON_BY_BUY_PAGE = "";
			
	// DOWNLOAD쿠폰(팝업), GRADE쿠폰, BATCH쿠폰 INSERT시 사용
	private static final String INSERT = "INSERT INTO MEMBER_COUPON (MEMBER_ID, COUPON_ID) VALUES (?,?)";
	
	

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	

	public List<MemberCouponDTO> selectAll(MemberCouponDTO memberCouponDTO) {
		
		log.trace("selectAll 진입");

		if (memberCouponDTO.getSearchCondition().equals("selectAllMyCoupon")) {
			
			log.trace("selectAllMyCoupon 진입");
			
			Object[] args = { memberCouponDTO.getMemberID() };
			
			try {
				return (List<MemberCouponDTO>)jdbcTemplate.query(SELECTALL_MY_COUPON, args, new SelectAllMemberCouponRowMapper());
			
			} catch (Exception e) {

				log.error("selectAllMyCoupon 예외/실패" + e.getMessage(), e);
				return null;

			}

		}

		log.error("selectAll 실패");
		return null;

	}

	
/*-----------------------------------[ insert ] ------------------------------------------------------------------------------------------------------------*/
	
	public boolean insert(MemberCouponDTO memberCouponDTO) {
	
		log.trace("insert 진입");
		
		int result = jdbcTemplate.update(INSERT,memberCouponDTO.getMemberID(), memberCouponDTO.getCouponID() );
		
		if(result <= 0) {
			
			log.error("insert 실패");
			return false;
		}
		
		log.trace("insert 성공");
		return true;
	}

}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

@Slf4j
class MemberCouponRowMapper implements RowMapper<MemberCouponDTO> {
	@Override
	public MemberCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberCouponDTO data = new MemberCouponDTO();
		
		data.setMemberCouponID(rs.getInt("MEMBER_COUPON_ID"));
		data.setMemberID(rs.getString("MEMBER_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCouponUsage(rs.getString("COUPON_USAGE"));
			
		log.debug("MEMBER_COUPON_ID : " + Integer.toString(rs.getInt("MEMBER_COUPON_ID")));
		log.debug("MEMBER_ID : " + rs.getString("MEMBER_ID"));
		log.debug("COUPON_ID : " + Integer.toString(rs.getInt("COUPON_ID")));
		log.debug("COUPON_USAGE : " + rs.getString(rs.getInt("COUPON_USAGE")));
		
		return data;
	}
	
}

@Slf4j
class SelectAllMemberCouponRowMapper implements RowMapper<MemberCouponDTO> {
	@Override
	public MemberCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberCouponDTO data = new MemberCouponDTO();
		
		data.setMemberCouponID(rs.getInt("MC.MEMBER_COUPON_ID"));
		data.setMemberID(rs.getString("MC.COUPON_ID"));
		data.setCouponUsage(rs.getString("MC.COUPON_USAGE"));
		data.setAncCouponName(rs.getString("C.COUPON_NAME"));
		data.setAncExpirationDate(rs.getTimestamp("C.EXPIRATION_DATE"));
		data.setAncCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncCategoryName(rs.getString("CATEGORY_NAME"));
		data.setAncDiscount(rs.getInt("DISCOUNT"));
		data.setAncAmount(rs.getInt("AMOUNT_LIMIT"));
		
		log.debug("MEMBER_COUPON_ID: " + rs.getInt("MC.MEMBER_COUPON_ID"));
		log.debug("COUPON_ID: " + rs.getString("MC.COUPON_ID"));
		log.debug("COUPON_USAGE: " + rs.getString("MC.COUPON_USAGE"));
		log.debug("COUPON_NAME: " + rs.getString("C.COUPON_NAME"));
		log.debug("EXPIRATION_DATE: " + rs.getTimestamp("C.EXPIRATION_DATE"));
		log.debug("COUPON_TYPE: " + rs.getString("C.COUPON_TYPE"));
		log.debug("CATEGORY_NAME: " + rs.getString("CATEGORY_NAME"));
		log.debug("DISCOUNT: " + rs.getInt("DISCOUNT"));
		log.debug("AMOUNT_LIMIT: " + rs.getInt("AMOUNT_LIMIT"));
		
		return data;
	}
	
}