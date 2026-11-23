package br.com.financecontrol;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import br.com.financecontrol.model.Categoria;
import br.com.financecontrol.model.TipoTransacao;
import br.com.financecontrol.model.Transacao;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;
import java.text.NumberFormat;


public class Main {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

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
                    System.out.println("O saldo é: " + formatarMoeda(saldoAtualizado));
                    break;
                case 5:
                    System.out.println("Consultar gasto por categoria");
                    Categoria categoriaConsultada = lerCategoria(scanner);
                    if (categoriaConsultada == null) {
                        break;
                    }
                    BigDecimal total = calcularTotalDespesaPorCategoria(transacoes, categoriaConsultada);
                    System.out.println("Total gasto em " + categoriaConsultada + " - " + formatarMoeda(total));
                    break;
                case 6:
                    listarDespesas(transacoes);
                    break;
                case 7:
                    listarReceitas(transacoes);
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
        System.out.println("6 - Listar só as despesas");
        System.out.println("7 - Listar só as receitas");
        System.out.println("0 - Sair\n");
        System.out.println("Escolha uma opção:");
    }

    public static void cadastrarTransacao(
            Scanner scanner, ArrayList<Transacao> transacoes, TipoTransacao tipo) {

        System.out.print("Digite a descrição: ");
        String descricao = scanner.nextLine();

        if (descricao.isBlank()) {
            System.out.println("Descrição inválida");
            return;
        }

        System.out.println("Descrição informada: " + descricao + "\n");
        System.out.print("Digite o valor: ");
        String valorDigitado = scanner.nextLine().replace(",", ".");

        try {
            BigDecimal valor = new BigDecimal(valorDigitado);
            System.out.println("Valor informado: " + formatarMoeda(valor));

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("O valor deve ser maior que 0");
            } else {
                Categoria categoria = lerCategoria(scanner);

                if (categoria == null) {
                    return;
                }

                System.out.println("Digite a data (DD/MM/AAAA) ou pressione Enter para usar a data de hoje");
                String dataDaTransacao = scanner.nextLine();
                LocalDate data;

                if (dataDaTransacao.isBlank()) {
                    data = LocalDate.now();
                } else {
                    try {
                        data = LocalDate.parse(dataDaTransacao, FORMATO_DATA);
                    } catch (DateTimeParseException e) {
                        System.out.println("Data inválida. Use o formato DD/MM/AAAA.");
                        return;
                    }
                }

                Transacao novaTransacao = new Transacao(descricao, valor, tipo, categoria, data);
                transacoes.add(novaTransacao);
                System.out.println("Transação cadastrada com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor digitado inválido");
        }
    }

    public static String formatarMoeda(BigDecimal valor) {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return formatoMoeda.format(valor);
    }

    public static void listarTransacoes(ArrayList<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada");
            return;
        }
        for (Transacao transacao : transacoes) {
            System.out.println("Descrição: " + transacao.getDescricao());
            System.out.println("Valor: " + formatarMoeda(transacao.getValor()));
            System.out.println("Tipo: " + transacao.getTipo());
            System.out.println("Categoria: " + transacao.getCategoria());
            System.out.println("Data: " + transacao.getData().format(FORMATO_DATA));
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

    public static BigDecimal calcularTotalDespesaPorCategoria(ArrayList<Transacao> transacoes, Categoria categoria) {
        BigDecimal total = BigDecimal.ZERO;

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == TipoTransacao.DESPESA && transacao.getCategoria() == categoria) {
                total = total.add(transacao.getValor());
            }

        }

        return total;

    }

    public static Categoria lerCategoria(Scanner scanner) {
        System.out.println("Opções de categoria: ");
        System.out.println("1 - " + Categoria.SALARIO);
        System.out.println("2 - " + Categoria.MORADIA);
        System.out.println("3 - " + Categoria.TRANSPORTE);
        System.out.println("4 - " + Categoria.ALIMENTACAO);
        System.out.println("5 - " + Categoria.OUTROS);
        String opcaoCategoria = scanner.nextLine();

        Categoria categoria;

        switch (opcaoCategoria) {
            case "1":
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

    public static void listarDespesas(ArrayList<Transacao> transacoes) {
        ArrayList<Transacao> despesas = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == TipoTransacao.DESPESA) {
                despesas.add(transacao);
            }
        }

        listarTransacoes(despesas);

    }

    public static void listarReceitas (ArrayList<Transacao> transacoes) {
        ArrayList<Transacao> receitas = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                receitas.add(transacao);
            }
        }

        listarTransacoes(receitas);

    }

}
