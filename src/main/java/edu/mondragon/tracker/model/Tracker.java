package edu.mondragon.tracker.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRACKER")
public class Tracker implements Serializable {

	/**
	 * @brief Default generated serial
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "clicked")
	private String clicked;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "regtimeDated")
	private ZonedDateTime regtimeDated;
	
	private String regtime;
	
	public Tracker() {}

	public Tracker(String clicked, String url, String regtime) {
		super();
		this.clicked = clicked;
		this.url = url;
		ZonedDateTime time = ZonedDateTime.parse(regtime);
		regtimeDated = time;
		this.regtime = regtime;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClicked() {
		return clicked;
	}

	public void setClicked(String clicked) {
		this.clicked = clicked;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		ZonedDateTime time = ZonedDateTime.parse(regtime);
		regtimeDated = time;
		this.regtime = regtime;
	}

	
	public ZonedDateTime getRegtimeDated() {
		return regtimeDated;
	}

	public void setRegtimeDated(String regtime) {
		ZonedDateTime time = ZonedDateTime.parse(regtime);
		regtimeDated = time;
	}

	@Override
	public String toString() {
		return "Tracker [id=" + id + ", clicked=" + clicked + ", url=" + url + ", regtime=" + regtime + "]";
	}
}
