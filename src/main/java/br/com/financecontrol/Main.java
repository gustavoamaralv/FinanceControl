package br.com.financecontrol;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== FINANCE CONTROL ===");
        System.out.println("Sistema de gestão financeira");
        System.out.println("Bem-vindo!\n");
        System.out.println("Versão: 1.0");
        System.out.println("Desenvolvido em Java\n");

        int opcao;

        do {
            System.out.println("=== MENU PRINCIPAL ===\n");
            System.out.println("1 - Cadastrar receita");
            System.out.println("2 - Cadastrar despesa");
            System.out.println("3 - Listar transações");
            System.out.println("4 - Consultar saldo");
            System.out.println("0 - Sair\n");
            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Cadastro de receita selecionada");
                    break;
                case 2:
                    System.out.println("Cadastro de despesa selecionada");
                    break;
                case 3:
                    System.out.println("Listagem de transações selecionada");
                    break;
                case 4:
                    System.out.println("Consulta de saldo selecionada");
                    break;
                case 0:
                    System.out.println("Encerrando o FinanceControl...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao !=0);

        scanner.close();

    }
}
