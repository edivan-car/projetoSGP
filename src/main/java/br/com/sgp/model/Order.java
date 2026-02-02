package br.com.sgp.model;

import java.util.Date;

public class Order {

    private int id;
    private String pedido;
    private String projeto;
    private String linhaMontagem;
    private String programacaoMes;
    private Date dataCorte;
    private String turnoCorte;
    private Date dataMontagem;
    private String turnoMontagem;
    private Date dataSoldaPescoco;
    private String turnoSoldaPescoco;

    public Order(int id,
                 String pedido,
                 String projeto,
                 String linhaMontagem,
                 String programacaoMes,
                 Date dataCorte,
                 String turnoCorte,
                 Date dataMontagem,
                 String turnoMontagem,
                 Date dataSoldaPescoco,
                 String turnoSoldaPescoco) {

        this.id = id;
        this.pedido = pedido;
        this.projeto = projeto;
        this.linhaMontagem = linhaMontagem;
        this.programacaoMes = programacaoMes;
        this.dataCorte = dataCorte;
        this.turnoCorte = turnoCorte;
        this.dataMontagem = dataMontagem;
        this.turnoMontagem = turnoMontagem;
        this.dataSoldaPescoco = dataSoldaPescoco;
        this.turnoSoldaPescoco = turnoSoldaPescoco;
    }

    public int getId() { return id; }
    public String getPedido() { return pedido; }
    public String getProjeto() { return projeto; }
    public String getLinhaMontagem() { return linhaMontagem; }
    public String getProgramacaoMes() { return programacaoMes; }
    public Date getDataCorte() { return dataCorte; }
    public String getTurnoCorte() { return turnoCorte; }
    public Date getDataMontagem() { return dataMontagem; }
    public String getTurnoMontagem() { return turnoMontagem; }
    public Date getDataSoldaPescoco() { return dataSoldaPescoco; }
    public String getTurnoSoldaPescoco() { return turnoSoldaPescoco; }
}
