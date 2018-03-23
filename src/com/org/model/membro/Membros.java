package com.org.model.membro;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Membros {
	
	private List<Membro> membros;
	

	public Membros() {}


	public Membros(List<Membro> membros) {
		super();
		this.membros = membros;
	}


	public List<Membro> getMembros() {
		return membros;
	}


	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}
	
	
}
