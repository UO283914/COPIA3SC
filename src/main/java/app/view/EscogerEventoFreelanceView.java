package app.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class EscogerEventoFreelanceView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// Componentes publicos para el controlador
	public JTable tableEventos;
	public JLabel lblNombreFreelance;
	public JLabel lblEspecialidades;
	public JLabel lblAgenciaEvento;
	public JLabel lblFechaEvento;
	public JLabel lblTematicasEvento;
	public JTextArea txtDescripcionEvento;
	public JComboBox<String> comboPreferencia;
	public JButton btnGuardarPreferencia;
	public JButton btnLimpiarSeleccion;
	public JButton btnCerrar;

	public EscogerEventoFreelanceView() {
		setTitle("Seleccion de Eventos para Reportero Freelance");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1080, 640);
		setMinimumSize(new Dimension(1000, 620));

		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		JPanel panelCabecera = new JPanel(new GridLayout(2, 1, 0, 6));
		lblNombreFreelance = new JLabel("Reportero freelance: -");
		lblNombreFreelance.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelCabecera.add(lblNombreFreelance);

		lblEspecialidades = new JLabel("Especialidades: -");
		panelCabecera.add(lblEspecialidades);
		contentPane.add(panelCabecera, BorderLayout.NORTH);

		JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPrincipal.setResizeWeight(0.46);
		splitPrincipal.setDividerLocation(470);
		contentPane.add(splitPrincipal, BorderLayout.CENTER);

		splitPrincipal.setLeftComponent(crearPanelEventos());
		splitPrincipal.setRightComponent(crearPanelDetalle());

		contentPane.add(crearPanelAcciones(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelEventos() {
		JPanel panelEventos = new JPanel(new BorderLayout(0, 8));
		panelEventos.setBorder(BorderFactory.createTitledBorder("Eventos disponibles para freelance"));

		JLabel lblInfo = new JLabel("Solo deben aparecer eventos con tematicas compatibles.");
		panelEventos.add(lblInfo, BorderLayout.NORTH);

		tableEventos = new JTable();
		tableEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEventos.setDefaultEditor(Object.class, null);
		panelEventos.add(new JScrollPane(tableEventos), BorderLayout.CENTER);

		return panelEventos;
	}

	private JPanel crearPanelDetalle() {
		JPanel panelDetalle = new JPanel(new BorderLayout(0, 10));
		panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle del evento y preferencia"));

		JPanel panelDatos = new JPanel(new GridLayout(6, 1, 0, 6));
		lblAgenciaEvento = new JLabel("Agencia: -");
		lblFechaEvento = new JLabel("Fecha: -");
		lblTematicasEvento = new JLabel("Tematicas: -");

		panelDatos.add(lblAgenciaEvento);
		panelDatos.add(lblFechaEvento);
		panelDatos.add(lblTematicasEvento);
		panelDatos.add(new JLabel("Descripcion del evento:"));

		txtDescripcionEvento = new JTextArea();
		txtDescripcionEvento.setEditable(false);
		txtDescripcionEvento.setLineWrap(true);
		txtDescripcionEvento.setWrapStyleWord(true);

		panelDetalle.add(panelDatos, BorderLayout.NORTH);
		panelDetalle.add(new JScrollPane(txtDescripcionEvento), BorderLayout.CENTER);

		JPanel panelPreferencia = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panelPreferencia.setBorder(BorderFactory.createTitledBorder("Estado de preferencia"));
		panelPreferencia.add(new JLabel("Seleccion:"));

		comboPreferencia = new JComboBox<String>();
		comboPreferencia.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Interesado", "No interesado", "Dudando" }));
		comboPreferencia.setPreferredSize(new Dimension(180, 25));
		panelPreferencia.add(comboPreferencia);

		btnGuardarPreferencia = new JButton("Guardar preferencia");
		panelPreferencia.add(btnGuardarPreferencia);

		panelDetalle.add(panelPreferencia, BorderLayout.SOUTH);
		return panelDetalle;
	}

	private JPanel crearPanelAcciones() {
		JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		btnLimpiarSeleccion = new JButton("Limpiar seleccion");
		panelAcciones.add(btnLimpiarSeleccion);

		btnCerrar = new JButton("Cerrar");
		panelAcciones.add(btnCerrar);

		return panelAcciones;
	}
}
