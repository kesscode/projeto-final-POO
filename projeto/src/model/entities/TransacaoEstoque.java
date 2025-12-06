package model.entities;

import exceptions.DataInvalidaException;
import java.time.LocalDateTime;

public class TransacaoEstoque{
    private Integer id;
    private LocalDateTime dataHora;
    private String tipoMovimento;
    private int quantidade;
    private int idProduto;
    private Integer idFornecedor;

    public TransacaoEstoque() {
    }

    public TransacaoEstoque(LocalDateTime dataHora, String tipoMovimento, int quantidade, int idProduto, Integer idFornecedor) throws DataInvalidaException {
        setDataHora(dataHora);
        this.tipoMovimento = tipoMovimento;
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.idFornecedor = idFornecedor;
    }

    public TransacaoEstoque(Integer id, LocalDateTime dataHora, String tipoMovimento, int quantidade, int idProduto, Integer idFornecedor) throws DataInvalidaException {
        this.id = id;
        setDataHora(dataHora);
        this.tipoMovimento = tipoMovimento;
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.idFornecedor = idFornecedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) throws DataInvalidaException {
        //se a data for nula, lança exceção
        if(dataHora == null){
            throw new DataInvalidaException("Data inválida! A data não pode ser nula.");
        }
        //se a data for futura, lança exceção
        if(dataHora.isAfter(LocalDateTime.now())){
            throw new DataInvalidaException("Data inválida! A data não pode ser futura.");
        }
        this.dataHora = dataHora;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "TransacaoEstoque{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", tipoMovimento='" + tipoMovimento + '\'' +
                ", quantidade=" + quantidade +
                ", idProduto=" + idProduto +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
