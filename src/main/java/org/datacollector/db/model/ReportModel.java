package org.datacollector.db.model;

public class ReportModel {

	private Long id;
	private String lat;
	private String lng;
	private Integer pollutionType;
	private String pollutionTypeText;
	private String description;
	private Long timestamp;
	
	
	public ReportModel(Long id, String lat, String lng, Integer pollutionType, String pollutionTypeText,
			String description, Long timestamp) {
		super();
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.pollutionType = pollutionType;
		this.pollutionTypeText = pollutionTypeText;
		this.description = description;
		this.timestamp = timestamp;
	}
	
	public ReportModel() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public Integer getPollutionType() {
		return pollutionType;
	}
	public void setPollutionType(Integer pollutionType) {
		this.pollutionType = pollutionType;
	}
	public String getPollutionTypeText() {
		return pollutionTypeText;
	}
	public void setPollutionTypeText(String pollutionTypeText) {
		this.pollutionTypeText = pollutionTypeText;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
		
}
