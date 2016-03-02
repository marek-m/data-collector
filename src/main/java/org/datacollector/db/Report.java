package org.datacollector.db;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

import org.hibernate.annotations.NaturalId;

@Entity
@SequenceGenerator(name="Report_SEQ", sequenceName="Report_SEQ", initialValue=1, allocationSize=1)
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5190761033434267677L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Report_SEQ")
	private Long id;

	@Column(nullable = false)
	private String lat;
	
	@Column(nullable = false)
	private String lng;

	@Column(nullable = false)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date;

	@Enumerated(EnumType.STRING)
    private PollutionType pollution;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = true)
	private String email;
	
	@NaturalId
	private String uid;
	
	@Column(nullable = false)
	private Boolean active = true;

	public Report() {
		super();
		this.date = new Date();
		this.uid = UUID.randomUUID().toString();
	}

	
	public Report(String lat, String lng, PollutionType pollution) {
		this();
		this.lat = lat;
		this.lng = lng;
		this.pollution = pollution;
	}
	
	public Report(String lat, String lng, PollutionType pollution, String description, String email) {
		this();
		this.lat = lat;
		this.lng = lng;
		this.pollution = pollution;
		this.description = description;
		this.email = email;
	}


	public Report(String lat, String lng, PollutionType pollution, String email) {
		this();
		this.lat = lat;
		this.lng = lng;
		this.pollution = pollution;
		this.email = email;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PollutionType getPollution() {
		return pollution;
	}

	public void setPollution(PollutionType pollution) {
		this.pollution = pollution;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
