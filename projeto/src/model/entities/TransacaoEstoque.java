package model.entities;

import exceptions.DataInvalidaException;
import exceptions.TipoInvalidoException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransacaoEstoque{
    private Integer id;
    private LocalDateTime dataHora;
    private LocalDate dataValidadeLote;
    private String tipoMovimento;
    private int quantidade;
    private int idProduto;
    private Integer idFornecedor;

    public TransacaoEstoque() {
    }

    public TransacaoEstoque(Integer id, LocalDateTime dataHora, LocalDate dataValidadeLote, String tipoMovimento, int quantidade, int idProduto, Integer idFornecedor) {
        this.id = id;
        setDataHora(dataHora);
        setDataValidadeLote(dataValidadeLote);
        setTipoMovimento(tipoMovimento);
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.idFornecedor = idFornecedor;
    }

    public TransacaoEstoque(LocalDateTime dataHora, LocalDate dataValidadeLote, String tipoMovimento, int quantidade, int idProduto, Integer idFornecedor) {
        setDataHora(dataHora);
        setTipoMovimento(tipoMovimento);
        setDataValidadeLote(dataValidadeLote);
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

    public void setDataHora(LocalDateTime dataHora) {
        //se a data for nula, lança exceção
        if(dataHora == null){
            throw new DataInvalidaException("Data inválida! A data da transação não pode ser nula.");
        }
        //se a data for futura, lança exceção
        if(dataHora.isAfter(LocalDateTime.now())){
            throw new DataInvalidaException("Data inválida! A data da transação não pode ser futura.");
        }
        this.dataHora = dataHora;
    }

    public LocalDate getDataValidadeLote() {
        return dataValidadeLote;
    }

    public void setDataValidadeLote(LocalDate dataValidadeLote) {
        if (tipoMovimento != null && tipoMovimento.equals("ENTRADA") && dataValidadeLote != null && dataValidadeLote.isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Data inválida! Não é possível dar entrada em um lote já vencido!");
        }
        this.dataValidadeLote = dataValidadeLote;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        if (tipoMovimento == null) {
            throw new TipoInvalidoException("Tipo de movimento inválido! É obrigatório informar o tipo.");
        }

        String tipoPadrao = tipoMovimento.toUpperCase().trim();
        if (!tipoPadrao.equals("ENTRADA") && !tipoPadrao.equals("SAIDA")) {
            throw new TipoInvalidoException("Tipo de movimento inválido! Use: ENTRADA ou SAIDA.");
        }

        if (tipoMovimento.equals("SAIDA") && dataValidadeLote != null) {
            throw new TipoInvalidoException("Saída não pode ter data de validade!");
        }

        this.tipoMovimento = tipoPadrao;
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
                ", dataValidadeLote=" + dataValidadeLote +
                ", tipoMovimento='" + tipoMovimento + '\'' +
                ", quantidade=" + quantidade +
                ", idProduto=" + idProduto +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
