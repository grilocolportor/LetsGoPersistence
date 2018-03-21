package com.org.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Localizacao {
	
	private String latitude;
	private String Longitude;
	
	public Localizacao() {}

	public Localizacao(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		Longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	
	
}
