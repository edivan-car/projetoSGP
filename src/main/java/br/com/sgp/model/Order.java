package br.com.sgp.model;

import java.util.Date;

public class Order {

    private String pedido;
    private String projeto;
    private String linhaMontagem;
    private String programacaoMes;
    private Date dataPlano;
    private Date dataEntrega;
	private Date dataCorte;
    private String turnoCorte;
    private Date dataMontagem;
    private String turnoMontagem;
    private Date dataSoldaPescoco;
    private String turnoSoldaPescoco;
    private String progCorte;
    private String duplicada;
    private String observacao;
    
    public Order(String pedido,
                 String projeto,
                 String linhaMontagem,
                 String programacaoMes,
                 Date dataPlano,
                 Date dataEntrega,
                 Date dataCorte,
                 String turnoCorte,
                 Date dataMontagem,
                 String turnoMontagem,
                 Date dataSoldaPescoco,
                 String turnoSoldaPescoco,
                 String progCorte,
                 String duplicada,
                 String observacao) {

        this.pedido = pedido;
        this.projeto = projeto;
        this.linhaMontagem = linhaMontagem;
        this.programacaoMes = programacaoMes;
        this.dataPlano = dataPlano;
        this.dataEntrega = dataEntrega;
        this.dataCorte = dataCorte;
        this.turnoCorte = turnoCorte;
        this.dataMontagem = dataMontagem;
        this.turnoMontagem = turnoMontagem;
        this.dataSoldaPescoco = dataSoldaPescoco;
        this.turnoSoldaPescoco = turnoSoldaPescoco;
        this.progCorte = progCorte;
        this.duplicada = duplicada;
        this.observacao = observacao;
    }

    public String getPedido() { return pedido; }
    public String getProjeto() { return projeto; }
    public String getLinhaMontagem() { return linhaMontagem; }
    public String getProgramacaoMes() { return programacaoMes; }
    public Date getDataPlano() { return dataPlano; }
    public Date getDataEntrega() { return dataEntrega; }
    public Date getDataCorte() { return dataCorte; }
    public String getTurnoCorte() { return turnoCorte; }
    public Date getDataMontagem() { return dataMontagem; }
    public String getTurnoMontagem() { return turnoMontagem; }
    public Date getDataSoldaPescoco() { return dataSoldaPescoco; }
    public String getTurnoSoldaPescoco() { return turnoSoldaPescoco; }
    public String getProgCorte() { return progCorte; }
    public String getDuplicada() { return duplicada; }
    public String getObservacao() {return observacao;}
}
