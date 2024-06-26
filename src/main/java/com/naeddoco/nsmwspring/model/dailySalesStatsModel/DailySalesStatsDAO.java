package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.couponModel.CouponDTO;
import com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel.DailyProductSalesStatsDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("dailySalesStatsDAO")
@Slf4j
public class DailySalesStatsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 관리자 메인 대시보드 화면에서
	// 최근 일주일의 일자별 요약 출력
	private static final String SELECTALL_DASHBOARD_DATA = "SELECT DAILY_SALES_STATS_ID, "
															+ "DAILY_TOTAL_CALCULATE_DATE, "
															+ "DAILY_TOTAL_GROSS_MARGINE, "
															+ "DAILY_TOTAL_NET_PROFIT "
														+ "FROM DAILY_SALES_STATS "
														+ "ORDER BY DAILY_TOTAL_CALCULATE_DATE DESC "
														+ "LIMIT 7";
	
	// 기간별 판매 통계 조회
	private static final String SELECTALL_ADMIN_STAT_DATA = "SELECT "
															+ "DAILY_SALES_STATS_ID, "
															+ "DAILY_TOTAL_CALCULATE_DATE, "
															+ "DAILY_TOTAL_GROSS_MARGINE, "
															+ "DAILY_TOTAL_NET_PROFIT "
														+ "FROM "
															+ "DAILY_SALES_STATS "
														+ "WHERE "
															+ "DAILY_TOTAL_CALCULATE_DATE BETWEEN ? AND ? "
														+ "ORDER BY "
															+ "DAILY_TOTAL_CALCULATE_DATE ";

	// 전일의 매출 통계를 추가하는 쿼리
	// DAILY_PRODUCT_SALES_STATS 테이블에 만들어놓은 매출과 이익의 합을
	// 해당 일자에 추가함
	// 이벤트 스케줄러를 사용해 시스템에서 자동으로 추가되게 구현할 예정
	/* private static final String INSERT = "INSERT INTO DAILY_SALES_STATS "
											+ "(DAILY_TOTAL_CALCULATE_DATE,"
											+ "DAILY_TOTAL_GROSS_MARGINE, "
											+ "DAILY_TOTAL_NET_PROFIT) "
										+ "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY), "
											+ "SUM(DAILY_TOTAL_GROSS_MARGINE), "
											+ "SUM(DAILY_TOTAL_NET_PROFIT) "
										+ "FROM DAILY_PRODUCT_SALES_STATS "
										+ "WHERE DAILY_TOTAL_CALCULATE_DATE = DATE_SUB(CURDATE(), INTERVAL 1 DAY) "
										+ "GROUP BY DAILY_TOTAL_CALCULATE_DATE"; */
	
	//샘플데이터 추가시 사용
	private static final String INSERT_SAMPLE_DATA = "INSERT INTO DAILY_SALES_STATS "
														+ "(DAILY_TOTAL_CALCULATE_DATE, DAILY_TOTAL_GROSS_MARGINE, DAILY_TOTAL_NET_PROFIT) "
													+ "SELECT "
														+ "DAILY_TOTAL_CALCULATE_DATE, "
														+ "SUM(DAILY_TOTAL_GROSS_MARGINE), "
														+ "SUM(DAILY_TOTAL_NET_PROFIT) "
													+ "FROM "
														+ "DAILY_PRODUCT_SALES_STATS "
													+ "WHERE "
														+ "DAILY_TOTAL_CALCULATE_DATE = ? "
													+ "GROUP BY "
														+ "DAILY_TOTAL_CALCULATE_DATE";
	

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	

	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO) {
		
		log.trace("selectAll 진입");

		if(dailySalesStatsDTO.getSearchCondition().equals("selectDashboardDatas")) {

			log.trace("selectDashboardDatas 진입");

			try {
				return (List<DailySalesStatsDTO>)jdbcTemplate.query(SELECTALL_DASHBOARD_DATA, new DailySalesStatsRowMapper());

			} catch (Exception e) {

				log.error("selectDashboardDatas 예외/실패"+ e.getMessage());
				return null;
			}
		}
		else if(dailySalesStatsDTO.getSearchCondition().equals("selectAdminStatDateDatas")) {

			Object[] args = { dailySalesStatsDTO.getAncStartDate(), dailySalesStatsDTO.getAncEndDate()};

			log.trace("selectAdminStatDateDatas 진입");

			try {
				
				return (List<DailySalesStatsDTO>)jdbcTemplate.query(SELECTALL_ADMIN_STAT_DATA, args, new DailySalesStatsRowMapper());
				
			} catch (Exception e) {

				log.error("selectDashboardDatas 예외/실패" + e.getMessage());
				return null;
			}
		}

		log.error("selectAll 실패");
		return null;
	}
	
	
/*-----------------------------------[ insert ] ------------------------------------------------------------------------------------------------------------*/
	
	
	public boolean insert(DailySalesStatsDTO dailySalesStatsDTO) {

		log.trace("insert 진입");

		int result = 0;

		try {

			result = jdbcTemplate.update(INSERT_SAMPLE_DATA, 
										dailySalesStatsDTO.getDailyTotalCalculateDate());

		} catch (Exception e) {

			log.error("insert 예외 발생");
			return false;

		}

		if (result <= 0) {

			log.error("insert 실패");
			return false;

		}

		log.trace("insert 성공");
		return true;

	}
}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

@Slf4j
class DailySalesStatsRowMapper implements RowMapper<DailySalesStatsDTO> {
	
	@Override
	public DailySalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DailySalesStatsDTO data = new DailySalesStatsDTO();
		
		log.trace("DailySalesStatsRowMapper 진입");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		data.setDailySalesStatsID(rs.getInt("DAILY_SALES_STATS_ID"));
		data.setDailyTotalCalculateDate(rs.getDate("DAILY_TOTAL_CALCULATE_DATE"));
		data.setDailyTotalGrossMargine(rs.getInt("DAILY_TOTAL_GROSS_MARGINE"));
		data.setDailyTotalNetProfit(rs.getInt("DAILY_TOTAL_NET_PROFIT"));
		
		log.debug("DAILY_SALES_STATS_ID : " + Integer.toString(rs.getInt("DAILY_SALES_STATS_ID")));
		log.debug("DAILY_TOTAL_CALCULATE_DATE : " + sdf.format(rs.getDate("DAILY_TOTAL_CALCULATE_DATE")));
		log.debug("DAILY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("DAILY_TOTAL_GROSS_MARGINE")));
		log.debug("DAILY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("DAILY_TOTAL_NET_PROFIT")));
		
		log.trace("DailySalesStatsRowMapper 완료");

		return data;
	}
}

