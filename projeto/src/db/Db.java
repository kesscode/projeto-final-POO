package db;

import exceptions.DbException;

import java.sql.*;

/*
* Db:
* - cria/fecha a conexão do BD
* - fecha o st e o rs
*/

public class Db {
    private static Connection conn = null;

    private static final String USUARIO = "root";
    private static final String SENHA = "1234";
    private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/estoque_petshop";

    //Estabelece a conexão com o banco (já existente ou uma nova)
    public static Connection getConnection(){
        if(conn == null){
            try{
                conn = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    //Fecha a conexão com o banco
    public static void closeConnection(){
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    //Fecha o objeto Statement
    public static void closeStatement(Statement st){
        if(st != null){
            try{
                st.close();
            }catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    //Fecha o objeto ResultSet
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    //teste para ver se conectou com o banco
    public static void main(String[] args) throws Exception {

        Connection con = getConnection();

        //Testar se a conexão é nula
        if(con != null){
            System.out.println("Conexão obtida com sucesso!");
        }
    }
}
