package model.dao.impl;

import model.dao.TransacaoEstoqueDAO;
import model.entities.TransacaoEstoque;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TransacaoEstoqueDAOJDBC implements TransacaoEstoqueDAO {

    private Connection conn;

    public TransacaoEstoqueDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(TransacaoEstoque tr){}

    public TransacaoEstoque buscarPorId(int id){
        return new TransacaoEstoque();
    }

    public List<TransacaoEstoque> buscarTodos(){
        return new ArrayList<>();
    }

    public void atualizar(TransacaoEstoque tr){}

}
