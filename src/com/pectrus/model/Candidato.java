package com.pectrus.model;

import java.io.Serializable;

public class Candidato implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String codigo;
	private Boolean like;

	public Candidato(String nome, String codigo, Boolean like) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.like = like;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}