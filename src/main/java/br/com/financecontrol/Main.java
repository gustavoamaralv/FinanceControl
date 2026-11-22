package br.com.financecontrol;

import java.util.ArrayList;
import java.util.Scanner;

import br.com.financecontrol.model.Categoria;
import br.com.financecontrol.model.TipoTransacao;
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

        ArrayList<Transacao> transacoes = new ArrayList<>();

        do {
            exibirMenu();

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.println("Cadastro de receita selecionada");
                    cadastrarTransacao(scanner, transacoes, TipoTransacao.RECEITA);
                    break;
                case 2:
                    System.out.println("Cadastro de despesa selecionada");
                    cadastrarTransacao(scanner, transacoes, TipoTransacao.DESPESA);
                    break;
                case 3:
                    System.out.println("Listagem de transações selecionada");
                    listarTransacoes(transacoes);
                    break;
                case 4:
                    System.out.println("Consulta de saldo selecionada");
                    BigDecimal saldoAtualizado = calcularSaldo(transacoes);
                    System.out.println("O saldo é: R$ " + saldoAtualizado);
                    break;
                case 5:
                    System.out.println("Consultar gasto por categoria");
                    Categoria categoriaConsultada = lerCategoria(scanner);
                    if (categoriaConsultada == null) {
                        break;
                    }
                    BigDecimal total = calcularTotalDespesaPorCategoria(transacoes, categoriaConsultada);
                    System.out.println("Total gasto em " + categoriaConsultada + " - R$ "  + total);
                    break;
                case 0:
                    System.out.println("Encerrando o FinanceControl...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

        scanner.close();

    }

    public static void exibirMenu() {
        System.out.println("=== MENU PRINCIPAL ===\n");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Cadastrar despesa");
        System.out.println("3 - Listar transações");
        System.out.println("4 - Consultar saldo");
        System.out.println("5 - Consultar gasto por categoria");
        System.out.println("0 - Sair\n");
        System.out.println("Escolha uma opção:");
    }

    public static void cadastrarTransacao(Scanner scanner, ArrayList<Transacao> transacoes, TipoTransacao tipo) {
        System.out.print("Digite a descrição: ");
        String descricao = scanner.nextLine();

        if (descricao.isBlank()) {
            System.out.println("Descrição inválida");
            return;
        }
        System.out.println("Descrição informada: " + descricao + "\n");
        System.out.print("Digite o valor: ");
        String valorDigitado = scanner.nextLine();

        try {
            BigDecimal valor = new BigDecimal(valorDigitado);
            System.out.println("Valor informado: R$ " + valor);

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("O valor deve ser maior que 0");
            } else {
                System.out.println("Opções de categoria: ");
                System.out.println("1 - " + Categoria.SALARIO);
                System.out.println("2 - " + Categoria.MORADIA);
                System.out.println("3 - " + Categoria.TRANSPORTE);
                System.out.println("4 - " + Categoria.ALIMENTACAO);
                System.out.println("5 - " + Categoria.OUTROS);
                String opcaoCategoria = scanner.nextLine();

                Categoria categoria;

                switch (opcaoCategoria) {
                    case  "1":
                        categoria = Categoria.SALARIO;
                        break;
                    case "2":
                        categoria = Categoria.MORADIA;
                        break;
                    case "3":
                        categoria = Categoria.TRANSPORTE;
                        break;
                    case "4":
                        categoria = Categoria.ALIMENTACAO;
                        break;
                    case "5":
                        categoria = Categoria.OUTROS;
                        break;
                    default:
                        System.out.println("Categoria inválida");
                        return;
                }

                Transacao novaTransacao = new Transacao(descricao, valor, tipo, categoria);
                transacoes.add(novaTransacao);
                System.out.println("Transação cadastrada com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor digitado inválido");
        }
    }

    public static void listarTransacoes(ArrayList<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada");
            return;
        }
        for (Transacao transacao : transacoes) {
            System.out.println("Descrição: " + transacao.getDescricao());
            System.out.println("Valor: R$ " + transacao.getValor());
            System.out.println("Tipo: " + transacao.getTipo());
            System.out.println("Categoria: " + transacao.getCategoria());
            System.out.println("-------------------------");
        }
    }

    public static BigDecimal calcularSaldo(ArrayList<Transacao> transacoes) {
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(transacao.getValor());
            } else if (transacao.getTipo() == TipoTransacao.DESPESA) {
                saldo = saldo.subtract(transacao.getValor());
            }
        }
        return saldo;
    }

    public static BigDecimal calcularTotalDespesaPorCategoria (ArrayList <Transacao> transacoes, Categoria categoria) {
        BigDecimal total = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == TipoTransacao.DESPESA && transacao.getCategoria() == categoria) {
                total = total.add(transacao.getValor());
            }

        }

        return total;

    }

    public static Categoria lerCategoria (Scanner scanner) {
        System.out.println("Opções de categoria: ");
        System.out.println("1 - " + Categoria.SALARIO);
        System.out.println("2 - " + Categoria.MORADIA);
        System.out.println("3 - " + Categoria.TRANSPORTE);
        System.out.println("4 - " + Categoria.ALIMENTACAO);
        System.out.println("5 - " + Categoria.OUTROS);
        String opcaoCategoria = scanner.nextLine();

        Categoria categoria;

        switch (opcaoCategoria) {
            case  "1":
                categoria = Categoria.SALARIO;
                break;
            case "2":
                categoria = Categoria.MORADIA;
                break;
            case "3":
                categoria = Categoria.TRANSPORTE;
                break;
            case "4":
                categoria = Categoria.ALIMENTACAO;
                break;
            case "5":
                categoria = Categoria.OUTROS;
                break;
            default:
                System.out.println("Categoria inválida");
                return null;
        }

        return categoria;

    }

}
