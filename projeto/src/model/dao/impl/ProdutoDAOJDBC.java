package model.dao.impl;

import db.Db;
import exceptions.DbException;
import model.dao.ProdutoDAO;
import model.entities.Produto;
import model.entities.ProdutoDuravel;
import model.entities.ProdutoPerecivel;

import java.sql.*;
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

    public Produto buscarPorId(int id){
        return new ProdutoDuravel();
    }

    public List<Produto> buscarTodos(){
        return new ArrayList<>();
    }

    public void atualizar(Produto p){}


    //Delete (se não tiver relacionado a nenhuma transação)
    public void deletarPorId(int id){}

}
