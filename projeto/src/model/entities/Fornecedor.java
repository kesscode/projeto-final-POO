package model.entities;

import exceptions.CnpjInvalidoException;

public class Fornecedor extends Pessoa{
    private String cnpj;

    public Fornecedor() {
    }

    public Fornecedor(Integer id, String nome, String telefone, String cnpj) {
        super(id, nome, telefone);
        setCnpj(cnpj);
    }

    public Fornecedor(String nome, String telefone, String cnpj) {
        super(nome, telefone);
        setCnpj(cnpj);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        //se o cnpj for nulo, lança a exceção
        if(cnpj == null){
            throw new CnpjInvalidoException("CNPJ inválido! Não pode ser nulo.");
        }

        //garante que o cnpj esteja apenas com dígitos
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");

        //se o cnpj for vazio ou não tiver o tamanho ideal, lança a exceção
        if(cnpjLimpo.length() != 14){
            throw new CnpjInvalidoException("CNPJ inválido! Quantidade de dígitos inválido.");
        }

        this.cnpj = cnpjLimpo;
    }

    @Override
    public String getDocumentoFormatado(){
        if (cnpj == null) {
            return "CNPJ não informado!";
        }

        String cnpjFormatado = cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        return cnpjFormatado;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                super.toString() +
                ", cnpj='" + getDocumentoFormatado() + '\'' +
                '}';
    }
}
