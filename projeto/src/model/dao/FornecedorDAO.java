package model.dao;

import model.entities.Fornecedor;
import java.util.List;

public interface FornecedorDAO {

    /*
    * Obedecendo ao CRUD:
    * para cada interface, definimos os métodos CRUD que cada classe DAOJDBC precisa implementar
    * */

    void cadastrar(Fornecedor f); //Create
    Fornecedor buscarPorId(int id); //Read
    List<Fornecedor> buscarTodos(); //Read
    void atualizar(Fornecedor f); //Update
    void deletarPorId(int id); //Delete (se não tiver relacionado a nenhuma compra)
}
