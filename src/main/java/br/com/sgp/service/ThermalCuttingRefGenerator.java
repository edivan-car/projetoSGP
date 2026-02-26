package br.com.sgp.service;

import java.time.LocalDate;

public final class ThermalCuttingRefGenerator {
	
	private ThermalCuttingRefGenerator() {
	}
	
	public static String gerar(LocalDate dataProg, LocalDate dataReceb, String linhaMont) {
		
		if (dataProg == null
                || dataReceb == null
                || linhaMont == null
                || linhaMont.trim().isEmpty()) {
            return "";
        }
		
		String ddmmReceb = String.format("%02d%02d",
                dataReceb.getDayOfMonth(),
                dataReceb.getMonthValue());

        String ddmmProg = String.format("%02d%02d",
                dataProg.getDayOfMonth(),
                dataProg.getMonthValue());

        String iniciais = extrairIniciais(linhaMont);

        return "P" + ddmmProg + iniciais + "/" + ddmmReceb;
		
	}
	
	 private static String extrairIniciais(String texto) {

	        StringBuilder sb = new StringBuilder();

	        for (String palavra : texto.trim().split("\\s+")) {
	            sb.append(Character.toUpperCase(palavra.charAt(0)));
	        }

	        return sb.toString();
	    }

}
