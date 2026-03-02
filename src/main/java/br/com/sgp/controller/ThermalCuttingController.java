package br.com.sgp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;

import br.com.sgp.context.ThermalCuttingMemory;
import br.com.sgp.service.ThermalCuttingRefGenerator;
import br.com.sgp.view.sector.dialog.ThermalCuttingInfoDialog;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class ThermalCuttingController {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final ThermalCuttingForm form;

	public ThermalCuttingController(ThermalCuttingForm form) {
		this.form = form;
		initActions();
	}

	private void initActions() {

		// =========================
		// BOTÃO INFO
		// =========================
		form.getBtnInfo().addActionListener(e -> {

			ThermalCuttingInfoDialog dialog = new ThermalCuttingInfoDialog(SwingUtilities.getWindowAncestor(form));

			dialog.setVisible(true);

			if (dialog.isConfirmed()) {

				String linha = dialog.getLinhaMontagem();
				LocalDate dataReceb = dialog.getDataRecebimento();
				LocalDate dataProg = LocalDate.parse(dialog.getDataProgramacao(), FORMAT);

				String ref = ThermalCuttingRefGenerator.gerar(dataProg, dataReceb, linha);

				// Salva na memória
				ThermalCuttingMemory.save(linha, dataReceb, dataProg, ref);
			}
		});

		// =========================
		// BOTÃO REG
		// =========================
		form.getBtnReg().addActionListener(e -> {

			if (ThermalCuttingMemory.hasData()) {
				form.setLinhaMontagem(ThermalCuttingMemory.getLinhaMontagem());
				form.setTextDataPlano(ThermalCuttingMemory.getDataProgramacao());
				form.setTextDataRecebimento(ThermalCuttingMemory.getDataRecebimento());
				form.setProgCorte(ThermalCuttingMemory.getReferencia());
			} else {
				javax.swing.JOptionPane.showMessageDialog(form, "Nenhuma informação foi definida no INFO.", "Aviso",
						javax.swing.JOptionPane.WARNING_MESSAGE);
			}
		});

		form.getBtnClean().addActionListener(e -> {
			ThermalCuttingMemory.clear();
		});
	}
}