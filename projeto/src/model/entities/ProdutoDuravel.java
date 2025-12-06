package model.entities;

public class ProdutoDuravel extends Produto{
    private String material;

    public ProdutoDuravel(){};

    public ProdutoDuravel(Integer id, String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, String material) {
        super(id, nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        this.material = material;
    }

    public ProdutoDuravel(String nome, double precoCompra, double precoVenda, int quantidadeEstoque, String tipoProduto, String material) {
        super(nome, precoCompra, precoVenda, quantidadeEstoque, tipoProduto);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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
