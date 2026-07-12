package br.com.sgp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;

import br.com.sgp.context.ThermalCuttingMemory;
import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.service.ThermalCuttingRefGenerator;
import br.com.sgp.view.sector.ThermalCuttingView;
import br.com.sgp.view.sector.dialog.ThermalCuttingInfoDialog;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class ThermalCuttingController {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final ThermalCuttingForm form;
	private final ThermalCuttingView sectorView;
	private final OrderDAO dao = new OrderDAO();

	public ThermalCuttingController(ThermalCuttingForm form, ThermalCuttingView sectorView) {
		this.form = form;
		this.sectorView = sectorView;
		initActions();
	}

	private void initActions() {

		// =========================
		// BOTÃO INFO - Lógica condicional por memória
		// =========================
		form.getBtnInfo().addActionListener(e -> {
	        if (ThermalCuttingMemory.hasData()) {
	            fillFromMemory();
	        } else {
	            openInfoDialog();
	        }
	    });

	    // BOTÃO LIMPAR INFO  //
	    form.getBtnCleanMemory().addActionListener(e -> {
	        ThermalCuttingMemory.clear();
	    });

		// =========================
		// BOTÃO REGISTRAR
		// =========================
		form.getBtnReg().addActionListener(e -> {
			save();
		});

		form.getBtnClean().addActionListener(e -> {
//			form.clearForm();
			sectorView.clearAllFields()
;			sectorView.clearCard();
		    sectorView.setFormsEditable(true);
		    sectorView.setRegisterEnabled(true);
		});
	}
	
	private void openInfoDialog() {
	    ThermalCuttingInfoDialog dialog =
	        new ThermalCuttingInfoDialog(SwingUtilities.getWindowAncestor(form));
	    dialog.setVisible(true);

	    if (dialog.isConfirmed()) {
	        String linha = dialog.getLinhaMontagem();
	        LocalDate dataReceb = dialog.getDataRecebimento();
	        LocalDate dataProg = LocalDate.parse(dialog.getDataProgramacao(), FORMAT);

	        String ref = ThermalCuttingRefGenerator.gerar(dataProg, dataReceb, linha);

	        ThermalCuttingMemory.save(linha, dataReceb, dataProg, ref);
	        fillFromMemory();
	    }
	}

	private void fillFromMemory() {
		if (!ThermalCuttingMemory.hasData()) {
			javax.swing.JOptionPane.showMessageDialog(form, "Nenhuma informação foi definida no Info.", "Aviso",
					javax.swing.JOptionPane.WARNING_MESSAGE);
			return;
		}

		form.setLinhaMontagem(ThermalCuttingMemory.getLinhaMontagem());
		form.setTextDataPlano(ThermalCuttingMemory.getDataProgramacao());
		form.setTextDataRecebimento(ThermalCuttingMemory.getDataRecebimento());
		form.setProgCorte(ThermalCuttingMemory.getReferencia());
		form.getBtnReg().requestFocusInWindow();
	}

	private void save() {
		String orderNumber = sectorView.getOrderNumber();

		if (orderNumber == null || orderNumber.trim().isEmpty()) {
			sectorView.showMessage("Nenhum pedido selecionado.");
			return;
		}

		String linha = form.getTxtLinhaMontagem().getText().trim();
		String dataPlano = form.getTxtDataPlano().getText().trim();
		String dataEntrega = form.getTxtDataRecebimento().getText().trim();
		String progCorte = form.getTxtProgCorte().getText().trim();
		String observacao = form.getTxtObservation().getText().trim();
		String duplicada = form.getChkDuplicada().isSelected() ? "S" : "";

		if (linha.isEmpty() || dataPlano.isEmpty() || dataEntrega.isEmpty() || progCorte.isEmpty()) {
			sectorView.showMessage("Preencha Linha, Data de programação, Data de recebimento e Ref. de corte.");
			return;
		}

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			java.util.Date dtPlano = sdf.parse(dataPlano);
			java.util.Date dtEntrega = sdf.parse(dataEntrega);

			Order existing = dao.findByOrder(orderNumber);

			if (existing == null) {
				dao.insertThermalCutting(orderNumber, linha, dtPlano, dtEntrega, progCorte, observacao, duplicada);
				sectorView.showMessage("Apontamento térmico cadastrado com sucesso.");
			} else {
				dao.updateThermalCutting(orderNumber, linha, dtPlano, dtEntrega, progCorte, observacao, duplicada);
				sectorView.showMessage("Apontamento térmico atualizado com sucesso.");
			}

			sectorView.clearAllFields();
			//ThermalCuttingMemory.clear();

		} catch (Exception e) {
			e.printStackTrace();
			sectorView.showMessage("Erro ao registrar apontamento térmico.");
		}
	}
}
