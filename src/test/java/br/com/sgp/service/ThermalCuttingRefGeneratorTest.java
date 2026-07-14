package br.com.sgp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ThermalCuttingRefGeneratorTest {

	@Test
	void deveGerarReferenciaComDatasELinhaDeMontagem() {
		// Arrange
		LocalDate dataProgramacao = LocalDate.of(2026, 7, 20);
		LocalDate dataRecebimento = LocalDate.of(2026, 7, 25);
		String linhaMontagem = "Linha 1";

		// Act
		String referencia = ThermalCuttingRefGenerator.gerar(dataProgramacao, dataRecebimento, linhaMontagem);

		// Assert
		assertEquals("P2007L1/2507", referencia);
	}

	@Test
	void deveRetornarVazioQuandoDadosObrigatoriosNaoForemInformados() {
		LocalDate data = LocalDate.of(2026, 7, 20);

		assertAll(() -> assertEquals("", ThermalCuttingRefGenerator.gerar(null, data, "Linha 1")),
				() -> assertEquals("", ThermalCuttingRefGenerator.gerar(data, null, "Linha 1")),
				() -> assertEquals("", ThermalCuttingRefGenerator.gerar(data, data, null)),
				() -> assertEquals("", ThermalCuttingRefGenerator.gerar(data, data, "   ")));
	}
}