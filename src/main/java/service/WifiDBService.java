package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.WifiHistoryVo;
import model.WifiVo;

public class WifiDBService{
	
	/* 
	 * DB내 모든 공공 와이파이 데이터 삭제 
	*/
	public void delete() {
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "DELETE FROM WIFI";
			
			pstmt = connection.prepareStatement(sql);
			int affected = pstmt.executeUpdate();
			
			if (affected < 0) {
				System.out.println("삭제 실패");
			} else {
				System.out.println("삭제 성공");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/* 
	 * DB내 특정 공공 와이파이 존재 여부 조회 
	 * @param  관리번호
	 * @return boolean
	*/
	
	public boolean hasWifi(String x_swifi_mgr_no) {
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		boolean result = true;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "SELECT X_SWIFI_MGR_NO FROM WIFI "
					       + "WHERE x_swifi_mgr_no = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, x_swifi_mgr_no);
			
			result = pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
		
	}
	
	
	/* 
	 * DB내 전체 공공 와이파이 갯수 조회 
	*/
	public long getSize() {
		
		long size = 0;
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "SELECT count(*) FROM WIFI";
			
			pstmt = connection.prepareStatement(sql);			
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				size = rs.getLong(1);
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return size;
	}
	
	
	/* 
	 * DB내 전체 공공 와이파이 정보 조회 
	 * @return WifiVo 목록
	*/
	public List<WifiVo> search() {
		
		List<WifiVo> wifiList = new ArrayList<>();
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "SELECT w.* FROM WIFI w";
			
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				WifiVo wifiVo = new WifiVo(
				   rs.getString("X_SWIFI_MGR_NO"),
				   rs.getString("X_SWIFI_WRDOFC"),
				   rs.getString("X_SWIFI_MAIN_NM"),
				   rs.getString("X_SWIFI_ADRES1"),
				   rs.getString("X_SWIFI_ADRES2"),
				   rs.getString("X_SWIFI_INSTL_FLOOR"),
				   rs.getString("X_SWIFI_INSTL_TY"),
				   rs.getString("X_SWIFI_INSTL_MBY"),
				   rs.getString("X_SWIFI_SVC_SE"),
				   rs.getString("X_SWIFI_CMCWR"),
				   rs.getString("X_SWIFI_CNSTC_YEAR"),
				   rs.getString("X_SWIFI_INOUT_DOOR"),
				   rs.getString("X_SWIFI_REMARS3"),
				   rs.getDouble("LAT"),
				   rs.getDouble("LNT"),
				   rs.getString("WORK_DTTM")
				);
				
				wifiList.add(wifiVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return wifiList;
	} 
	
	/* 
	 * DB내 특정 페이지의 와이파이 정보 조회 
	 * @param  위치 정보
	 * @return 위치WifiVo 목록
	*/
	
	public List<WifiVo> search(int pg_start) {
		
		List<WifiVo> wifiList = new ArrayList<>();
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "SELECT w.* FROM WIFI w limit 20 offset ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pg_start <= 1? 0 : (pg_start - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				WifiVo wifiVo = new WifiVo(
				   rs.getString("X_SWIFI_MGR_NO"),
				   rs.getString("X_SWIFI_WRDOFC"),
				   rs.getString("X_SWIFI_MAIN_NM"),
				   rs.getString("X_SWIFI_ADRES1"),
				   rs.getString("X_SWIFI_ADRES2"),
				   rs.getString("X_SWIFI_INSTL_FLOOR"),
				   rs.getString("X_SWIFI_INSTL_TY"),
				   rs.getString("X_SWIFI_INSTL_MBY"),
				   rs.getString("X_SWIFI_SVC_SE"),
				   rs.getString("X_SWIFI_CMCWR"),
				   rs.getString("X_SWIFI_CNSTC_YEAR"),
				   rs.getString("X_SWIFI_INOUT_DOOR"),
				   rs.getString("X_SWIFI_REMARS3"),
				   rs.getDouble("LAT"),
				   rs.getDouble("LNT"),
				   rs.getString("WORK_DTTM")
				);
				
				wifiList.add(wifiVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return wifiList;
	} 
	
	
	/* 
	 * DB내 전체 공공 와이파이 정보 조회
	 * @param  좌표 정보, 페이지 번호
	 * @return WifiVo 목록
	*/
	public List<WifiVo> search(double[] pos, int pg_start) {
		
		List<WifiVo> wifiList = new ArrayList<>();
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = 
					"SELECT "
					+ "round(sin(w.LAT * PI() / 180.0) * sin(? * PI() / 180.0)"
					+ "+ cos(w.LAT * PI() / 180.0) * cos(? * PI() / 180.0) "
					+ "* cos((w.LAT - ?) * PI() / 180.0), 4) as distance, "
					+ "w.* "
					+ "FROM WIFI w "
					+ "order by distance "
					+ "limit 20 offset ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setDouble(1, pos[0]);
			pstmt.setDouble(2, pos[1]);
			pstmt.setDouble(3, pos[1]);
			pstmt.setInt(4, pg_start <= 1? 0 : (pg_start - 1) * 20);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				WifiVo wifiVo = new WifiVo(
				   rs.getDouble("distance"),
				   rs.getString("X_SWIFI_MGR_NO"),
				   rs.getString("X_SWIFI_WRDOFC"),
				   rs.getString("X_SWIFI_MAIN_NM"),
				   rs.getString("X_SWIFI_ADRES1"),
				   rs.getString("X_SWIFI_ADRES2"),
				   rs.getString("X_SWIFI_INSTL_FLOOR"),
				   rs.getString("X_SWIFI_INSTL_TY"),
				   rs.getString("X_SWIFI_INSTL_MBY"),
				   rs.getString("X_SWIFI_SVC_SE"),
				   rs.getString("X_SWIFI_CMCWR"),
				   rs.getString("X_SWIFI_CNSTC_YEAR"),
				   rs.getString("X_SWIFI_INOUT_DOOR"),
				   rs.getString("X_SWIFI_REMARS3"),
				   rs.getDouble("LAT"),
				   rs.getDouble("LNT"),
				   rs.getString("WORK_DTTM")
				);
				
				wifiList.add(wifiVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return wifiList;
	} 
	
	/*
	 * DB내 공공 와이파이 정보 추가
	 *  
	 * @param 신규 공공 와이파이 정보
	 */
	
	public int insert(List<WifiVo> wifiList) {
		
		int success = 0;
		
		// 연결 정보 (파일)
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		// 드라이버 로드
		try {
			Class.forName("org.sqlite.JDBC");		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 연결
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "INSERT INTO WIFI "
					+ "    (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, "
					+ "     X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
					+ "     X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, "
					+ "     X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
					+ "     X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)"
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = connection.prepareStatement(sql);
			
			for (int i = 0; i < wifiList.size(); i++) {
				
				WifiVo vo = wifiList.get(i);
				
				String key = vo.getX_SWIFI_MGR_NO();
				
				pstmt.setString(1, key);
				pstmt.setString(2, vo.getX_SWIFI_WRDOFC());
				pstmt.setString(3, vo.getX_SWIFI_MAIN_NM());
				pstmt.setString(4, vo.getX_SWIFI_ADRES1());
				pstmt.setString(5, vo.getX_SWIFI_ADRES2());
				pstmt.setString(6, vo.getX_SWIFI_INSTL_FLOOR());
				pstmt.setString(7, vo.getX_SWIFI_INSTL_TY());
				pstmt.setString(8, vo.getX_SWIFI_INSTL_MBY());
				pstmt.setString(9, vo.getX_SWIFI_SVC_SE());
				pstmt.setString(10, vo.getX_SWIFI_CMCWR());
				pstmt.setString(11, vo.getX_SWIFI_CNSTC_YEAR());
				pstmt.setString(12, vo.getX_SWIFI_INOUT_DOOR());
				pstmt.setString(13, vo.getX_SWIFI_REMARS3());
				pstmt.setDouble(14, vo.getLAT());
				pstmt.setDouble(15, vo.getLNT());
				pstmt.setString(16, vo.getWORK_DTTM());
				
				pstmt.addBatch();
			}
			
			int[] affected = pstmt.executeBatch();
			
			for (int i = 0; i < affected.length; i++) {
				if (affected[i] == -2) {
					success += 1;
				} 
			}
			
			System.out.println(success);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	
	/*
	 * DB내 와이파이 검색 히스토리 조회
	 */
	
	public List<WifiHistoryVo> searchLog() {
		
		List<WifiHistoryVo> wifiHistoryList = new ArrayList<WifiHistoryVo>();
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "SELECT * FROM WIFI_SEARCH_HISTORY ORDER BY IDX DESC";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				WifiHistoryVo vo = new WifiHistoryVo(rs.getInt("idx"), rs.getDouble("LAT"), rs.getDouble("LNT"), rs.getString("SEARCH_DTTM"));
				wifiHistoryList.add(vo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return wifiHistoryList;
	}
	
	/*
	 * DB내 와이파이 검색 히스토리 로그 저장
	 * 
	 * @param 검색 시 입력한 X, Y 좌표
	 */ 
	
	public void saveLog(Double X, Double Y) {
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "INSERT INTO WIFI_SEARCH_HISTORY "
					+ "    (LAT, LNT) "
					   + "VALUES(?, ?)";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setDouble(1, X);
			pstmt.setDouble(2, Y);
			
			int affected = pstmt.executeUpdate();
			
			if (affected > 0) {
				System.out.println("로그 저장 성공");
			} else {
				System.out.println("로그 저장 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/*
	 * DB내 와이파이 검색 히스토리 로그 삭제
	 * 
	 * @param 로그 idx 
	 */
	
	public void deleteLog(int logIdx) {
		
		String dbUrl = "jdbc:sqlite:D://세빈//sqlite";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = DriverManager.getConnection(dbUrl);
			
			String sql = "DELETE FROM WIFI_SEARCH_HISTORY WHERE IDX = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, logIdx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
