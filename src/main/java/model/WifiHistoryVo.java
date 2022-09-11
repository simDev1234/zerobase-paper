package model;

public class WifiHistoryVo {
	private Integer idx;
	private Double LAT;
	private Double LNT;
	private String search_dttm;
	
	public WifiHistoryVo(Double lAT, Double lNT, String search_dttm) {
		super();
		LAT = lAT;
		LNT = lNT;
		this.search_dttm = search_dttm;
	}
	
	
	
	public WifiHistoryVo(Integer idx, Double lAT, Double lNT, String search_dttm) {
		super();
		this.idx = idx;
		LAT = lAT;
		LNT = lNT;
		this.search_dttm = search_dttm;
	}



	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Double getLAT() {
		return LAT;
	}
	public void setLAT(Double lAT) {
		LAT = lAT;
	}
	public Double getLNT() {
		return LNT;
	}
	public void setLNT(Double lNT) {
		LNT = lNT;
	}
	public String getSearch_dttm() {
		return search_dttm;
	}
	public void setSearch_dttm(String search_dttm) {
		this.search_dttm = search_dttm;
	}	
	
	
}


