package model.dao;

import db.Db;
import model.dao.impl.FornecedorDAOJDBC;
import model.dao.impl.ProdutoDAOJDBC;
import model.dao.impl.TransacaoEstoqueDAOJDBC;

/*
*
* */

public class DAOFactory {

    public static FornecedorDAO createFornecedorDAO(){
        return new FornecedorDAOJDBC(Db.getConnection());
    }

    public static ProdutoDAO createProdutoDAO(){
        return new ProdutoDAOJDBC(Db.getConnection());
    }

    public static TransacaoEstoqueDAO createTransacaoEstoqueDAO(){
        return new TransacaoEstoqueDAOJDBC(Db.getConnection());
    }
}
