package com.org.model.membro;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MembroContatos {

	private String membro;
	private List<String> contato;
	
	public  MembroContatos() {}

	public MembroContatos(String membro, List<String> contato) {
		super();
		this.membro = membro;
		this.contato = contato;
	}

	public String getMembro() {
		return membro;
	}

	public void setMembro(String membro) {
		this.membro = membro;
	}

	public List<String> getContato() {
		return contato;
	}

	public void setContato(List<String> contato) {
		this.contato = contato;
	}

	
}
