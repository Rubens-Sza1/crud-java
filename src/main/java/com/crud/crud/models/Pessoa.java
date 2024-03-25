package com.crud.crud.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

		private int id;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getPes_nome() {
			return pes_nome;
		}
		public void setPes_nome(String pes_nome) {
			this.pes_nome = pes_nome;
		}
		public String getPes_cpf() {
			return pes_cpf;
		}
		public void setPes_cpf(String pes_cpf) {
			this.pes_cpf = pes_cpf;
		}
		public String getPes_email() {
			return pes_email;
		}
		public void setPes_email(String pes_email) {
			this.pes_email = pes_email;
		}
		public String getPes_telefone() {
			return pes_telefone;
		}
		public void setPes_telefone(String pes_telefone) {
			this.pes_telefone = pes_telefone;
		}

		public java.util.Date getPes_dt_cadastro() {
			return pes_dt_cadastro;
		}
		public void setPes_dt_cadastro(java.util.Date pes_dt_cadastro) {
			this.pes_dt_cadastro = pes_dt_cadastro;
		}


		public LocalDateTime getPes_dt_ult_modificacao() {
			return pes_dt_ult_modificacao;
		}
		
		public void setPes_dt_ult_modificacao(LocalDateTime pes_dt_ult_modificacao) {
		    this.pes_dt_ult_modificacao = pes_dt_ult_modificacao;
		}

		public String getPes_dt_ult_modificacao_formatada() {
		    return pes_dt_ult_modificacao.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		}

		public Integer getStatus() {
			return pes_flg_atv;
		}
		public void setStatus(Integer status) {
			this.pes_flg_atv = status;
		}

		private String pes_nome;
		private String pes_cpf;
		private String pes_email;
		private String pes_telefone;
		private Date pes_dt_cadastro;
		private Integer pes_flg_atv;
		private LocalDateTime pes_dt_ult_modificacao;


			
		
}
