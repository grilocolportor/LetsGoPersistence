package com.org.model.agenda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.org.model.Eventos;

@XmlRootElement
public class Agenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
		private String nome;
		private String sobre;
		private String imagePath;
		private String dataCriacao;
		private String id;
		private List<AgendaMembro> agendaMembro;
		private List<Eventos> eventos;
		private String ativo;

	public Agenda() {}

	public Agenda(String id) {
		this.id = id;
	}

	public Agenda(String nome, String sobre, String imagePath, String dataCriacao,List<AgendaMembro>  agendaMembro,
			 List<Eventos> eventos, String ativo) {
		super();
		this.nome = nome;
		this.sobre = sobre;
		this.imagePath = imagePath;
		this.dataCriacao = dataCriacao;
		this.agendaMembro = agendaMembro;
		this.eventos = eventos;
		this.ativo = ativo;
	}

	public Agenda(String nome, String sobre, String imagePath, String dataCriacao, String id,
			List<AgendaMembro>  agendaMembro, String ativo,  List<Eventos> eventos ) {
		super();
		this.nome = nome;
		this.sobre = sobre;
		this.imagePath = imagePath;
		this.dataCriacao = dataCriacao;
		this.id = id;
		this.agendaMembro = agendaMembro;
		this.ativo = ativo;
		this.eventos = eventos;
	}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
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

		public String getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(String dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String isAtivo() {
			return ativo;
		}

		public void setAtivo(String ativo) {
			this.ativo = ativo;
		}

		public List<AgendaMembro> getAgendaMembro() {
			return agendaMembro;
		}

		public void setAgendaMembro(List<AgendaMembro> agendaMembro) {
			this.agendaMembro = agendaMembro;
		}

		public List<Eventos> getEventos() {
			return eventos;
		}

		public void setEventos(List<Eventos> eventos) {
			this.eventos = eventos;
		}

		public String getAtivo() {
			return ativo;
		}
	
}
