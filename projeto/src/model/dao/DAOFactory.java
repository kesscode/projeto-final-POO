package model.dao;

import db.Db;
import model.dao.impl.FornecedorDAOJDBC;
import model.dao.impl.ProdutoDAOJDBC;
import model.dao.impl.TransacaoEstoqueDAOJDBC;

/*
* Através do DAOFactory é possível fazer a criação indireta das implementações
* Essa camada garante:
* -> segurança e encapsulamento da implementação e do tipo de banco que realmente está sendo usado
* -> maior flexibilidade, pro caso de querer mudar o SGBD usado
* -> particionamento da aplicação e do sistema em si (abstração)
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
