package model.entities;

import exceptions.NomeInvalidoException;
import exceptions.PrecoInvalidoException;
import exceptions.QuantidadeInvalidaException;
import exceptions.TipoInvalidoException;

public abstract class Produto {
    private Integer id;
    private String nome;
    private double precoCompra;
    private double precoVenda;
    private int quantidadeEstoque;
    private String tipoProduto;

    public Produto() {}

    public Produto(Integer id, String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto) {
        this.id = id;
        setNome(nome);
        setPrecoCompra(precoCompra);
        setPrecoVenda(precoVenda);
        setQuantidadeEstoque(quantidadeEstoque);
        setTipoProduto(tipoProduto);
    }

    //Adotei que o produto cadastrado nasce sem quantidade de estoque, portanto isso não deve ser preenchido pelo usuário
    //Vou usar esse construtor para registrar novos produtos
    public Produto(String nome, double precoCompra, double precoVenda, String tipoProduto) {
        setNome(nome);
        setPrecoCompra(precoCompra);
        setPrecoVenda(precoVenda);
        this.quantidadeEstoque = 0;
        setTipoProduto(tipoProduto);
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
        if(nome == null || nome.trim().isEmpty()){
            throw new NomeInvalidoException("Nome do produto inválido! Preencha corretamente.");
        }
        this.nome = nome.trim();
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        if (precoCompra < 0) {
            throw new PrecoInvalidoException("Preço de compra não pode ser negativo!");
        }
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        if (precoVenda < 0) {
            throw new PrecoInvalidoException("Preço de venda não pode ser negativo!");
        }
        this.precoVenda = precoVenda;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new QuantidadeInvalidaException("Quantidade do estoque não pode ser negativa!");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        if (tipoProduto == null) {
            throw new TipoInvalidoException("Tipo de produto não pode ser nulo! É obrigatório informar o tipo.");
        }
        String tipoPadrao = tipoProduto.toUpperCase().trim();

        if(!("PERECIVEL").equals(tipoPadrao) && !("DURAVEL").equals(tipoPadrao)) {
            throw new TipoInvalidoException("Tipo de produto inválido! Opções permitidas: PERECIVEL ou DURAVEL.");
        }
        this.tipoProduto = tipoPadrao;
    }

    public abstract boolean isDisponivel();

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoCompra=R$" + precoCompra +
                ", precoVenda=R$" + precoVenda +
                ", quantidadeEstoque=" + quantidadeEstoque + "unid" +
                ", tipoProduto='" + tipoProduto + '\'';
    }
}
