package app.controller;



import javax.swing.JOptionPane;
import java.util.List;


import app.model.AccederReportajeModel;
import app.model.AppMainModel;
import app.view.AccederReportajeView;
import app.view.AppMainView;
import giis.demo.util.Database; // Asumo que esta es la ruta de tu BD según mensajes anteriores
import giis.demo.util.SwingUtil;
import app.model.EscogerEventoFreelanceModel;
import app.view.EscogerEventoFreelanceView;


public class AppMainController {

	private AppMainView view;
	private AppMainModel model;

	/**
	 * Constructor del Controlador. Recibe la vista como parámetro.
	 */
	public AppMainController(AppMainView v, AppMainModel m) {
		this.view = v;
		this.model = m;

		// Inicializamos los "escuchadores" de eventos
		this.initView();
		this.initController();
	}

	/**
	 * Este método enlaza los botones de la Vista con la lógica del Controlador
	 */
	private void initController() {
		// Hacemos visible la ventana
		view.getFrame().setVisible(true);
		// ==========================================
		// EVENTOS DE LA BASE DE DATOS
		// ==========================================
		this.view.addCrearBDListener(e -> crearBaseDeDatos());
		this.view.addCargarBDListener(e -> cargarBaseDeDatos());

		// ==========================================
		// EVENTOS DE LOS ROLES
		// ==========================================

		// Reportero
		this.view.addEntregarReportajeListener(e -> ejecutarEntregarReportaje());
		this.view.addRevisarReportajesListener(e -> ejecutarRevisarReportajes());
		this.view.addEscogerEventoFreelanceListener(e -> ejecutarEscogerEventoFreelance());


		// Agente
		this.view.addAccionAgente1Listener(e -> ejecutarAccionAgente1());
		this.view.addAccionAgente2Listener(e -> ejecutarAccionAgente2());
		this.view.addAccionInformeEventosListener(e -> accionInformeEventos());

		// Empresa
        this.view.addAccionEmpresa1Listener(e -> ejecutarAccionEmpresa1());
        this.view.addOfrecerReportajesListener(e -> ejecutarOfrecerReportajes()); 
        view.addAccederReportajesListener(e -> ejecutarAccederReportajes());
        
        // NUEVO: Conectamos el clic del botón con su método
        view.addInformeReportajesListener(e -> ejecutarInformeReportajes());
        
        this.view.addInformeIngresosListener(e -> ejecutarInformeIngresos());

	}

	public void initView() {
		asegurarDatosDemoAccesos();
		actualizarComboBoxes();
		view.getFrame().setVisible(true);
		// Agregar artículos a la lista.
	}

	/**
	 * Garantiza que estén cargados los datos demo usados por DarAccesoEmpresa
	 * (empresas 7,8,9,10 y sus ofrecimientos asociados en el dataReportajes).
	 * Si no están, recarga dataset completo para evitar pantallas vacías/incompletas.
	 */
	private void asegurarDatosDemoAccesos() {
		Database db = new Database();
		try {
			List<Object[]> rows = db.executeQueryArray(
					"SELECT COUNT(*) FROM Empresa_Comunicacion WHERE id_empresa IN (7,8,9,10)");
			int count = (rows == null || rows.isEmpty()) ? 0 : ((Number) rows.get(0)[0]).intValue();
			if (count < 4) {
				db.loadDatabase();
			}
		} catch (RuntimeException ex) {
			// Si la BD no existe o está incompleta, se recargan datos demo.
			db.loadDatabase();
		}
	}

	// LÓGICA DE NEGOCIO (Los métodos que hacen el trabajo de verdad)
	private void crearBaseDeDatos() {
		System.out.println("Controlador: Creando base de datos en blanco...");
		Database db = new Database();
		db.createDatabase(false);

		actualizarComboBoxes();
	}

	private void cargarBaseDeDatos() {
		System.out.println("Controlador: Cargando datos de prueba...");
		Database db = new Database();
		db.loadDatabase();

		actualizarComboBoxes();
	}

