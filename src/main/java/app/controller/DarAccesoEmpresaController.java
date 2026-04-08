package app.controller;

import java.util.List;
import javax.swing.table.TableModel;
import giis.demo.util.SwingUtil;

public class DarAccesoEmpresaController {
	private app.model.DarAccesoEmpresaModel model;
	private app.view.DarAccesoEmpresaView view;
	private String nombreAgencia;

	private List<app.dto.EmpresaDisplayDTO> empresasMemoria;
	private List<app.dto.EmpresaDisplayDTO> empresasFiltradas;
	private boolean filtroConAcceso = false;
	private int filtroPago = 0;

	public DarAccesoEmpresaController(app.model.DarAccesoEmpresaModel m, app.view.DarAccesoEmpresaView v, String nombreAgencia) {
		this.model = m;
		this.view = v;
		this.nombreAgencia = nombreAgencia;
		this.initView();
		this.initController();
	}

	public void initController() {
		view.getTabEventos().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				cargarEmpresasPorFiltro();
			}
		});

		view.getCbFiltroAcceso().addActionListener(e -> {
			filtroConAcceso = view.getCbFiltroAcceso().getSelectedIndex() == 1;
			cargarEmpresasPorFiltro();
		});
		view.getCbFiltroPago().addActionListener(e -> {
			filtroPago = view.getCbFiltroPago().getSelectedIndex();
			cargarEmpresasPorFiltro();
		});

		view.getBtnDarAcceso().addActionListener(e -> ejecutarDarAcceso());
		view.getBtnQuitarAcceso().addActionListener(e -> ejecutarQuitarAcceso());
		view.getBtnAceptar().addActionListener(e -> view.getFrame().dispose());
		view.getBtnCancelar().addActionListener(e -> view.getFrame().dispose());
	}

	public void initView() {
		view.getLblTituloAgencia().setText("Agencia de Prensa: " + nombreAgencia);

		List<app.dto.EventoDisplayDTO> eventos = model.getEventosConReportaje(nombreAgencia);

		TableModel tmodel = SwingUtil.getTableModelFromPojos(eventos, new String[]{"idEvento", "descripcion", "fecha", "tematicas", "finalizado"});
		view.getTabEventos().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getTabEventos());

		view.getTabEventos().getColumnModel().getColumn(0).setMinWidth(0);
		view.getTabEventos().getColumnModel().getColumn(0).setMaxWidth(0);
		view.getTabEventos().getColumnModel().getColumn(0).setWidth(0);

		view.getTabEventos().getColumnModel().getColumn(2).setMinWidth(80);
		view.getTabEventos().getColumnModel().getColumn(2).setMaxWidth(100);
		view.getTabEventos().getColumnModel().getColumn(4).setMinWidth(70);
		view.getTabEventos().getColumnModel().getColumn(4).setMaxWidth(80);

		view.getTabEventos().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
		view.getTabEventos().setRowHeight(25);
		view.getFrame().setVisible(true);
	}

	private void cargarEmpresasPorFiltro() {
		int fila = view.getTabEventos().getSelectedRow();
		if (fila >= 0) {
			Integer idEvento = (Integer) view.getTabEventos().getValueAt(fila, 0);
			empresasMemoria = model.getEmpresasAceptadasByAcceso(idEvento, filtroConAcceso);
			empresasFiltradas = filtrarPorPago(empresasMemoria);
			actualizarTablaEmpresas();
		}
	}

	private void ejecutarDarAcceso() {
		if (filtroConAcceso) {
			SwingUtil.showMessage("El filtro actual solo muestra empresas con acceso. Cambia al filtro sin acceso para conceder.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
			return;
		}

		int filaEvento = view.getTabEventos().getSelectedRow();
		int[] filasSeleccionadas = view.getTabEmpresas().getSelectedRows();
		if (filaEvento == -1 || filasSeleccionadas.length == 0) {
			SwingUtil.showMessage("Debes seleccionar un evento y al menos una empresa.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
			return;
		}

		Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaEvento, 0);
		int concedidas = 0;
		int rechazadas = 0;
		for (int fila : filasSeleccionadas) {
			app.dto.EmpresaDisplayDTO emp = empresasFiltradas.get(fila);
			try {
				model.concederAcceso(idEvento, emp.getIdEmpresa());
				concedidas++;
			} catch (giis.demo.util.ApplicationException ex) {
				rechazadas++;
			}
		}

		if (rechazadas > 0) {
			SwingUtil.showMessage("Acceso concedido a " + concedidas + " empresa(s). "
					+ rechazadas + " empresa(s) no cumplen requisitos de finalización/pago.",
					"Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
		} else {
			SwingUtil.showMessage("Accesos concedidos correctamente.", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		cargarEmpresasPorFiltro();
	}

	private void ejecutarQuitarAcceso() {
		if (!filtroConAcceso) {
			SwingUtil.showMessage("El filtro actual solo muestra empresas sin acceso. Cambia al filtro con acceso para revocar.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
			return;
		}

		int filaEvento = view.getTabEventos().getSelectedRow();
		int[] filasSeleccionadas = view.getTabEmpresas().getSelectedRows();
		if (filaEvento == -1 || filasSeleccionadas.length == 0) {
			SwingUtil.showMessage("Debes seleccionar un evento y al menos una empresa.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
			return;
		}

		Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaEvento, 0);
		int revocadas = 0;
		int bloqueadas = 0;
		for (int fila : filasSeleccionadas) {
			app.dto.EmpresaDisplayDTO emp = empresasFiltradas.get(fila);
			if (emp.getDescargado() != null && emp.getDescargado() == 1) {
				bloqueadas++;
				continue;
			}
			model.revocarAcceso(idEvento, emp.getIdEmpresa());
			revocadas++;
		}

		if (bloqueadas > 0) {
			SwingUtil.showMessage("Se revocó el acceso a " + revocadas + " empresa(s). "
					+ bloqueadas + " empresa(s) ya descargaron el reportaje y no se pueden revocar.",
					"Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
		} else {
			SwingUtil.showMessage("Accesos revocados correctamente.", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		cargarEmpresasPorFiltro();
	}

	private void actualizarTablaEmpresas() {
		TableModel tm = SwingUtil.getTableModelFromPojos(empresasFiltradas, new String[]{"nombre", "acceso", "descarga", "tarifa", "pago"});
		view.getTabEmpresas().setModel(tm);
		view.getTabEmpresas().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
		view.getTabEmpresas().setRowHeight(25);
		view.getTabEmpresas().getColumnModel().getColumn(0).setPreferredWidth(180);
		view.getTabEmpresas().getColumnModel().getColumn(1).setPreferredWidth(90);
		view.getTabEmpresas().getColumnModel().getColumn(2).setPreferredWidth(110);
		view.getTabEmpresas().getColumnModel().getColumn(3).setPreferredWidth(80);
		view.getTabEmpresas().getColumnModel().getColumn(4).setPreferredWidth(80);
	}

	private List<app.dto.EmpresaDisplayDTO> filtrarPorPago(List<app.dto.EmpresaDisplayDTO> base) {
		java.util.ArrayList<app.dto.EmpresaDisplayDTO> out = new java.util.ArrayList<>();
		for (app.dto.EmpresaDisplayDTO emp : base) {
			boolean tieneTarifa = emp.getTieneTarifa() != null && emp.getTieneTarifa() == 1;
			boolean alCorriente = emp.getAlCorrientePago() != null && emp.getAlCorrientePago() == 1;
			boolean reportajePagado = emp.getReportajePagado() != null && emp.getReportajePagado() == 1;
			boolean elegible = emp.getElegiblePago() != null && emp.getElegiblePago() == 1;

			boolean include;
			switch (filtroPago) {
			case 1: // Tarifa vigente
				include = tieneTarifa && alCorriente;
				break;
			case 2: // Sin tarifa + reportaje pagado
				include = !tieneTarifa && reportajePagado;
				break;
			case 3: // No cumple pago
				include = !elegible;
				break;
			default: // Todos
				include = true;
			}
			if (include) {
				out.add(emp);
			}
		}
		return out;
	}
}
