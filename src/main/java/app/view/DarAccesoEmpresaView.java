package app.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;

public class DarAccesoEmpresaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabEventos;
	private JTable tabEmpresas;
	private JButton btnDarAcceso;
	private JButton btnQuitarAcceso;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JComboBox<String> cbFiltroAcceso;
	private JComboBox<String> cbFiltroPago;

	// El JLabel arriba para poder cambiarlo desde el Controlador
	private JLabel lblNombreAgencia;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DarAccesoEmpresaView frame = new DarAccesoEmpresaView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DarAccesoEmpresaView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1280, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 560, 420);
		contentPane.add(scrollPane);

		tabEventos = new JTable();
		scrollPane.setViewportView(tabEventos);

		lblNombreAgencia = new JLabel("Agencia de Prensa : Mi Agencia");
		lblNombreAgencia.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNombreAgencia.setBounds(10, 9, 450, 22);
		contentPane.add(lblNombreAgencia);

		JLabel lblTituloEventos = new JLabel("Eventos con reportaje entregado");
		lblTituloEventos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTituloEventos.setBounds(10, 94, 320, 14);
		contentPane.add(lblTituloEventos);

		JLabel lblFiltro = new JLabel("Filtro de acceso:");
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFiltro.setBounds(620, 64, 140, 20);
		contentPane.add(lblFiltro);

		cbFiltroAcceso = new JComboBox<>(new String[] {
				"Empresas sin acceso concedido",
				"Empresas con acceso concedido"
		});
		cbFiltroAcceso.setBounds(770, 62, 320, 25);
		contentPane.add(cbFiltroAcceso);

		JLabel lblFiltroPago = new JLabel("Filtro estado de pago:");
		lblFiltroPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFiltroPago.setBounds(620, 94, 140, 20);
		contentPane.add(lblFiltroPago);

		cbFiltroPago = new JComboBox<>(new String[] {
				"Todos",
				"Tarifa vigente y pagada",
				"Sin tarifa y reportaje pagado",
				"No cumple pago"
		});
		cbFiltroPago.setBounds(770, 92, 320, 25);
		contentPane.add(cbFiltroPago);

		JLabel lblTituloEmpresas = new JLabel("Empresas");
		lblTituloEmpresas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTituloEmpresas.setBounds(620, 129, 208, 14);
		contentPane.add(lblTituloEmpresas);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(620, 154, 640, 385);
		contentPane.add(scrollPane_1);

		tabEmpresas = new JTable();
		scrollPane_1.setViewportView(tabEmpresas);

		btnDarAcceso = new JButton("Dar Acceso");
		btnDarAcceso.setBounds(620, 550, 240, 32);
		contentPane.add(btnDarAcceso);

		btnQuitarAcceso = new JButton("Quitar Acceso");
		btnQuitarAcceso.setBounds(1020, 550, 240, 32);
		contentPane.add(btnQuitarAcceso);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(1149, 629, 111, 23);
		contentPane.add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(1022, 629, 117, 23);
		contentPane.add(btnAceptar);
	}

	public JTable getTabEventos() { return tabEventos; }
	public JTable getTabEmpresas() { return tabEmpresas; }
	public JButton getBtnDarAcceso() { return btnDarAcceso; }
	public JButton getBtnQuitarAcceso() { return btnQuitarAcceso; }
	public JButton getBtnCancelar() { return btnCancelar; }
	public JButton getBtnAceptar() { return btnAceptar; }
	public JComboBox<String> getCbFiltroAcceso() { return cbFiltroAcceso; }
	public JComboBox<String> getCbFiltroPago() { return cbFiltroPago; }
	public JFrame getFrame() { return this; }
	public JLabel getLblTituloAgencia() { return this.lblNombreAgencia; }
}
