package br.com.financecontrol.model;

import java.math.BigDecimal;

public class Transacao {
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private Categoria categoria;

    public Transacao (String descricao, BigDecimal valor, TipoTransacao tipo, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
