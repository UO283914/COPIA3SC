package app.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.controller.AppMainController;
import app.controller.informeReportajesController;
import app.model.informeReportajesModel;

public class AppMainView {

	private JFrame frame;
	private JPanel contentPane;

	// ==========================================
	// COMPONENTES (Privados para el MVC)
	// ==========================================

	// ComboBoxes
	private JComboBox<String> comboReportero;
	private JComboBox<String> comboAgente;
	private JComboBox<String> comboEmpresa;

	// Botones de las listas verticales (Añade más aquí según necesitéis)
	private JButton btnEntregarReportaje;
	private JButton btnRevisarReportajes;
	private JButton btnAccionAgente1;
	private JButton btnAccionEmpresa1;
	private JButton btnOfrecerReportajes; // Historia 33526
	private JButton btnAccionInformeEventos;
	private JButton btnAccederReportajes; // Historia 33529
    private JButton btnInformeReportajes; // NUEVO BOTÓN: Historia 34083
	
	// Botones de Base de Datos
	private JButton btnCrearBD;
	private JButton btnCargarBD;
	private JButton btnAccionAgente2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// En Java Swing, la interfaz gráfica debe arrancarse en este hilo especial
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Instanciamos el modelo
					app.model.AppMainModel modelo = new app.model.AppMainModel();
					// Instanciamos la vista
					AppMainView vista = new AppMainView();

					// Instanciamos el controlador y le "inyectamos" la vista
					// El controlador se encarga de darle vida a los botones
					AppMainController controlador = new AppMainController(vista,modelo);

					// Mostramos la ventana


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppMainView() {
		initialize();
	}

