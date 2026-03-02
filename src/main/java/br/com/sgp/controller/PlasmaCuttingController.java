package br.com.sgp.controller;

import java.time.format.DateTimeFormatter;

import javax.swing.JButton;

import br.com.sgp.view.sector.form.PlasmaCuttingForm;

public class PlasmaCuttingController {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final PlasmaCuttingForm form;

	public PlasmaCuttingController(PlasmaCuttingForm form) {
		this.form = form;
		initActions();
	}

	private void initActions() {
		
		for (JButton btn : form.getRackButtons()) {
	        configurarRack(btn);
	    }

	    configurarRack(form.getBtnFixedSupport());
	    configurarRack(form.getBtnMobileSupport());
	    
	    form.addCleanRackListener(e -> {
	        form.clearRackField();
	    });
	}
	
	private void configurarRack(JButton button) {
        button.addActionListener(e -> {
            form.getTxtRack().setText(button.getText());
        });
    }
}