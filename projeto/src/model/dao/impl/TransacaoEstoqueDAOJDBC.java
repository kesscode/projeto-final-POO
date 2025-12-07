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


        } catch (SQLException e) {
            throw new DbException("Erro ao cadastrar transação: " + e.getMessage());
        }
    }

    public TransacaoEstoque buscarPorId(int id){
        return new TransacaoEstoque();
    }

    public List<TransacaoEstoque> buscarTodos(){
        return new ArrayList<>();
    }

    private void atualizarEstoqueProduto(TransacaoEstoque tr, Connection conn, PreparedStatement st, ResultSet rs) throws SQLException{
        //verificação do tipo do produto referenciado na transação atual (conferir se batem)
        try {
            st = conn.prepareStatement("select produtos.tipo_produto, produtos.data_validade " +
                    "from produtos join transacoes_estoque " +
                    "on produtos.id = transacoes_estoque.id_produto " +
                    "where transacoes_estoque.id = ?");

            st.setInt(1, tr.getId());
            rs = st.executeQuery();

            String tipoProduto = rs.getString("tipo_produto");
            LocalDate dataValidade = rs.getObject("data_validade", LocalDate.class);

            //se o produto referenciado for duravel e tem data de validade, erro!
            if (("DURAVEL").equals(tipoProduto) && tr.getDataValidadeLote() != null) {
                throw new DbException("Tipo do produto de ID " + tr.getIdProduto() + " não equivale ao informado");
            }
            //se o produto referenciado for perecivel, é uma entrada e não tem data de validade, erro!
            if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento()) && tr.getDataValidadeLote() != null) {
                throw new DbException("Tipo do produto de ID " + tr.getIdProduto() + " não equivale ao informado");
            }


            //se passou, tá tudo certo. agora atualizamos o estoque/data
            if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ?, data_validade = ?, where id = ?");
                st.setInt(1, tr.getQuantidade());


                //lógica para salvar o lote mais próximo de vencer:
                if (dataValidade == null)
                    st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                else {
                    if (dataValidade.isAfter(tr.getDataValidadeLote()))
                        st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                    else
                        st.setDate(2, Date.valueOf(dataValidade));
                }

                st.setInt(3, tr.getIdProduto());
            } else if (("DURAVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ?, where id = ?");
                st.setInt(1, tr.getQuantidade());
                st.setInt(2, tr.getIdProduto());

            } else if ("SAIDA".equals(tr.getTipoMovimento())) {
                st = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque - ?, where id = ?");
                st.setInt(1, tr.getQuantidade());
                st.setInt(2, tr.getIdProduto());
            }

            st.executeUpdate();
        } catch (DbException e){
            System.out.println("Erro ao atualizar estoque: " + e.getMessage());
        }
    }
}
