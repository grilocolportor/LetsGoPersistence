package com.org.model.agenda;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Agendas {
	
	private String title;
	private List<Agenda> agendas;
	
	public Agendas() {}

	public Agendas( List<Agenda> agendas) {
		super();
		
		this.agendas = agendas;
	}

	
	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	

}
