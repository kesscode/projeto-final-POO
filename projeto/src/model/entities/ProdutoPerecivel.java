package model.entities;

import java.time.LocalDate;

public class ProdutoPerecivel extends Produto{
    private LocalDate dataValidade;

    public ProdutoPerecivel(){}

    public ProdutoPerecivel(Integer id, String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, LocalDate dataValidade){
        super(id, nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        setDataValidade(dataValidade);
    }

    public ProdutoPerecivel(String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, LocalDate dataValidade) {
        super(nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        setDataValidade(dataValidade);
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public boolean isDisponivel() {
        //o produto foi recém-criado, sem estoque
        if (this.dataValidade == null) {
            return false;
        }
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