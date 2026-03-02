package br.com.sgp.context;

import java.time.LocalDate;

public class ThermalCuttingMemory {

    private static String linhaMontagem;
    private static LocalDate dataRecebimento;
    private static LocalDate dataProgramacao;
    private static String referencia;

    public static void save(String linha,
                            LocalDate dataReceb,
                            LocalDate dataProg,
                            String ref) {

        linhaMontagem = linha;
        dataRecebimento = dataReceb;
        dataProgramacao = dataProg;
        referencia = ref;
    }

    public static String getLinhaMontagem() {
        return linhaMontagem;
    }

    public static LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public static LocalDate getDataProgramacao() {
        return dataProgramacao;
    }

    public static String getReferencia() {
        return referencia;
    }

    public static boolean hasData() {
        return linhaMontagem != null;
    }

    public static void clear() {
        linhaMontagem = null;
        dataRecebimento = null;
        dataProgramacao = null;
        referencia = null;
    }
}