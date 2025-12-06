package application;

import exceptions.CnpjInvalidoException;
import exceptions.DbException;
import exceptions.NomeInvalidoException;
import exceptions.TelefoneInvalidoException;
import model.dao.DAOFactory;
import model.entities.Fornecedor;

import java.util.ArrayList;
import java.util.List;

// 🅥 🅧

public class Main {
    public static void main(String[] args) {

       /* try{
            Fornecedor f = new Fornecedor("Chuchu e Pila", "(88)9 8599-759", "12.345.678/0001-50");
            DAOFactory.createFornecedorDAO().cadastrar(f);

            System.out.println("\uD83C\uDD65 ID: " + f.getId());
        }catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e){
            System.out.println("\uD83C\uDD67 ERRO na validação do fornecedor: " + e.getMessage());
        }catch (DbException e) {
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }*/


/*        try{
            Fornecedor f = DAOFactory.createFornecedorDAO().buscarPorId(3);
            if(f != null)
                System.out.println("FORNECEDOR ENCONTRADO: \n" + f.toString());
            else
                System.out.println("Não foi possível encontrar o fornecedor!");

        }catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e){
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }catch (DbException e) {
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }*/

/*        try{
            List<Fornecedor> fornecedores = new ArrayList<>();
            fornecedores = DAOFactory.createFornecedorDAO().buscarTodos();
            if(!fornecedores.isEmpty()) {
                System.out.println("-- LISTA DE FORNECEDORES --");
                for(Fornecedor f : fornecedores){
                    System.out.println(f.toString());
                }
            }else
                System.out.println("Não foi possível encontrar nenhum fornecedor!");
        }catch (DbException e) {
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }*/

        /*try{
            Fornecedor f = DAOFactory.createFornecedorDAO().buscarPorId(5);
            f.setNome("Pintinha e Bolinha");
            DAOFactory.createFornecedorDAO().atualizar(f);
            System.out.println(f.toString());
        }catch (DbException e) {
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }*/

/*        try{
            List<Fornecedor> fornecedores = new ArrayList<>();
            fornecedores = DAOFactory.createFornecedorDAO().buscarTodos();
            if(!fornecedores.isEmpty()) {
                System.out.println("-- LISTA DE FORNECEDORES --");
                for(Fornecedor f : fornecedores){
                    System.out.println(f.toString());
                }
            }
            DAOFactory.createFornecedorDAO().deletarPorId(1);
            fornecedores = DAOFactory.createFornecedorDAO().buscarTodos();
            if(!fornecedores.isEmpty()) {
                System.out.println("-- LISTA DE FORNECEDORES --");
                for (Fornecedor f : fornecedores) {
                    System.out.println(f.toString());
                }
            }
        }catch (DbException e) {
            System.out.println("\uD83C\uDD67 " + e.getMessage());
        }*/



    }
}