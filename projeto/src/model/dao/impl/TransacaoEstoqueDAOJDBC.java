package model.dao.impl;

import db.Db;
import exceptions.DbException;
import model.dao.TransacaoEstoqueDAO;
import model.entities.TransacaoEstoque;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoEstoqueDAOJDBC implements TransacaoEstoqueDAO {

    private Connection conn;

    public TransacaoEstoqueDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(TransacaoEstoque tr){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            if (tr.getId() != null) {
                throw new DbException("Transação já cadastrada! ID: " + tr.getId());
            }

            //uso de transação: "desliguei" o commit automatico para garantir a atomicidade
            conn.setAutoCommit(false);
            st = conn.prepareStatement("insert into transacoes_estoque(data_hora, data_validade_lote, tipo_movimento, quantidade, id_produto, id_fornecedor)" +
                                            " values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setTimestamp(1, Timestamp.valueOf(tr.getDataHora()));

            //não tem data (caso da saída)
            if(tr.getDataHora() == null) {
                st.setNull(2, Types.TIMESTAMP);
            } else { //tem data (caso da entrada)
                st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
            }

            st.setString(3, tr.getTipoMovimento());
            st.setInt(4, tr.getQuantidade());
            st.setInt(5, tr.getIdProduto());

            if("ENTRADA".equals(tr.getTipoMovimento())) {
                st.setInt(6, tr.getIdFornecedor());
            }else if("SAIDA".equals(tr.getTipoMovimento())) {
                st.setNull(7, Types.INTEGER);
            }

            int linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    tr.setId(id);
                }
            }

            //atualizar o estoque
            atualizarEstoqueProduto(tr);

            conn.commit();
        } catch (SQLException e) {
            try{
                conn.rollback();
            }catch (SQLException e){
                throw new DbException("Erro: Falha no Rollback! " + e.getMessage());
            }
            throw new DbException("Erro ao cadastrar transação: " + e.getMessage());
        }finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);

        }
    }

    public TransacaoEstoque buscarPorId(int id){
        return new TransacaoEstoque();
    }

    public List<TransacaoEstoque> buscarTodos(){
        return new ArrayList<>();
    }

    private void atualizarEstoqueProduto(TransacaoEstoque tr) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select tipo_produto, data_validade from produtos where id = ?");

            st.setInt(1, tr.getIdProduto());
            rs = st.executeQuery();

            //verificação do tipo do produto referenciado na transação atual (conferir se batem)
            if (rs.next()) {
                String tipoProduto = rs.getString("tipo_produto");
                Date dataSql = rs.getDate("data_validade");
                LocalDate dataValid = (dataSql != null) ? dataSql.toLocalDate() : null;

                //se o produto referenciado for duravel e tem data de validade, erro!
                if (("DURAVEL").equals(tipoProduto) && tr.getDataValidadeLote() != null) {
                    throw new DbException("Erro: produto durável não pode ter data de validade.");
                }
                //se o produto referenciado for perecivel, é uma entrada e não tem data de validade, erro!
                if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento()) && tr.getDataValidadeLote() == null) {
                    throw new DbException("Erro: Entrada de produto perecivel exige data de validade.");
                }

                Db.closeStatement(st);

                //se passou, tá tudo certo. agora atualizamos o estoque/data
                if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                    st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ?, data_validade = ? where id = ?");
                    st.setInt(1, tr.getQuantidade());

                    //lógica para salvar o lote mais próximo de vencer:
                    if (dataValid == null)
                        st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                    else {
                        if (dataValid.isAfter(tr.getDataValidadeLote()))
                            st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                        else
                            st.setDate(2, Date.valueOf(dataValid));
                    }

                    st.setInt(3, tr.getIdProduto());
                } else if (("DURAVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                    st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ? where id = ?");
                    st.setInt(1, tr.getQuantidade());
                    st.setInt(2, tr.getIdProduto());
                } else if ("SAIDA".equals(tr.getTipoMovimento())) {
                    st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque - ? where id = ?");
                    st.setInt(1, tr.getQuantidade());
                    st.setInt(2, tr.getIdProduto());
                }
                st.executeUpdate();

            } else {
                throw new DbException("Produto de ID " + tr.getIdProduto() + " não encontrado para atualizar estoque.");
            }
        } finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
    }

}
