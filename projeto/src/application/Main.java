package application;

import exceptions.*;
import model.dao.DAOFactory;
import model.entities.Fornecedor;
import model.entities.Produto;
import model.entities.ProdutoDuravel;
import model.entities.ProdutoPerecivel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int opcao = 0;
        Scanner sc = new Scanner(System.in);

        do{
            menuPrincipal();
            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        gerenciarFornecedores(sc);
                        break;
                    case 2:
                        gerenciarProdutos(sc);
                        break;
                    case 3:
                        gerenciarTransacoes(sc);
                        break;
                    case 0:
                        System.out.println("\n🔒 Encerrando... Volte sempre ao Pet Stok! 🐾");
                        break;
                    default:
                        System.out.println("\n⚠ Opção inválida! Digite um número de 0 a 5.");
                        break;

                }
            }catch (InputMismatchException e){
                System.out.println("❌ Erro: Digite apenas números!");
                sc.nextLine();
            }catch (Exception e){
                System.out.println("❌ Erro Inesperado: " + e.getMessage());
            }

        }while(opcao != 0);

        sc.close();
    }

//GERENCIAMENTO DE CADA ENTIDADE

    private static void gerenciarFornecedores(Scanner sc){
        int opcao = 0;
        Integer id;
        String nome, telefone, cnpj;

        do{
            menuFornecedores();
            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\n---ESCOLHA: Cadastrar Novo Fornecedor---");
                        System.out.println("\nINFORME OS DADOS NECESSÁRIOS ABAIXO ↓");

                        try{
                            System.out.println("Nome: ");
                            nome = sc.nextLine();

                            System.out.println("Telefone: ");
                            telefone = sc.nextLine();

                            System.out.println("CNPJ (apenas números): ");
                            cnpj = sc.nextLine();

                            Fornecedor f = new Fornecedor(nome,telefone,cnpj);
                            DAOFactory.createFornecedorDAO().cadastrar(f);

                            System.out.println("✅ Fornecedor cadastrado com sucesso!");
                            System.out.println("Dados registrados: " + f.toString());

                        }catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e){
                            System.out.println("❌ Erro de Validação: " + e.getMessage());
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("\n---ESCOLHA: Consultar Fornecedor---");
                        System.out.println("\nINFORME O DADO NECESSÁRIO ABAIXO ↓");
                        try{
                            System.out.println("ID do Fornecedor: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            Fornecedor f = DAOFactory.createFornecedorDAO().buscarPorId(id);
                            if(f != null){
                                System.out.println("✅ Fornecedor encontrado!");
                                System.out.println(f.toString());
                            } else {
                                System.out.println("❌ Fornecedor de ID " + id + " não encontrado.");
                            }
                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("\n---ESCOLHA: Consultar Lista de Fornecedores---");
                        System.out.println("\nCONSULTANDO... ↓");
                        try{
                            List<Fornecedor> fornecedores = DAOFactory.createFornecedorDAO().buscarTodos();
                            if(!fornecedores.isEmpty()){
                                System.out.println("Lista de Fornecedores:\n");
                                for (Fornecedor f : fornecedores){
                                    System.out.println(f.toString());
                                }
                            } else {
                                System.out.println("❌ Nenhum fornecedor cadastrado no momento.");
                            }
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n---ESCOLHA: Editar Dados de um Fornecedor---");
                        System.out.println("\nINFORME O ID DO FORNECEDOR QUE DESEJA MODIFICAR ABAIXO ↓");
                        try{
                            System.out.println("ID: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            Fornecedor f = DAOFactory.createFornecedorDAO().buscarPorId(id);
                            if(f != null){
                                System.out.println("\nINFORME OS DADOS QUE DESEJA MODIFICAR ABAIXO ↓");
                                System.out.println("Nome: ");
                                nome = sc.nextLine();

                                System.out.println("Telefone: ");
                                telefone = sc.nextLine();

                                f.setNome(nome);
                                f.setTelefone(telefone);

                                DAOFactory.createFornecedorDAO().atualizar(f);
                                System.out.println("✅ Dados do Fornecedor de ID " + f.getId() + " atualizados com sucesso!");
                            } else {
                                System.out.println("❌ Fornecedor de ID " + id + " não encontrado.");
                            }

                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        }catch (NomeInvalidoException | TelefoneInvalidoException e){
                            System.out.println("❌ Erro de Validação: " + e.getMessage());
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("\n---ESCOLHA: Excluir Fornecedor---");
                        System.out.println("\nINFORME O DADO NECESSÁRIO ABAIXO ↓");

                        try{
                            System.out.println("ID: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            DAOFactory.createFornecedorDAO().deletarPorId(id);
                            System.out.println("✅ Fornecedor deletado com sucesso!");
                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;
                    //Voltar ao Menu Principal
                    case 0:
                        System.out.println("\n↩ Retornando ao Menu Principal... 🐾");
                        break;
                    default:
                        System.out.println("\n⚠ Opção inválida! Digite um número de 0 a 5.");
                }
            }catch (Exception e){
                System.out.println("❌ Erro: Digite apenas números!");
                sc.nextLine();
                opcao = -1;
            }
        }while(opcao != 0);
    }

    private static void gerenciarProdutos(Scanner sc){
        int opcao = 0;

        do{
            menuProdutos();
            try {
                opcao = sc.nextInt();
                sc.nextLine();

                Integer id;
                String nome, tipoProduto, material;
                double precoCompra, precoVenda;
                int quantidadeEstoque;
                LocalDate dataVal;

                //String nome, double precoCompra, double precoVenda, String tipoProduto
                switch (opcao) {
                    case 1:
                        System.out.println("\n---ESCOLHA: Cadastrar Novo Produto---");
                        System.out.println("\nINFORME OS DADOS NECESSÁRIOS ABAIXO ↓");

                        try{
                            System.out.println("Nome: ");
                            nome = sc.nextLine();

                            System.out.println("Preço de Compra: ");
                            precoCompra = sc.nextDouble();
                            sc.nextLine();

                            System.out.println("Preço de Venda: ");
                            precoVenda = sc.nextDouble();
                            sc.nextLine();

                            System.out.println("Tipo DURAVEL ou PERECIVEL (escreva sem acentos): ");
                            tipoProduto = sc.nextLine().toUpperCase().trim();

                            Produto p = null;
                            if("PERECIVEL".equals(tipoProduto)){
                                //perecivel nasce sem data de validade
                                p = new ProdutoPerecivel(nome,precoCompra, precoVenda, tipoProduto);
                            } else if ("DURAVEL".equals(tipoProduto)){
                                System.out.println("Material: ");
                                material = sc.nextLine();
                                p = new ProdutoDuravel(nome,precoCompra, precoVenda, tipoProduto, material);
                            } else {
                                System.out.println("❌ Tipo inválido! Cadastro cancelado.");
                                break;
                            }
                            DAOFactory.createProdutoDAO().cadastrar(p);

                            System.out.println("✅ Produto cadastrado com sucesso!");
                            System.out.println("Dados registrados: " + p.toString());

                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        } catch (NomeInvalidoException | TelefoneInvalidoException | CnpjInvalidoException e){
                            System.out.println("❌ Erro de Validação: " + e.getMessage());
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;
                    //Consultar Produto
                    case 2:
                        System.out.println("\n---ESCOLHA: Consultar Produto---");
                        System.out.println("\nINFORME O DADO NECESSÁRIO ABAIXO ↓");

                        try{
                            System.out.println("ID do Produto: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            Produto p = DAOFactory.createProdutoDAO().buscarPorId(id);
                            if(p != null) {
                                System.out.println("✅ Produto encontrado!");
                                System.out.println(p.toString());
                            }else{
                                System.out.println("❌ Produto de ID " + id + " não encontrado.");
                            }

                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;
                    //Consultar Lista de Produtos
                    case 3:
                        System.out.println("\n---ESCOLHA: Consultar Lista de Produtos---");

                        try {
                            List<Produto> produtos = DAOFactory.createProdutoDAO().buscarTodos();

                            if (!produtos.isEmpty()) {
                                System.out.println("Lista de Fornecedores:\n");
                                for (Produto p : produtos) {
                                    System.out.println(p.toString());
                                }
                            } else {
                                System.out.println("❌ Nenhum fornecedor cadastrado no momento.");
                            }
                        } catch (DbException e) {
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n---ESCOLHA: Editar Dados de um Produto---");
                        System.out.println("\nINFORME O ID DO PRODUTO QUE DESEJA MODIFICAR ABAIXO ↓");
                        try{
                            System.out.println("ID: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            Produto p = DAOFactory.createProdutoDAO().buscarPorId(id);
                            if(p != null){
                                System.out.println("\nINFORME OS DADOS QUE DESEJA MODIFICAR ABAIXO ↓");
                                System.out.println("Nome: ");
                                nome = sc.nextLine();

                                System.out.println("Preço de Compra: ");
                                precoCompra = sc.nextDouble();

                                System.out.println("Preço de Venda: ");
                                precoVenda = sc.nextDouble();
                                sc.nextLine();

                                p.setNome(nome);
                                p.setPrecoCompra(precoCompra);
                                p.setPrecoVenda(precoVenda);

                                if (p instanceof ProdutoPerecivel pp){
                                    System.out.println("Data de validade (AAAA-MM-DD): ");
                                    String dataTemp = sc.nextLine();

                                    dataVal = LocalDate.parse(dataTemp);
                                    if (dataVal.isBefore(LocalDate.now())) {
                                        throw new DataInvalidaException("Erro: Data não pode estar vencida!");
                                    }
                                    pp.setDataValidade(dataVal);
                                } else if (p instanceof ProdutoDuravel pd) {
                                    System.out.println("Material: ");
                                    material = sc.nextLine();
                                    pd.setMaterial(material);
                                }
                                DAOFactory.createProdutoDAO().atualizar(p);
                                System.out.println("✅ Dados do Produto de ID " + p.getId() + " atualizados com sucesso!");
                            } else {
                                System.out.println("❌ Produto de ID " + id + " não encontrado.");
                            }
                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ Erro: Formato de data inválido!");
                        }
                        catch (NomeInvalidoException | PrecoInvalidoException | QuantidadeInvalidaException | TipoInvalidoException | DataInvalidaException e){
                            System.out.println("❌ Erro de Validação: " + e.getMessage());
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("\n---ESCOLHA: Excluir Produto---");
                        System.out.println("\nINFORME O DADO NECESSÁRIO ABAIXO ↓");

                        try{
                            System.out.println("ID: ");
                            id = sc.nextInt();
                            sc.nextLine();

                            DAOFactory.createProdutoDAO().deletarPorId(id);
                            System.out.println("✅ Produto deletado com sucesso!");
                        }catch (InputMismatchException e){
                            System.out.println("❌ Erro: Digite apenas números!");
                            sc.nextLine();
                        }catch (DbException e){
                            System.out.println("❌ Erro de Banco de Dados: " + e.getMessage());
                        }catch (Exception e){
                            System.out.println("❌ Erro Inesperado: " + e.getMessage());
                        }
                        break;
                    case 0:
                        System.out.println("\n↩ Retornando ao Menu Principal... 🐾");
                    default:

                }
            }catch (Exception e){
                System.out.println("❌ Erro! Digite apenas números!");
                sc.nextLine();
            }
        }while(opcao != 0);
    }

    private static void gerenciarTransacoes(Scanner sc){
        int opcao = 0;

        do{
            menuTransacoes();
            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:

                        break;

                    case 2:

                        break;

                    case 3:

                        break;

                    case 0:
                        System.out.println("\n↩ Retornando ao Menu Principal... 🐾");
                    default:
                }
            }catch (Exception e){
                System.out.println("❌ Erro! Digite apenas números!");
                sc.nextLine();
            }
        }while(opcao != 0);
    }


    //MENUS INTERATIVOS
    public static void menuPrincipal(){
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.println("│    🐾 CONTROLE DE ESTOQUE - PET STOCK 🐾         │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ [1] Gerenciar Fornecedores                   🤝  │");
        System.out.println("│ [2] Gerenciar Produtos                       📦  │");
        System.out.println("│ [3] Gerenciar Transações do Estoque          🔄  │");
        System.out.println("│ [0] Sair                                     🚪  │");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.print("→ Digite sua opção: ");
    }

    public static void menuFornecedores() {
        System.out.println("\n┌───────────────────────────────────────────────────┐");
        System.out.println("│ 🐾 GERENCIAMENTO DE FORNECEDORES - PET STOCK 🐾   │");
        System.out.println("├───────────────────────────────────────────────────┤");
        System.out.println("│ [1] Cadastrar Novo Fornecedor                🆕   │");
        System.out.println("│ [2] Consultar Fornecedor                     🔎   │");
        System.out.println("│ [3] Consultar Lista de Fornecedores          📋   │");
        System.out.println("│ [4] Editar Dados de um Fornecedor            ✏️   │");
        System.out.println("│ [5] Excluir Fornecedor                       ❌    │");
        System.out.println("│ [0] Voltar ao Menu Principal                 👋   │");
        System.out.println("└───────────────────────────────────────────────────┘");
        System.out.print("→ Digite sua opção: ");
    }

    public static void menuProdutos() {
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.println("│   🐾 GERENCIAMENTO DE PRODUTOS - PET STOCK 🐾    │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ [1] Cadastrar Novo Produto                   🆕  │");
        System.out.println("│ [2] Consultar Produto                        🔎  │");
        System.out.println("│ [3] Consultar Lista de Produtos              📋  │");
        System.out.println("│ [4] Editar Dados de um Produto               ✏️  │");
        System.out.println("│ [5] Excluir Produto                          ❌   │");
        System.out.println("│ [0] Voltar ao Menu Principal                 👋  │");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.print("→ Digite sua opção: ");
    }

    public static void menuTransacoes() {
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.println("│  🐾 GERENCIAMENTO DE TRANSAÇÕES - PET STOCK 🐾   │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ [1] Cadastrar Nova Transação                 🆕  │");
        System.out.println("│ [2] Consultar Transação                      🔎  │");
        System.out.println("│ [3] Consultar Lista de Transações            📋  │");
        System.out.println("│ [0] Voltar ao Menu Principal                 👋  │");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.print("→ Digite sua opção: ");
    }

}