	public void initialize() {
		// Configuración principal de la ventana
		frame = new JFrame();

		frame.setTitle("Login del Sistema");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 850, 450);
		frame.setLocationRelativeTo(null); // Centra la ventana al abrir


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// PANEL CENTRAL (GridBagLayout para la cuadrícula principal)
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		// El 1.0 en la fila 2 permite que los paneles de botones se expandan hacia abajo
		gbl_panelCentral.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE}; 
		panelCentral.setLayout(gbl_panelCentral);

		// --- FILA 1: ETIQUETAS ---
		JLabel lblReportero = new JLabel("Reportero");
		GridBagConstraints gbc_lblReportero = new GridBagConstraints();
		gbc_lblReportero.insets = new Insets(10, 10, 10, 10);
		gbc_lblReportero.gridx = 0;
		gbc_lblReportero.gridy = 0;
		panelCentral.add(lblReportero, gbc_lblReportero);


		JLabel lblAgente = new JLabel("Agente de prensa");
		GridBagConstraints gbc_lblAgente = new GridBagConstraints();
		gbc_lblAgente.insets = new Insets(10, 10, 10, 10);
		gbc_lblAgente.gridx = 1;
		gbc_lblAgente.gridy = 0;
		panelCentral.add(lblAgente, gbc_lblAgente);


		JLabel lblEmpresa = new JLabel("Empresa de comunicación");
		GridBagConstraints gbc_lblEmpresa = new GridBagConstraints();
		gbc_lblEmpresa.insets = new Insets(10, 10, 10, 10);
		gbc_lblEmpresa.gridx = 2;
		gbc_lblEmpresa.gridy = 0;
		panelCentral.add(lblEmpresa, gbc_lblEmpresa);

		// --- FILA 2: COMBOBOXES ---
		comboReportero = new JComboBox<String>();
		GridBagConstraints gbc_comboReportero = new GridBagConstraints();
		gbc_comboReportero.insets = new Insets(0, 10, 10, 10);
		gbc_comboReportero.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboReportero.gridx = 0;
		gbc_comboReportero.gridy = 1;
		panelCentral.add(comboReportero, gbc_comboReportero);

		comboAgente = new JComboBox<String>();
		GridBagConstraints gbc_comboAgente = new GridBagConstraints();
		gbc_comboAgente.insets = new Insets(0, 10, 10, 10);
		gbc_comboAgente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAgente.gridx = 1;
		gbc_comboAgente.gridy = 1;
		panelCentral.add(comboAgente, gbc_comboAgente);

		comboEmpresa = new JComboBox<String>();
		GridBagConstraints gbc_comboEmpresa = new GridBagConstraints();
		gbc_comboEmpresa.insets = new Insets(0, 10, 10, 10);
		gbc_comboEmpresa.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEmpresa.gridx = 2;
		gbc_comboEmpresa.gridy = 1;
		panelCentral.add(comboEmpresa, gbc_comboEmpresa);

		// --- FILA 3: PANELES CON LISTAS VERTICALES DE BOTONES ---

		// Columna 1: Botones de Reportero
		JPanel panelBotonesReportero = new JPanel(new GridLayout(0, 1, 0, 10)); 
		GridBagConstraints gbc_panelBotonesReportero = new GridBagConstraints();
		gbc_panelBotonesReportero.insets = new Insets(15, 10, 10, 10);
		gbc_panelBotonesReportero.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotonesReportero.anchor = GridBagConstraints.NORTH; // Los ancla arriba
		gbc_panelBotonesReportero.gridx = 0;
		gbc_panelBotonesReportero.gridy = 2;
		panelCentral.add(panelBotonesReportero, gbc_panelBotonesReportero);

		btnEntregarReportaje = new JButton("EntregarReportaje");
		panelBotonesReportero.add(btnEntregarReportaje);
		btnRevisarReportajes = new JButton("Revisar reportajes");
		panelBotonesReportero.add(btnRevisarReportajes);


		// Columna 2: Botones de Agente de Prensa
		JPanel panelBotonesAgente = new JPanel(new GridLayout(0, 1, 0, 10));
		GridBagConstraints gbc_panelBotonesAgente = new GridBagConstraints();
		gbc_panelBotonesAgente.insets = new Insets(15, 10, 10, 10);
		gbc_panelBotonesAgente.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotonesAgente.anchor = GridBagConstraints.NORTH;
		gbc_panelBotonesAgente.gridx = 1;
		gbc_panelBotonesAgente.gridy = 2;
		panelCentral.add(panelBotonesAgente, gbc_panelBotonesAgente);

		btnAccionAgente1 = new JButton("Asignar Reporteros");
		panelBotonesAgente.add(btnAccionAgente1);

		btnAccionAgente2 = new JButton("Dar Accesos a Empresas");
		panelBotonesAgente.add(btnAccionAgente2);
		
		btnAccionInformeEventos = new JButton("Informe de eventos");
		panelBotonesAgente.add(btnAccionInformeEventos);

		btnOfrecerReportajes = new JButton("Ofrecer reportajes");
		panelBotonesAgente.add(btnOfrecerReportajes);

		
		// Columna 3: Botones de Empresa de Comunicación
		JPanel panelBotonesEmpresa = new JPanel(new GridLayout(0, 1, 0, 10));
		GridBagConstraints gbc_panelBotonesEmpresa = new GridBagConstraints();
		gbc_panelBotonesEmpresa.insets = new Insets(15, 10, 10, 10);
		gbc_panelBotonesEmpresa.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotonesEmpresa.anchor = GridBagConstraints.NORTH;
		gbc_panelBotonesEmpresa.gridx = 2;
		gbc_panelBotonesEmpresa.gridy = 2;
		panelCentral.add(panelBotonesEmpresa, gbc_panelBotonesEmpresa);

		btnAccionEmpresa1 = new JButton("Gestionar ofrecimientos de reportajes");
		panelBotonesEmpresa.add(btnAccionEmpresa1);
		

		btnAccederReportajes = new JButton("Visualizar reportajes multimedia");
		panelBotonesEmpresa.add(btnAccederReportajes);

        // NUEVO: Añadimos el botón del informe a la interfaz
        btnInformeReportajes = new JButton("Informe de reportajes adquiridos");
        panelBotonesEmpresa.add(btnInformeReportajes);


		// PANEL INFERIOR (Botones Base de Datos)
		JPanel panelInferior = new JPanel();
		panelInferior.setBorder(new EmptyBorder(30, 0, 0, 0)); // Margen superior de 30px
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

		btnCrearBD = new JButton("Crear base de datos");
		panelInferior.add(btnCrearBD);

		btnCargarBD = new JButton("Cargar base de datos");
		panelInferior.add(btnCargarBD);
	}

	// MÉTODOS PARA EL PATRÓN MVC (Listeners y Getters)

	// Listeners para la Base de Datos
	public void addCrearBDListener(ActionListener listener) {
		btnCrearBD.addActionListener(listener);
	}

	public void addCargarBDListener(ActionListener listener) {
		btnCargarBD.addActionListener(listener);
	}

	// Listeners para las acciones de los roles
	public void addEntregarReportajeListener(ActionListener listener) {
		btnEntregarReportaje.addActionListener(listener);
	}

	public void addRevisarReportajesListener(ActionListener listener) {
		btnRevisarReportajes.addActionListener(listener);
	}

	public void addAccionAgente1Listener(ActionListener listener) {
		btnAccionAgente1.addActionListener(listener);
	}

	public void addAccionEmpresa1Listener(ActionListener listener) {
		btnAccionEmpresa1.addActionListener(listener);
	}
	public void addAccionAgente2Listener(ActionListener listener) {
		btnAccionAgente2.addActionListener(listener);
	}
	
	public void addOfrecerReportajesListener(ActionListener listener) {
	    btnOfrecerReportajes.addActionListener(listener);
	}
	
	public void addAccionInformeEventosListener(ActionListener listener) {
	    btnAccionInformeEventos.addActionListener(listener);
	}
	public void addAccederReportajesListener(ActionListener listener) {
        btnAccederReportajes.addActionListener(listener);
    }
    
    // NUEVO: Listener para el botón del informe
    public void addInformeReportajesListener(ActionListener listener) {
        btnInformeReportajes.addActionListener(listener);
    }

	public JFrame getFrame() {
		return frame;
	}

	// Getters para obtener los datos seleccionados en los ComboBoxes
	public JComboBox<String> getComboReportero() { return comboReportero; }
	public JComboBox<String> getComboAgente() { return comboAgente; }
	public JComboBox<String> getComboEmpresa() { return comboEmpresa; }
}