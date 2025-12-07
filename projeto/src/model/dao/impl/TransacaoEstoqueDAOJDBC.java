package model.dao.impl;

import db.Db;
import exceptions.DataInvalidaException;
import exceptions.DbException;
import exceptions.TipoInvalidoException;
import model.dao.TransacaoEstoqueDAO;
import model.entities.TransacaoEstoque;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

            //uso de transaction: "desliguei" o commit automatico para garantir a atomicidade
            conn.setAutoCommit(false);

            st = conn.prepareStatement("insert into transacoes_estoque(data_hora, data_validade_lote, " +
                                            "tipo_movimento, quantidade, id_produto, id_fornecedor) " +
                                            "values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setTimestamp(1, Timestamp.valueOf(tr.getDataHora()));

            //não tem data (caso da saída)
            if(tr.getDataValidadeLote() == null) {
                st.setNull(2, Types.DATE);
            } else { //tem data (caso da entrada)
                st.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
            }

            st.setString(3, tr.getTipoMovimento());
            st.setInt(4, tr.getQuantidade());
            st.setInt(5, tr.getIdProduto());

            if (tr.getIdFornecedor() != null) {
                st.setInt(6, tr.getIdFornecedor());
            } else {
                st.setNull(6, Types.INTEGER);
            }

            int linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    tr.setId(id);
                }
            }

            atualizarEstoqueProduto(tr);
            conn.commit();

        } catch (SQLException e) {
            try{
                conn.rollback();
            }catch (SQLException e1){
                throw new DbException("Erro no rollback: " + e1.getMessage());
            }
            throw new DbException("Erro na transação: " + e.getMessage());
        }finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    public TransacaoEstoque buscarPorId(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from transacoes_estoque where id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                TransacaoEstoque transacao = new TransacaoEstoque();
                try {
                    transacao.setId(rs.getInt("id"));

                    java.sql.Timestamp dataSql = rs.getTimestamp("data_hora");
                    LocalDateTime dataHora = (dataSql != null) ? dataSql.toLocalDateTime() : null;

                    java.sql.Date dataValSql = rs.getDate("data_validade_lote");
                    LocalDate dataValid = (dataValSql != null) ? dataValSql.toLocalDate() : null;

                    transacao.setDataHora(dataHora);
                    transacao.setDataValidadeLote(dataValid);

                    transacao.setTipoMovimento(rs.getString("tipo_movimento"));
                    transacao.setQuantidade(rs.getInt("quantidade"));
                    transacao.setIdProduto(rs.getInt("id_produto"));

                    int idForn = rs.getInt("id_fornecedor");
                    if (rs.wasNull()) {
                        transacao.setIdFornecedor(null);
                    } else {
                        transacao.setIdFornecedor(idForn);
                    }

                    return transacao;

                } catch (DataInvalidaException | TipoInvalidoException e) {
                    throw new DbException("Dados da transação de ID " + id + " está mal-formatado: " + e.getMessage());
                }
            }
            return null;

        }catch (SQLException e){
            throw new DbException("Erro ao buscar transação: " + e.getMessage());
        } finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);
        }
    }

    public List<TransacaoEstoque> buscarTodos(){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from transacoes_estoque");
            rs = st.executeQuery();
            List<TransacaoEstoque> transacoes = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                try {
                    java.sql.Timestamp dataSql = rs.getTimestamp("data_hora");
                    LocalDateTime dataHora = (dataSql != null) ? dataSql.toLocalDateTime() : null;

                    java.sql.Date dataValSql = rs.getDate("data_validade_lote");
                    LocalDate dataValid = (dataValSql != null) ? dataValSql.toLocalDate() : null;

                    String tipoMov = rs.getString("tipo_movimento");
                    int qtd = rs.getInt("quantidade");
                    int id_prod = rs.getInt("id_produto");

                    int idForn = rs.getInt("id_fornecedor");
                    if (rs.wasNull()) {
                        transacoes.add(new TransacaoEstoque(id, dataHora, dataValid, tipoMov, qtd, id_prod, null));
                    } else {
                        transacoes.add(new TransacaoEstoque(id, dataHora, dataValid, tipoMov, qtd, id_prod, idForn));
                    }
                } catch (DataInvalidaException | TipoInvalidoException e) {
                    System.out.println("Dados do produto de ID " + id + " está mal-formatado: " + e.getMessage());
                }
            }
            return transacoes;
        }catch (SQLException e){
            throw new DbException("Erro ao buscar transações: " + e.getMessage());
        } finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);
        }
    }

    private void atualizarEstoqueProduto(TransacaoEstoque tr) throws SQLException {
        PreparedStatement st = null;
        PreparedStatement stUpdate = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select quantidade_estoque, tipo_produto, data_validade from produtos where id = ?");
            st.setInt(1, tr.getIdProduto());
            rs = st.executeQuery();

            //verificação do tipo do produto referenciado na transação atual (conferir se batem)
            if (rs.next()) {
                String tipoProduto = rs.getString("tipo_produto");
                Date dataSql = rs.getDate("data_validade");
                LocalDate dataValid = (dataSql != null) ? dataSql.toLocalDate() : null;
                int estoqueAtual = rs.getInt("quantidade_estoque");


                //se o produto referenciado for duravel e tem data de validade, erro!
                if (("DURAVEL").equals(tipoProduto) && tr.getDataValidadeLote() != null) {
                    throw new DbException("Erro: produto durável não pode ter data de validade.");
                }

                //se o produto referenciado for perecivel, é uma entrada e não tem data de validade, erro!
                if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento()) && tr.getDataValidadeLote() == null) {
                    throw new DbException("Erro: Entrada de produto perecivel exige data de validade.");
                }

                if ("SAIDA".equals(tr.getTipoMovimento()) && estoqueAtual < tr.getQuantidade()) {
                    throw new DbException("Erro: Estoque do produto insuficiente para venda!");
                }


                //se passou, tá tudo certo. agora atualizamos o estoque/data
                if (("PERECIVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                    stUpdate = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ?, data_validade = ? where id = ?");
                    stUpdate.setInt(1, tr.getQuantidade());

                    //lógica para salvar o lote mais próximo de vencer:
                    if (dataValid == null)
                        stUpdate.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                    else {
                        if (dataValid.isAfter(tr.getDataValidadeLote()))
                            stUpdate.setDate(2, Date.valueOf(tr.getDataValidadeLote()));
                        else
                            stUpdate.setDate(2, Date.valueOf(dataValid));
                    }
                    stUpdate.setInt(3, tr.getIdProduto());

                } else if (("DURAVEL").equals(tipoProduto) && "ENTRADA".equals(tr.getTipoMovimento())) {
                    stUpdate = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque + ? where id = ?");
                    stUpdate.setInt(1, tr.getQuantidade());
                    stUpdate.setInt(2, tr.getIdProduto());

                } else if ("SAIDA".equals(tr.getTipoMovimento())) {
                    stUpdate = conn.prepareStatement("update produtos set quantidade_estoque = quantidade_estoque - ? where id = ?");
                    stUpdate.setInt(1, tr.getQuantidade());
                    stUpdate.setInt(2, tr.getIdProduto());
                } else {
                    throw new DbException("Erro: Tipo de transação inválida.");
                }

                int linhasAfetadas = stUpdate.executeUpdate();
                if (linhasAfetadas == 0) {
                    throw new DbException("Erro: Não foi possível atualizar o produto.");
                }
            } else {
                throw new DbException("Erro: Produto de ID " + tr.getIdProduto() + " não encontrado.");
            }
        } finally {
            Db.closeResultSet(rs);
            Db.closeStatement(stUpdate);
            Db.closeStatement(st);
        }
    }

}

