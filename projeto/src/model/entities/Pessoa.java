package model.entities;

import exceptions.NomeInvalidoException;
import exceptions.TelefoneInvalidoException;

public abstract class Pessoa {
    private Integer id;
    private String nome;
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(Integer id, String nome, String telefone) {
        this.id = id;
        setNome(nome);
        setTelefone(telefone);
    }

    public Pessoa(String nome, String telefone) {
        setNome(nome);
        setTelefone(telefone);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        //se nome for nulo ou vazio, lança uma exception
        if(nome == null || nome.trim().isEmpty()){
            throw new NomeInvalidoException("Nome inválido! Preencha corretamente.");
        }
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        //Se telefone for nulo, lança uma exception
        if(telefone == null){
            throw new TelefoneInvalidoException("Telefone inválido! Não pode ser nulo.");
        }

        //Deixa apenas numeros na string, garantindo que não tenha caracteres especiais
        String telefoneLimpo= telefone.replaceAll("[^0-9]", "");

        //Se telefone for menor ou maior que a qtd de dígitos válidos (fixo ou celular), lança uma exception
        if(telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11){
            throw new TelefoneInvalidoException("Telefone inválido! Quantidade de dígitos incorretos.");
        }

        this.telefone = telefoneLimpo;
    }

    public String getTelefoneFormatado(){
        if (telefone == null) {
            return "Telefone não informado!";
        }

        String telefoneFormatado;
        if(telefone.length() == 10) //fixo
            telefoneFormatado = telefone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        else //celular
            telefoneFormatado = telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");

        return telefoneFormatado;
    }

    public abstract String getDocumentoFormatado();

    @Override
    public String toString() {
        return "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + getTelefoneFormatado() + '\'';
    }
}
