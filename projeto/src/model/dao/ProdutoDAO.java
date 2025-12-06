package model.dao;

import model.entities.Produto;
import java.util.List;

public interface ProdutoDAO {

    /*
     * Obedecendo ao CRUD:
     * para cada interface, definimos os métodos CRUD que cada classe DAOJDBC precisa implementar
     * */

    void cadastrar(Produto p); //Create
    Produto buscarPorId(int id); //Read
    List<Produto> buscarTodos(); //Read
    void atualizar(Produto p); //Update
    void deletarPorId(int id); //Delete (se não tiver relacionado a nenhuma transação)
}
