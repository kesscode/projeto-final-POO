package model.dao.impl;

import model.dao.ProdutoDAO;
import model.entities.Produto;
import model.entities.ProdutoDuravel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOJDBC implements ProdutoDAO {

    private Connection conn;

    public ProdutoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    public void cadastrar(Produto p){
        

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
