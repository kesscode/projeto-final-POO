package model.entities;

import exceptions.DataInvalidaException;
import java.time.LocalDate;

public class ProdutoPerecivel extends Produto{
    private LocalDate dataValidade;

    public ProdutoPerecivel(){};

    public ProdutoPerecivel(Integer id, String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, LocalDate dataValidade) throws DataInvalidaException {
        super(id, nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        setDataValidade(dataValidade);
    }

    public ProdutoPerecivel(String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, LocalDate dataValidade) throws DataInvalidaException {
        super(nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        setDataValidade(dataValidade);
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) throws DataInvalidaException {
        //se a data for nula, lança exceção
        if(dataValidade == null){
            throw new DataInvalidaException("Data inválida! A data não pode ser nula.");
        }
        this.dataValidade = dataValidade;
    }

    @Override
    public boolean isDisponivel() {
        //se o estoque for maior que zero e a data de validade estiver no prazo, está disponível
        return (getQuantidadeEstoque() > 0 && !dataValidade.isBefore(LocalDate.now()));
    }

    @Override
    public String toString() {
        return super.toString() +
                ", dataValidade=" + dataValidade +
                '}';
    }
}