package model.dao;

import model.entities.TransacaoEstoque;
import java.util.List;

public interface TransacaoEstoqueDAO {

    /*
     * Obedecendo ao CRUD:
     * para cada interface, definimos os métodos CRUD que cada classe DAOJDBC precisa implementar
     * obs.: Para essa entidade, optei por não implementar o update e o delete para fins de segurança, já que estamos lidando com um histórico
     * */

    void cadastrar(TransacaoEstoque tr); //Create
    TransacaoEstoque buscarPorId(int id); //Read
    List<TransacaoEstoque> buscarTodos(); //Read
}
