package br.com.financecontrol;
import java.util.ArrayList;
import java.util.Scanner;
import br.com.financecontrol.model.Transacao;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== FINANCE CONTROL ===");
        System.out.println("Sistema de gestão financeira");
        System.out.println("Bem-vindo!\n");
        System.out.println("Versão: 1.0");
        System.out.println("Desenvolvido em Java\n");

        int opcao;

        Transacao salario = new Transacao("Salário", new BigDecimal("3500.00"));
        ArrayList<Transacao> entradas = new ArrayList<>();
        entradas.add(salario);

        System.out.println("Descrição: " + salario.getDescricao());
        System.out.println("Valor: R$ " + salario.getValor());
        System.out.println(entradas.size());

        do {
            exibirMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarReceita(scanner, entradas);
                    break;
                case 2:
                    System.out.println("Cadastro de despesa selecionada");
                    break;
                case 3:
                    listarTransacoes(entradas);
                    break;
                case 4:
                    System.out.println("Consulta de saldo selecionada");
                    BigDecimal saldo = BigDecimal.ZERO;
                    for(Transacao transacao : entradas) {
                        saldo = saldo.add(transacao.getValor());
                    }
                    System.out.println("Saldo total: R$ " + saldo);
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

    public static void exibirMenu() {
        System.out.println("=== MENU PRINCIPAL ===\n");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Cadastrar despesa");
        System.out.println("3 - Listar transações");
        System.out.println("4 - Consultar saldo");
        System.out.println("0 - Sair\n");
        System.out.println("Escolha uma opção:");
    }

    public static void cadastrarReceita(Scanner scanner, ArrayList<Transacao> entradas) {
        System.out.println("Cadastro de receita selecionada");
        scanner.nextLine();
        System.out.println("Digite a descrição da receita:");
        String descricao = scanner.nextLine();

        if (descricao.isBlank()) {
            System.out.println("Descrição inválida");
        } else {
            System.out.println("Descrição informada: " + descricao + "\n");
            System.out.println("Digite o valor da receita:");
            String valorDigitado = scanner.nextLine();

            try {
                BigDecimal valor = new BigDecimal (valorDigitado);
                System.out.println("Valor informado: R$ " + valor);

                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("O valor da receita deve ser maior que 0");
                } else {
                    Transacao novaReceita = new Transacao(descricao, valor);
                    entradas.add(novaReceita);
                    System.out.println("Receita cadastrada com sucesso!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor digitado inválido");
            }
        }
    }

    public static void listarTransacoes (ArrayList<Transacao> entradas) {
        System.out.println("Listagem de transações selecionada");
        for (Transacao transacao : entradas) {
            System.out.println("Descrição: " + transacao.getDescricao());
            System.out.println("Valor: R$ " + transacao.getValor());
            System.out.println("-------------------------");
        }
    }

}
