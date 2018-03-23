package com.org.model.membro;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.org.model.agenda.Agenda;
import com.org.model.agenda.AgendaMembro;

@XmlRootElement
public class Membro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String imagePath;
	private String dataEntrada;
	private boolean ativo;
	private String numeroCelular;
	private String email;
	private String id;
	
	private List<AgendaMembro> membrosAgenda;
	private List<Agenda> agendas;
	private List<MembroContatos> membroContatos;
	private List<Membro> contatos;
	
	public Membro() {}
	
	public Membro(String id) {
		this.id = id;
	}

	public Membro(String nome, String imagePath, String dataEntrada, boolean ativo, String numeroCelular,
			String email, String id) {
		super();
		this.nome = nome;
		this.imagePath = imagePath;
		this.dataEntrada = dataEntrada;
		this.ativo = ativo;
		this.numeroCelular = numeroCelular;
		this.email = email;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<AgendaMembro> getMembrosAgenda() {
		return membrosAgenda;
	}

	public void setMembrosAgenda(List<AgendaMembro> membrosAgenda) {
		this.membrosAgenda = membrosAgenda;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public List<MembroContatos> getMembroContatos() {
		return membroContatos;
	}

	public void setMembroContatos(List<MembroContatos> membroContatos) {
		this.membroContatos = membroContatos;
	}

	public List<Membro> getContatos() {
		return contatos;
	}

	public void setContatos(List<Membro> contatos) {
		this.contatos = contatos;
	}
	
	
	
}
