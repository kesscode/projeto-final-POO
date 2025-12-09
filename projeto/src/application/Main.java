package application;

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
                    default:

                }
            }catch (Exception e){
                System.out.println("❌ Erro! Digite apenas números!");
                sc.nextLine();
            }

        }while(opcao != 0);

    }

//GERENCIAMENTO DE CADA ENTIDADE

    private static void gerenciarFornecedores(Scanner sc){
        int opcao = 0;

        do{
            menuFornecedores();
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

                    case 4:

                        break;

                    case 5:

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

    private static void gerenciarProdutos(Scanner sc){
        int opcao = 0;

        do{
            menuProdutos();
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

                    case 4:

                        break;

                    case 5:

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