	// --- Lógica del Reportero ---
		private void ejecutarEntregarReportaje() {
			//Mensaje de error si no se ha seleccionado ningún reportero en el combobox
			if (view.getComboReportero().getSelectedItem() == null) {
				SwingUtil.showMessage("Debes seleccionar un reportero", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String reporteroSeleccionado = (String) view.getComboReportero().getSelectedItem();

			app.model.EntregarReportajeModel modelo = new app.model.EntregarReportajeModel();
			app.view.EntregarReportajeView vista = new app.view.EntregarReportajeView();
			new app.controller.EntregarReportajeController(modelo, vista, reporteroSeleccionado);
		}
		
		private void ejecutarRevisarReportajes() {
			if (view.getComboReportero().getSelectedItem() == null) {
				SwingUtil.showMessage("Debes seleccionar un reportero", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String reporteroSeleccionado = (String) view.getComboReportero().getSelectedItem();

			app.model.RevisarReportajeModel modelo = new app.model.RevisarReportajeModel();
			app.view.RevisarReportajesView vista = new app.view.RevisarReportajesView();
			new app.controller.RevisarReportajeController(modelo, vista, reporteroSeleccionado);
		}
			
		private void ejecutarEscogerEventoFreelance() {
			if (view.getComboReportero().getSelectedItem() == null) {
				SwingUtil.showMessage("Debes seleccionar un reportero", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String reporteroSeleccionado = (String) view.getComboReportero().getSelectedItem();
			EscogerEventoFreelanceModel modelo = new EscogerEventoFreelanceModel();

			if (!modelo.esFreelance(reporteroSeleccionado)) {
				SwingUtil.showMessage("El reportero seleccionado no es freelance.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			EscogerEventoFreelanceView vista = new EscogerEventoFreelanceView();
			new EscogerEventoFreelanceController(modelo, vista, reporteroSeleccionado);
		}


	// --- Lógica del Agente ---
	private void ejecutarAccionAgente1() {
		// Mensaje de error si no se ha seleccionado ningún agente en el combobox
		if (view.getComboAgente().getSelectedItem() == null) {
			SwingUtil.showMessage("Debes seleccionar un agente", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String agenteSeleccionado = (String) view.getComboAgente().getSelectedItem();
		System.out.println("Agente seleccionado: " + agenteSeleccionado);
		System.out.println("Controlador: Ejecutando acción 1 del Agente.");

		// ==========================================
		// CÓDIGO NUEVO: AQUÍ SE ABRE TU PANTALLA
		// ==========================================

		// 1. Instanciamos tu modelo y tu vista (usando tu paquete unificado app.asignacion)
		app.model.AsignacionReporterosModel asigModel = new app.model.AsignacionReporterosModel();
		app.view.AsignarReporterosView asigView = new app.view.AsignarReporterosView();

		// 2. Instanciamos tu controlador inyectándole el modelo y la vista
		app.controller.AsignacionReporterosController asigController = new app.controller.AsignacionReporterosController(asigModel, asigView, agenteSeleccionado);
		// 3. Inicializamos tu controlador (activa tus botones)
		asigController.initController();

		// 4. Hacemos visible tu ventana
		asigView.getFrame().setVisible(true);
	}

	private void ejecutarAccionAgente2() {
		// Mensaje de error si no se ha seleccionado ningún agente en el combobox
		if (view.getComboAgente().getSelectedItem() == null) {
			giis.demo.util.SwingUtil.showMessage("Debes seleccionar un agente", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtenemos el nombre de la agencia seleccionada
		String agenteSeleccionado = (String) view.getComboAgente().getSelectedItem();
		System.out.println("Agente seleccionado para distribuir reportaje: " + agenteSeleccionado);

		// Arrancamos el MVC de la historia 33528
		app.model.DarAccesoEmpresaModel modeloAcceso = new app.model.DarAccesoEmpresaModel();
		app.view.DarAccesoEmpresaView vistaAcceso = new app.view.DarAccesoEmpresaView();
		new app.controller.DarAccesoEmpresaController(modeloAcceso, vistaAcceso, agenteSeleccionado);
	}


	// --- Lógica de la Empresa ---
	private void ejecutarAccionEmpresa1() {
		if (view.getComboEmpresa().getSelectedItem() == null) {
			SwingUtil.showMessage("Debes seleccionar una empresa", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String empresaSeleccionada = (String) view.getComboEmpresa().getSelectedItem();

		// ¡Magia! Creamos el Modelo, la Vista y el Controlador de la nueva pantalla
		app.model.gestionarOfrecimientosReportajesModel ofreModel = new app.model.gestionarOfrecimientosReportajesModel();
		app.view.gestionarOfrecimientosReportajesView ofreView = new app.view.gestionarOfrecimientosReportajesView();

		// Le pasamos el nombre de la empresa para que sepa de quién cargar los datos
		new app.controller.gestionarOfrecimientosReportajesController(ofreModel, ofreView, empresaSeleccionada);
	}

	private void ejecutarOfrecerReportajes() {
	    // 1. Validamos que haya una AGENCIA seleccionada (combo central), no la empresa
	    if (view.getComboAgente().getSelectedItem() == null) {
	        SwingUtil.showMessage("Debes seleccionar una agencia de prensa", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    // 2. Capturamos el nombre de la agencia seleccionada
	    String agenciaSeleccionada = (String) view.getComboAgente().getSelectedItem();
	    System.out.println("Agencia seleccionada para ofrecer reportajes: " + agenciaSeleccionada);

	    // 3. Instanciamos tu modelo y vista
	    app.model.OfrecerReportajesModel miModelo = new app.model.OfrecerReportajesModel();
	    app.view.OfrecerReportajesView miVista = new app.view.OfrecerReportajesView();

	    // 4. Instanciamos tu controlador pasándole el nombre de la AGENCIA
	    // Importante: Ahora le pasamos 3 parámetros para cumplir con el filtrado solicitado
	    new app.controller.OfrecerReportajesController(miModelo, miVista, agenciaSeleccionada);
	}
	
	// El método que lanza la ventana
	private void ejecutarAccederReportajes() {
	    String empresa = (String) view.getComboEmpresa().getSelectedItem();
	    if (empresa != null) {
	        new AccederReportajeController(new AccederReportajeModel(), new AccederReportajeView(), empresa);
	    }
	}
	
	/**
	 * Limpia y vuelve a cargar los datos de los ComboBoxes desde la base de datos.
	 */
	/**
	 * Limpia y vuelve a cargar los datos de los ComboBoxes desde la base de datos.
	 */
	private void actualizarComboBoxes() {
		try {
			// 1. Limpiamos los comboboxes por si tenían datos viejos
			view.getComboReportero().removeAllItems();
			view.getComboAgente().removeAllItems();
			view.getComboEmpresa().removeAllItems();

			// 2. Pedimos los datos al modelo y rellenamos Reporteros
			java.util.List<app.dto.AppMainDTO> reporteros = model.getListaReporteros();
			for (app.dto.AppMainDTO rep : reporteros) {
				view.getComboReportero().addItem(rep.getNombre());
			}

			// 3. Rellenamos Agencias (Para el combo del Agente)
			java.util.List<app.dto.AppMainDTO> agencias = model.getListaAgencias();
			for (app.dto.AppMainDTO agencia : agencias) {
				view.getComboAgente().addItem(agencia.getNombre());
			}

			// 4. Rellenamos Empresas
			java.util.List<app.dto.AppMainDTO> empresas = model.getListaEmpresas();
			for (app.dto.AppMainDTO emp : empresas) {
				view.getComboEmpresa().addItem(emp.getNombre());
			}

		} catch (giis.demo.util.UnexpectedException e) {
			// Si da error (por ejemplo, porque la tabla Reportero no existe aún), 
			// atrapamos el error para que la aplicación NO crashee.
			System.out.println("Aviso: Base de datos no inicializada. Los combos aparecerán vacíos.");
		}
	}
	
	private void accionInformeEventos() {
        //Mensaje de error si no se ha seleccionado ninguna agencia en el combobox
        if (view.getComboAgente().getSelectedItem() == null) {
            SwingUtil.showMessage("Debes seleccionar una agencia", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String agenciaSeleccionada = (String) view.getComboAgente().getSelectedItem();
        
        // Instanciamos el MVC del informe del evento
        app.model.informeEventoModel model = new app.model.informeEventoModel();
        app.view.informeEventoView vista = new app.view.informeEventoView();
        
        new app.controller.informeEventoController(model, vista, agenciaSeleccionada);
    }
	// NUEVO MÉTODO: Arranca el MVC del Informe de Reportajes
    private void ejecutarInformeReportajes() {
        // 1. Validamos que haya una EMPRESA seleccionada
        if (view.getComboEmpresa().getSelectedItem() == null) {
            SwingUtil.showMessage("Debes seleccionar una empresa de comunicación", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 2. Capturamos el nombre de la empresa seleccionada
        String empresaSeleccionada = (String) view.getComboEmpresa().getSelectedItem();
        
        // 3. Instanciamos el MVC del informe pasándole el nombre de la empresa
        app.model.informeReportajesModel modelo = new app.model.informeReportajesModel();
        app.view.informeReportajesView vista = new app.view.informeReportajesView();
        
        new app.controller.informeReportajesController(modelo, vista, empresaSeleccionada);
    }
    
    private void ejecutarInformeIngresos() {
		// 1. Validamos que haya una AGENCIA seleccionada
		if (view.getComboAgente().getSelectedItem() == null) {
			giis.demo.util.SwingUtil.showMessage("Debes seleccionar una agencia de prensa", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// 2. Capturamos el nombre de la agencia seleccionada en el desplegable
		String agenciaSeleccionada = (String) view.getComboAgente().getSelectedItem();
		
		// 3. Instanciamos el MVC del informe
		app.model.InformeIngresosModel modelo = new app.model.InformeIngresosModel();
		app.view.InformeIngresosView vista = new app.view.InformeIngresosView();
		
		// 4. Lanzamos el controlador pasándole el nombre de la agencia
		app.controller.InformeIngresosController controlador = new app.controller.InformeIngresosController(modelo, vista, agenciaSeleccionada);
		
		// 5. ¡AQUÍ ESTABA EL ERROR! Faltaba decirle a la vista que se mostrara
		vista.getFrame().setVisible(true);
	}
	
}
