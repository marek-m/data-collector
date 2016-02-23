package org.datacollector.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

@Entity
@SequenceGenerator(name="Meal_SEQ", sequenceName="Meal_SEQ", initialValue=1, allocationSize=1)
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5190761033434267677L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Meal_SEQ")
	private Long id;

	@Column(nullable = false)
	private String name; 

	@Column(nullable = false)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date date;

	
	
	public Report(String name) {
		super();
		this.name = name;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

}
