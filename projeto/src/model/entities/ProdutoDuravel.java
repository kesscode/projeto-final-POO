package model.entities;

import exceptions.TipoInvalidoException;


public class ProdutoDuravel extends Produto{
    private String material;

    public ProdutoDuravel(){}

    public ProdutoDuravel(Integer id, String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, String material) {
        super(id, nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        setMaterial(material);
    }

    public ProdutoDuravel(String nome, double precoCompra, double precoVenda, String tipoProduto, String material) {
        super(nome, precoCompra, precoVenda, tipoProduto);
        setMaterial(material);
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty()) {
            throw new TipoInvalidoException("Material do produto está vazio! É obrigatório informar o material.");
        }
        this.material = material.trim();
    }

    @Override
    public boolean isDisponivel() {
        return getQuantidadeEstoque() > 0;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", material='" + material + '\'' +
                '}';
    }
}
