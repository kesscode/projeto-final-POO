package model.entities;

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
        this.nome = nome;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        setTipoProduto(tipoProduto);
    }

    //Adotei que o produto cadastrado nasce sem quantidade de estoque, portanto isso não deve ser preenchido pelo usuário
    public Produto(String nome, double precoCompra, double precoVenda, String tipoProduto) {
        this.nome = nome;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
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
        this.nome = nome;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        if (tipoProduto == null) {
            throw new TipoInvalidoException("Tipo de produto inválido! É obrigatório informar o tipo.");
        }
        String tipoPadrao = tipoProduto.toUpperCase().trim();

        if (!tipoPadrao.equals("PERECIVEL") && !tipoPadrao.equals("DURAVEL")) {
            throw new TipoInvalidoException("Tipo de produto inválido! Use apenas: PERECIVEL ou DURAVEL.");
        }
        this.tipoProduto = tipoPadrao;
    }

    public abstract boolean isDisponivel();

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoCompra= R$" + precoCompra +
                ", precoVenda= R$" + precoVenda +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", tipoProduto='" + tipoProduto + '\'';
    }
}
