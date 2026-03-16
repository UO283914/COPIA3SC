package app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class gestionarOfrecimientosReportajesView {

    private JFrame frame;
    private JTable tabOfrecimientos;
    
    private JRadioButton rdbtnPendientes;
    private JRadioButton rdbtnConDecision;
    
    private JCheckBox chkEspecializacion;
    private JComboBox<String> comboTematicas;
    
    private JTextField txtPrecioMin;
    private JTextField txtPrecioMax;
    private JButton btnAplicarFiltros;
    private JButton btnLimpiarFiltros; // NUEVO BOTÓN
    
    private JButton btnAceptar;
    private JButton btnRechazar;
    private JButton btnEliminarDecision; 
    private JButton btnVolver;
    
    private JLabel lblEmpresaActual;

    public gestionarOfrecimientosReportajesView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Gestión de Ofrecimientos");
        frame.setBounds(100, 100, 950, 600); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 10));
        frame.setContentPane(contentPane);

        // --- CABECERA Y FILTROS ---
        JPanel panelNorte = new JPanel(new GridLayout(4, 1)); 
        
        lblEmpresaActual = new JLabel("🏢 Empresa: ");
        panelNorte.add(lblEmpresaActual);
        
        // Fila 1: Estado
        JPanel panelFiltrosEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltrosEstado.add(new JLabel("Estado de Decisión: "));
        rdbtnPendientes = new JRadioButton("Ver PENDIENTES de decisión");
        rdbtnConDecision = new JRadioButton("Ver con DECISIÓN TOMADA");
        rdbtnPendientes.setSelected(true); 
        ButtonGroup grupoFiltros = new ButtonGroup();
        grupoFiltros.add(rdbtnPendientes);
        grupoFiltros.add(rdbtnConDecision);
        panelFiltrosEstado.add(rdbtnPendientes);
        panelFiltrosEstado.add(rdbtnConDecision);
        panelNorte.add(panelFiltrosEstado);

        // Fila 2: Temáticas
        JPanel panelFiltrosTematicas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkEspecializacion = new JCheckBox("Mostrar solo eventos de mis especializaciones");
        comboTematicas = new JComboBox<String>();
        comboTematicas.setPreferredSize(new Dimension(200, 25)); 
        panelFiltrosTematicas.add(chkEspecializacion);
        panelFiltrosTematicas.add(new JLabel("  Filtrar por temática: "));
        panelFiltrosTematicas.add(comboTematicas);
        panelNorte.add(panelFiltrosTematicas);
        
        // Fila 3: Precios
        JPanel panelFiltrosPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtPrecioMin = new JTextField(6);
        txtPrecioMax = new JTextField(6);
        btnAplicarFiltros = new JButton("Aplicar Filtros");
        btnLimpiarFiltros = new JButton("Limpiar Filtros"); // INICIALIZAMOS EL BOTÓN
        
        panelFiltrosPrecio.add(new JLabel("Precio Mínimo (€): "));
        panelFiltrosPrecio.add(txtPrecioMin);
        panelFiltrosPrecio.add(new JLabel("  Precio Máximo (€): "));
        panelFiltrosPrecio.add(txtPrecioMax);
        panelFiltrosPrecio.add(new JLabel("   ")); 
        panelFiltrosPrecio.add(btnAplicarFiltros);
        panelFiltrosPrecio.add(new JLabel(" ")); 
        panelFiltrosPrecio.add(btnLimpiarFiltros); // LO AÑADIMOS A LA PANTALLA
        panelNorte.add(panelFiltrosPrecio);
        
        contentPane.add(panelNorte, BorderLayout.NORTH);

        // --- TABLA ÚNICA CENTRAL ---
        tabOfrecimientos = new JTable();
        tabOfrecimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabOfrecimientos.setDefaultEditor(Object.class, null);
        contentPane.add(new JScrollPane(tabOfrecimientos), BorderLayout.CENTER);

        // --- BOTONES SUR ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnVolver = new JButton("Volver");
        btnRechazar = new JButton("Rechazar");
        btnAceptar = new JButton("Aceptar");
        btnEliminarDecision = new JButton("Eliminar Decisión");
        
        panelSur.add(btnVolver);
        panelSur.add(btnRechazar);
        panelSur.add(btnAceptar);
        panelSur.add(btnEliminarDecision);
        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

    public JFrame getFrame() { return frame; }
    public JTable getTabOfrecimientos() { return tabOfrecimientos; }
    public JRadioButton getRdbtnPendientes() { return rdbtnPendientes; }
    public JRadioButton getRdbtnConDecision() { return rdbtnConDecision; }
    public JCheckBox getChkEspecializacion() { return chkEspecializacion; }
    public JComboBox<String> getComboTematicas() { return comboTematicas; }
    public JTextField getTxtPrecioMin() { return txtPrecioMin; }
    public JTextField getTxtPrecioMax() { return txtPrecioMax; }
    
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnRechazar() { return btnRechazar; }
    public JButton getBtnEliminarDecision() { return btnEliminarDecision; }

    public void setNombreEmpresa(String nombre) { lblEmpresaActual.setText("🏢 Empresa: " + nombre); }

    // --- LISTENERS ---
    public void addAceptarListener(ActionListener listener) { btnAceptar.addActionListener(listener); }
    public void addRechazarListener(ActionListener listener) { btnRechazar.addActionListener(listener); }
    public void addEliminarDecisionListener(ActionListener listener) { btnEliminarDecision.addActionListener(listener); }
    public void addVolverListener(ActionListener listener) { btnVolver.addActionListener(listener); }
    
    public void addAplicarFiltrosListener(ActionListener listener) { btnAplicarFiltros.addActionListener(listener); }
    public void addLimpiarFiltrosListener(ActionListener listener) { btnLimpiarFiltros.addActionListener(listener); } // NUEVO
    
    public void addFiltroEstadoListener(ActionListener listener) { 
        rdbtnPendientes.addActionListener(listener);
        rdbtnConDecision.addActionListener(listener);
    }
    public void addChkEspecializacionListener(ActionListener listener) { chkEspecializacion.addActionListener(listener); }
    public void addComboTematicasListener(ActionListener listener) { comboTematicas.addActionListener(listener); } // RESTAURADO
}