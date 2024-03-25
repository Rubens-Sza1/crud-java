package com.crud.crud.models;

public class PessoaCad {

	
	//A intenção aqui seria emitir uma mensagem para o usuário que os dados sao obrigarórios. Ele até chega a bloquear o campo não permitindo a inclusao.
	//Mas a mensagem  não está aparecendo.
	//Para corrigir isso eu coloquei required nos campos para o próprio html não deixar essa inclusão
	
    //@NotEmpty(message = "Nome não pode ser vazio")
	//@NotEmpty(message = "CPF não pode ser vazio")
	//@NotEmpty(message = "Telefone não pode ser vazio")
	//@NotEmpty(message = "Email não pode ser vazio")
	
	
    private String nome;    
    private String telefone;
    private String cpf;
    private String email;
    private Integer status;
    
      
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    


}