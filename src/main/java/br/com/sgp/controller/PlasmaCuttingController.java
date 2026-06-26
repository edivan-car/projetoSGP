package br.com.sgp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.view.sector.FabricacaoPecasView;
import br.com.sgp.view.sector.form.PlasmaCuttingForm;

public class PlasmaCuttingController {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final PlasmaCuttingForm form;
	private final FabricacaoPecasView sectorView;

	public PlasmaCuttingController(PlasmaCuttingForm form, FabricacaoPecasView view) {
		this.form = form;
		this.sectorView = view;
		initActions();
	}

	private void initActions() {
		
		for (JButton btn : form.getRackButtons()) {
	        configurarRack(btn);
	    }

	    configurarRack(form.getBtnFixedSupport());
	    configurarRack(form.getBtnMobileSupport());
	    
	    form.addCleanRackListener(e -> {
//	        form.clearRackField();
	    	sectorView.clearAllFields();
	        sectorView.clearCard(); // <- incluir: limpa tabela e campo pedido
	        sectorView.setFormsEditable(true);   // <- incluir: reabilita campos
	        sectorView.setRegisterEnabled(true); // <- incluir: reabilita botão
	    });
	    
	    form.getBtnGenerate().addActionListener(e -> {
	        LocalDate date = LocalDate.now();

	        if (form.getChkPreviousDay().isSelected()) {
	            date = date.minusDays(1);
	        }

	        form.getTxtDate().setText(date.format(FORMAT));
	        form.getTxtShift().requestFocusInWindow(); 
	    });
	    
	    form.getBtnRegister().addActionListener(e -> save());
	}
	
	private void configurarRack(JButton button) {
        button.addActionListener(e -> {
            form.getTxtRack().setText(button.getText());
            form.selectRackButton(button);
        });
    }
	
	private void save() {

		String orderNumber = sectorView.getOrderNumber();

	    if (orderNumber == null || orderNumber.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(form, "Nenhum pedido selecionado.");
	        return;
	    }

	    String date     = form.getTxtDate().getText().trim();
	    String shift    = form.getTxtShift().getText().trim();
	    String rack     = form.getTxtRack().getText().trim();
	    String obs      = form.getTxtObservation().getText().trim();

	    if (date.isEmpty() || shift.isEmpty()) {
	        JOptionPane.showMessageDialog(form,
	            "Preencha Data do Corte e Turno antes de cadastrar.");
	        return;
	    }

	    try {
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date dataCorte = sdf.parse(date);

	        new OrderDAO().updatePlasmaCutting(orderNumber, dataCorte, shift, rack, obs);

	        JOptionPane.showMessageDialog(form, "Apontamento registrado com sucesso.");
	        sectorView.clearAllFields();

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(form, "Erro ao registrar apontamento.");
	    }
	}
}
