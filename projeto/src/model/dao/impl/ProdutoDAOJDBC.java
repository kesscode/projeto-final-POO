package model.dao.impl;

import db.Db;
import exceptions.DbException;
import exceptions.TipoInvalidoException;
import model.dao.ProdutoDAO;
import model.entities.Produto;
import model.entities.ProdutoDuravel;
import model.entities.ProdutoPerecivel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOJDBC implements ProdutoDAO {

    private Connection conn;

    public ProdutoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(Produto p){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("insert into produtos(nome, preco_compra, preco_venda, tipo_produto, data_validade, material) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1,p.getNome());
            st.setDouble(2, p.getPrecoCompra());
            st.setDouble(3, p.getPrecoVenda());
            st.setString(4, p.getTipoProduto());

            //pro caso do perecivel: assumindo que o produto é recém-criado, a data_val é nula
            if(p.getTipoProduto().equals("PERECIVEL")){
                ProdutoPerecivel pp = (ProdutoPerecivel) p;
                st.setNull(5, Types.DATE);
                st.setNull(6, Types.VARCHAR);
            }else{
                ProdutoDuravel pd = (ProdutoDuravel) p;
                st.setNull(5, Types.DATE);
                st.setString(6, pd.getMaterial());
            }

            int linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    p.setId(id);
                }
            }
        }catch (SQLException e){
            throw new DbException("Erro ao cadastrar produto: " + e.getMessage());
        }finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);
        }
    }

    public Produto buscarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from produtos where id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                double p_compra = rs.getDouble("preco_compra");
                double p_venda = rs.getDouble("preco_venda");
                int qtd_estoque = rs.getInt("quantidade_estoque");
                String tipoProd = rs.getString("tipo_produto");

                try{
                if (tipoProd.equals("PERECIVEL")) {
                    java.sql.Date data = rs.getDate("data_validade");
                    LocalDate dataValid;

                    if (data != null)
                        dataValid = data.toLocalDate();
                    else
                        dataValid = null;

                    return new ProdutoPerecivel(id, nome, p_compra, p_venda, qtd_estoque, tipoProd, dataValid);
                } else {
                    String material = rs.getString("material");
                    return new ProdutoDuravel(id, nome, p_compra, p_venda, qtd_estoque, tipoProd, material);
                }
                }catch (TipoInvalidoException e){
                    throw new DbException("Dados do produto de ID " + id + " está mal-formatado: " + e.getMessage());
                }
            } return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar produto: " + e.getMessage());
        } finally {
            Db.closeResultSet(rs);
            Db.closeStatement(st);
        }
    }

    public List<Produto> buscarTodos(){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("");
        }catch (SQLException e){
            throw new DbException("Erro ao buscar fornecedor: " + e.getMessage());
        }finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
        return new ArrayList<>();
    }

    public void atualizar(Produto p){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("");
        }catch (SQLException e){
            throw new DbException("Erro ao buscar fornecedor: " + e.getMessage());
        }finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
    }


    //Delete (se não tiver relacionado a nenhuma transação)
    public void deletarPorId(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("");
        }catch (SQLException e){
            throw new DbException("Erro ao buscar fornecedor: " + e.getMessage());
        }finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
    }

}
