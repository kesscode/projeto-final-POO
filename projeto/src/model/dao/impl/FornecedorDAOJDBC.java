package model.dao.impl;

import db.Db;
import exceptions.CnpjInvalidoException;
import exceptions.DbException;
import exceptions.NomeInvalidoException;
import exceptions.TelefoneInvalidoException;
import model.dao.FornecedorDAO;
import model.entities.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAOJDBC implements FornecedorDAO {

    private Connection conn;

    public FornecedorDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void cadastrar(Fornecedor f) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("insert into fornecedores(nome, telefone, cnpj) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, f.getNome());
            st.setString(2, f.getTelefone());
            st.setString(3, f.getCnpj());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    f.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao registrar fornecedor: " + e.getMessage());
        } finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
    }

    public Fornecedor buscarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from fornecedores where id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Fornecedor f = new Fornecedor();
                try {
                    f.setId(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setCnpj(rs.getString("cnpj"));
                } catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e) {
                    throw new DbException("Dados do Fornecedor de ID " + id + " está mal-formatado: " + e.getMessage());
                }
                return f;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar fornecedor: " + e.getMessage());
        } finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
    }

    public List<Fornecedor> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Fornecedor> fornecedores = new ArrayList<>();
        int temp = 0;

        try {
            st = conn.prepareStatement("select * from fornecedores");
            rs = st.executeQuery();

            while (rs.next()) {
                try {
                    temp = rs.getInt("id");

                    Fornecedor f = new Fornecedor();
                    f.setId(temp);
                    f.setNome(rs.getString("nome"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setCnpj(rs.getString("cnpj"));

                    fornecedores.add(f);
                } catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e) {
                    System.out.println("Fornecedor de ID " + temp + " ignorado: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar fornecedores: " + e.getMessage());
        } finally {
            Db.closeStatement(st);
            Db.closeResultSet(rs);
        }
        return fornecedores;
    }

    public void atualizar(Fornecedor f) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update fornecedores set nome = ?, telefone = ? where id = ?");
            st.setString(1, f.getNome());
            st.setString(2, f.getTelefone());
            st.setInt(3, f.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar fornecedor: " + e.getMessage());
        } finally {
            Db.closeStatement(st);
        }
    }

    //Delete (se não tiver relacionado a nenhuma compra)
    public void deletarPorId(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from fornecedores where id = ?");
            st.setInt(1, id);

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new DbException("Falha ao deletar: fornecedor de ID " + id + " não encontrado.");
            }

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new DbException("Falha ao deletar: fornecedor de ID " + id + " vinculado a registros de compra.");
        } catch (SQLException e) {
            throw new DbException("Erro ao deletar fornecedor: " + e.getMessage());
        } finally {
            Db.closeStatement(st);
        }
    }

}