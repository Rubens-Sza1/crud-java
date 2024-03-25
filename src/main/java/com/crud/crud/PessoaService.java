package com.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.crud.crud.controllers.PessoaRepository;
import com.crud.crud.models.Pessoa;
import com.crud.crud.models.PessoaCad;


//Classe criada com o intuito de melhorar a arquitetura do codigo, entao para que não seja chamado tudo na controller, eu passo por aqui e do outro lado fica somente a execução.


@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repo;

    public boolean deletePessoa(int id) {
        Optional<Pessoa> optiona1Pessoa = repo.findById(id);
        if (optiona1Pessoa.isPresent()) {
            Pessoa pessoa = optiona1Pessoa.get();
            pessoa.setStatus(0); // Set pes_flg_atv to 1 instead of deleting
            repo.save(pessoa);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean createPessoa(PessoaCad pessoacad) {
        // Verifica se uma pessoa com o mesmo CPF já existe
        if (repo.ExisteCPF(pessoacad.getCpf())) {
            return false;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setPes_nome(pessoacad.getNome());
        pessoa.setPes_cpf(pessoacad.getCpf());
        pessoa.setPes_telefone(pessoacad.getTelefone());
        pessoa.setPes_email(pessoacad.getEmail());
        pessoa.setPes_dt_ult_modificacao(LocalDateTime.now()); // Definindo a data de modificação como o momento atual.
        pessoa.setPes_dt_cadastro(new Date()); // Definindo a data de cadastro como o momento atual.
        pessoa.setStatus(1); // Definindo o status como ativo

        repo.save(pessoa);
        return true;
    }
    
    public Pessoa updatePessoa(int id, PessoaCad pessoacad) {
        Pessoa pessoa = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        if (!pessoa.getPes_cpf().equals(pessoacad.getCpf()) && repo.ExisteCPF(pessoacad.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        pessoa.setPes_nome(pessoacad.getNome());
        pessoa.setPes_cpf(pessoacad.getCpf());
        pessoa.setPes_telefone(pessoacad.getTelefone());
        pessoa.setPes_email(pessoacad.getEmail());
        pessoa.setPes_dt_ult_modificacao(LocalDateTime.now()); // Atualiza a data de modificação

        return repo.save(pessoa);
    }
    
}