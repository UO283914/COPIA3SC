package app.view;

import javax.swing.*;
import java.awt.*;

public class InformeIngresosView {

	private JFrame frame;
	private JTable tableEventos;      
	private JTable tableTarifaPlana;  
	private JTable tableSinTarifa;    
	
	private JLabel lblAgenciaNombre;
	private JLabel lblEventoSeleccionado;
	private JLabel lblSumatorioTarifa;
	private JLabel lblSumatorioSinTarifa;
	private JButton btnVolver;

	public InformeIngresosView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Informe de Ingresos por Temática");
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 10));

		// --- PANEL SUPERIOR: Eventos ---
		JPanel pnlSuperior = new JPanel();
		pnlSuperior.setLayout(new BoxLayout(pnlSuperior, BoxLayout.Y_AXIS));
		pnlSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		lblAgenciaNombre = new JLabel("Agencia: Cargando...");
		lblAgenciaNombre.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlSuperior.add(lblAgenciaNombre);
		pnlSuperior.add(Box.createVerticalStrut(10));

		pnlSuperior.add(new JLabel("Eventos agrupados por temática:"));
		pnlSuperior.add(Box.createVerticalStrut(5));

		tableEventos = new JTable();
		JScrollPane scrollEventos = new JScrollPane(tableEventos);
		scrollEventos.setPreferredSize(new Dimension(950, 200));
		pnlSuperior.add(scrollEventos);

		frame.getContentPane().add(pnlSuperior, BorderLayout.NORTH);

		// --- PANEL CENTRAL: Detalles del evento ---
		JPanel pnlCentral = new JPanel(new BorderLayout());
		pnlCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		lblEventoSeleccionado = new JLabel("Detalles del evento seleccionado: [Ninguno]");
		lblEventoSeleccionado.setFont(new Font("Tahoma", Font.ITALIC, 12));
		pnlCentral.add(lblEventoSeleccionado, BorderLayout.NORTH);

		JPanel pnlTablasInferiores = new JPanel(new GridLayout(1, 2, 15, 0));
		
		JPanel pnlIzquierda = new JPanel(new BorderLayout());
		pnlIzquierda.add(new JLabel("Empresas con tarifa plana:"), BorderLayout.NORTH);
		tableTarifaPlana = new JTable();
		pnlIzquierda.add(new JScrollPane(tableTarifaPlana), BorderLayout.CENTER);
		lblSumatorioTarifa = new JLabel("Sumatorio con tarifa plana: 0.00 €");
		lblSumatorioTarifa.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlIzquierda.add(lblSumatorioTarifa, BorderLayout.SOUTH);

		JPanel pnlDerecha = new JPanel(new BorderLayout());
		pnlDerecha.add(new JLabel("Empresas sin tarifa plana:"), BorderLayout.NORTH);
		tableSinTarifa = new JTable();
		pnlDerecha.add(new JScrollPane(tableSinTarifa), BorderLayout.CENTER);
		lblSumatorioSinTarifa = new JLabel("Sumatorio sin tarifa plana: 0.00 €");
		lblSumatorioSinTarifa.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlDerecha.add(lblSumatorioSinTarifa, BorderLayout.SOUTH);

		pnlTablasInferiores.add(pnlIzquierda);
		pnlTablasInferiores.add(pnlDerecha);
		pnlCentral.add(pnlTablasInferiores, BorderLayout.CENTER);

		frame.getContentPane().add(pnlCentral, BorderLayout.CENTER);

		// --- PANEL INFERIOR: Botonera ---
		JPanel pnlInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnVolver = new JButton("Volver");
		pnlInferior.add(btnVolver);
		
		frame.getContentPane().add(pnlInferior, BorderLayout.SOUTH);
	}

	public JFrame getFrame() { return frame; }
	public JTable getTableEventos() { return tableEventos; }
	public JTable getTableTarifaPlana() { return tableTarifaPlana; }
	public JTable getTableSinTarifa() { return tableSinTarifa; }
	public JLabel getLblAgenciaNombre() { return lblAgenciaNombre; }
	public JLabel getLblEventoSeleccionado() { return lblEventoSeleccionado; }
	public JLabel getLblSumatorioTarifa() { return lblSumatorioTarifa; }
	public JLabel getLblSumatorioSinTarifa() { return lblSumatorioSinTarifa; }
	public JButton getBtnVolver() { return btnVolver; }
}