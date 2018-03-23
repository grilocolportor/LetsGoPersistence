package com.org.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Eventos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5372765860243108679L;
	private String id;
	private String nome; 
	private String data;
	private String hora;
	private Localizacao localizacao;
	private String sobre;
	private String imagePath;
	private String agenda;
	
	public Eventos() {}
	
	public Eventos(String id, String agenda) {
		this.agenda = agenda;
	}
	
	public Eventos(String id) {
		this.id = id;
	}

	public Eventos(String nome, String data, String hora, Localizacao localizacao, String sobre,
			String imagePath, String agenda) {
		super();
		this.nome = nome;
		this.data = data;
		this.hora = hora;
		this.localizacao = localizacao;
		this.sobre = sobre;
		this.imagePath = imagePath;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getSobre() {
		return sobre;
	}

	public void setSobre(String sobre) {
		this.sobre = sobre;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
