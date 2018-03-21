package com.org.model.agenda;

import javax.xml.bind.annotation.XmlRootElement;

import com.org.model.membro.Membro;

@XmlRootElement
public class AgendaMembro  {
	
	private String id;
	private String membro;
	private String agenda;
	private String tipo;
	private Membro oMembro;
	
	public AgendaMembro() {}
	
	public AgendaMembro(String membro, String tipo, String agenda) {
		super();
		this.membro = membro;
		this.tipo = tipo;
		this.agenda = agenda;
	}
	
	public AgendaMembro(String id, String membro, String agenda, String tipo) {
		super();
		this.id = id;
		this.membro = membro;
		this.agenda = agenda;
		this.tipo = tipo;
	}
	
	public AgendaMembro(String id, String membro, String agenda, String tipo, Membro oMembro) {
		super();
		this.id = id;
		this.membro = membro;
		this.agenda = agenda;
		this.tipo = tipo;
		this.oMembro = oMembro;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMembro() {
		return membro;
	}

	public void setMembro(String membro) {
		this.membro = membro;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Membro getoMembro() {
		return oMembro;
	}

	public void setoMembro(Membro oMembro) {
		this.oMembro = oMembro;
	}
	
	

}
