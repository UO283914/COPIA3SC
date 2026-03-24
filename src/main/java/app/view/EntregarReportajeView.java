package app.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import app.dto.EntregarReportajeDTO;
import javax.swing.JList;

public class EntregarReportajeView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldTitulo;
    private JTextField textFieldSubtitulo;
    private JTextArea textAreaCuerpo;
    private JComboBox<EntregarReportajeDTO> comboEventos;
    private JComboBox<String> comboFiltro;
    private JButton btnAceptar;
    private JButton btnRestaurarVersion;
    private JButton btnSolicitarRevision;
    private JLabel lblPermisos;
    private JComboBox<String> comboVersiones;
    private JTextField textFieldSubtituloVersion;
    private JTextArea textAreaCuerpoVersion;
    private JTextField textFieldRutaImagen;
    private JTextField textFieldRutaVideo;
    private JButton btnAnadirImagen;
    private JButton btnAnadirVideo;
    private JComboBox<EntregarReportajeDTO> comboImagenesDefinitivas;
    private JComboBox<EntregarReportajeDTO> comboVideosDefinitivos;
    private JComboBox<EntregarReportajeDTO> comboImagenesBorrador;
    private JComboBox<EntregarReportajeDTO> comboVideosBorrador;
    private JButton btnCambiarFotoABorrador;
    private JButton btnCambiarVideoABorrador;
    private JButton btnCambiarFotoADefinitiva;
    private JButton btnCambiarVideoADefinitivo;
    private JButton btnEliminarFoto;
    private JButton btnEliminarVideo;

    public JComboBox<EntregarReportajeDTO> getComboEventos() {
        return comboEventos;
    }

    public JComboBox<String> getComboFiltro() {
        return comboFiltro;
    }

    public JComboBox<String> getComboVersiones() {
        return comboVersiones;
    }

    public JComboBox<EntregarReportajeDTO> getComboImagenesDefinitivas() {
        return comboImagenesDefinitivas;
    }

    public JComboBox<EntregarReportajeDTO> getComboVideosDefinitivos() {
        return comboVideosDefinitivos;
    }

    public JComboBox<EntregarReportajeDTO> getComboImagenesBorrador() {
        return comboImagenesBorrador;
    }

    public JComboBox<EntregarReportajeDTO> getComboVideosBorrador() {
        return comboVideosBorrador;
    }

    public int getIndiceVersionSeleccionada() {
        return comboVersiones.getSelectedIndex();
    }

    public String getTitulo() {
        return textFieldTitulo.getText();
    }

    public String getSubtitulo() {
        return textFieldSubtitulo.getText();
    }

    public String getCuerpo() {
        return textAreaCuerpo.getText();
    }

    public String getRutaImagen() {
        return textFieldRutaImagen.getText();
    }

    public String getRutaVideo() {
        return textFieldRutaVideo.getText();
    }

    public void setTitulo(String titulo) {
        textFieldTitulo.setText(titulo == null ? "" : titulo);
    }

    public void setSubtitulo(String subtitulo) {
        textFieldSubtitulo.setText(subtitulo == null ? "" : subtitulo);
    }

    public void setCuerpo(String cuerpo) {
        textAreaCuerpo.setText(cuerpo == null ? "" : cuerpo);
    }

    public void setSubtituloVersion(String subtitulo) {
        textFieldSubtituloVersion.setText(subtitulo == null ? "" : subtitulo);
    }

    public void setCuerpoVersion(String cuerpo) {
        textAreaCuerpoVersion.setText(cuerpo == null ? "" : cuerpo);
    }

    public void limpiarVersionSeleccionada() {
        setSubtituloVersion("");
        setCuerpoVersion("");
    }

    public void setTituloEditable(boolean editable) {
        textFieldTitulo.setEditable(editable);
    }

    public void setContenidoEditable(boolean editable) {
        textFieldSubtitulo.setEditable(editable);
        textAreaCuerpo.setEditable(editable);
    }

    public void setMultimediaEditable(boolean editable) {
        textFieldRutaImagen.setEditable(editable);
        textFieldRutaVideo.setEditable(editable);
        btnAnadirImagen.setEnabled(editable);
        btnAnadirVideo.setEnabled(editable);
        comboImagenesDefinitivas.setEnabled(editable);
        comboVideosDefinitivos.setEnabled(editable);
        comboImagenesBorrador.setEnabled(editable);
        comboVideosBorrador.setEnabled(editable);
        btnCambiarFotoABorrador.setEnabled(editable);
        btnCambiarVideoABorrador.setEnabled(editable);
        btnCambiarFotoADefinitiva.setEnabled(editable);
        btnCambiarVideoADefinitivo.setEnabled(editable);
        btnEliminarFoto.setEnabled(editable);
        btnEliminarVideo.setEnabled(editable);
    }

    public void setSeccionVersionesVisible(boolean visible) {
        comboVersiones.setEnabled(visible);
        textFieldSubtituloVersion.setEnabled(visible);
        textAreaCuerpoVersion.setEnabled(visible);
        btnRestaurarVersion.setEnabled(visible);
    }

    public void setTextoBotonAceptar(String texto) {
        btnAceptar.setText(texto);
    }

    public void setSolicitarRevisionEnabled(boolean enabled) {
        btnSolicitarRevision.setEnabled(enabled);
    }

    public void setMensajePermisos(String mensaje) {
        lblPermisos.setText(mensaje == null ? "" : mensaje);
    }

    public void addAceptarListener(ActionListener listener) {
        btnAceptar.addActionListener(listener);
    }

    public void addRestaurarVersionListener(ActionListener listener) {
        btnRestaurarVersion.addActionListener(listener);
    }

    public void addSolicitarRevisionListener(ActionListener listener) {
        btnSolicitarRevision.addActionListener(listener);
    }

    public void addFiltroListener(ActionListener listener) {
        comboFiltro.addActionListener(listener);
    }

    public void addEventoListener(ActionListener listener) {
        comboEventos.addActionListener(listener);
    }

    public void addVersionListener(ActionListener listener) {
        comboVersiones.addActionListener(listener);
    }

    public void addAnadirImagenListener(ActionListener listener) {
        btnAnadirImagen.addActionListener(listener);
    }

    public void addAnadirVideoListener(ActionListener listener) {
        btnAnadirVideo.addActionListener(listener);
    }

    public void addCambiarFotoABorradorListener(ActionListener listener) {
        btnCambiarFotoABorrador.addActionListener(listener);
    }

    public void addCambiarVideoABorradorListener(ActionListener listener) {
        btnCambiarVideoABorrador.addActionListener(listener);
    }

    public void addCambiarFotoADefinitivaListener(ActionListener listener) {
        btnCambiarFotoADefinitiva.addActionListener(listener);
    }

    public void addCambiarVideoADefinitivoListener(ActionListener listener) {
        btnCambiarVideoADefinitivo.addActionListener(listener);
    }

    public void addEliminarFotoListener(ActionListener listener) {
        btnEliminarFoto.addActionListener(listener);
    }

    public void addEliminarVideoListener(ActionListener listener) {
        btnEliminarVideo.addActionListener(listener);
    }

    public void limpiarCampos() {
        textFieldTitulo.setText("");
        textFieldSubtitulo.setText("");
        textAreaCuerpo.setText("");
        limpiarMultimedia();
    }

    public void limpiarRutaImagen() {
        textFieldRutaImagen.setText("");
    }

    public void limpiarRutaVideo() {
        textFieldRutaVideo.setText("");
    }

    public void limpiarMultimedia() {
        limpiarRutaImagen();
        limpiarRutaVideo();
        comboImagenesDefinitivas.removeAllItems();
        comboVideosDefinitivos.removeAllItems();
        comboImagenesBorrador.removeAllItems();
        comboVideosBorrador.removeAllItems();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EntregarReportajeView frame = new EntregarReportajeView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EntregarReportajeView() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1300, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblFiltro = new JLabel("Filtro de eventos");
        lblFiltro.setBounds(10, 11, 120, 14);
        contentPane.add(lblFiltro);

        comboFiltro = new JComboBox<>();
        comboFiltro.addItem("Pendientes de entrega");
        comboFiltro.addItem("Entregados");
        comboFiltro.setBounds(10, 31, 570, 22);
        contentPane.add(comboFiltro);

        JLabel lblEventos = new JLabel("Eventos asignados");
        lblEventos.setBounds(10, 64, 140, 14);
        contentPane.add(lblEventos);

        comboEventos = new JComboBox<>();
        comboEventos.setBounds(10, 84, 570, 22);
        contentPane.add(comboEventos);

        JLabel lblNewLabelTitulo = new JLabel("Título");
        lblNewLabelTitulo.setBounds(10, 117, 46, 14);
        contentPane.add(lblNewLabelTitulo);

        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(10, 137, 400, 30);
        contentPane.add(textFieldTitulo);
        textFieldTitulo.setColumns(10);

        JLabel lblNewLabelSubtitulo = new JLabel("Subtítulo actual");
        lblNewLabelSubtitulo.setBounds(10, 178, 100, 14);
        contentPane.add(lblNewLabelSubtitulo);

        textFieldSubtitulo = new JTextField();
        textFieldSubtitulo.setBounds(10, 198, 340, 30);
        contentPane.add(textFieldSubtitulo);
        textFieldSubtitulo.setColumns(10);

        JLabel lblNewLabelCuerpo = new JLabel("Cuerpo actual");
        lblNewLabelCuerpo.setBounds(10, 239, 90, 14);
        contentPane.add(lblNewLabelCuerpo);

        textAreaCuerpo = new JTextArea();
        textAreaCuerpo.setLineWrap(true);
        textAreaCuerpo.setWrapStyleWord(true);

        JScrollPane scrollCuerpo = new JScrollPane(textAreaCuerpo);
        scrollCuerpo.setBounds(10, 259, 340, 238);
        contentPane.add(scrollCuerpo);

        JLabel lblVersiones = new JLabel("Versiones (fecha/hora y cambios)");
        lblVersiones.setBounds(365, 177, 230, 14);
        contentPane.add(lblVersiones);

        comboVersiones = new JComboBox<>();
        comboVersiones.setBounds(368, 198, 320, 22);
        contentPane.add(comboVersiones);

        JLabel lblSubtituloVersion = new JLabel("Subtítulo versión seleccionada");
        lblSubtituloVersion.setBounds(367, 230, 190, 14);
        contentPane.add(lblSubtituloVersion);

        textFieldSubtituloVersion = new JTextField();
        textFieldSubtituloVersion.setEditable(false);
        textFieldSubtituloVersion.setBounds(369, 249, 320, 30);
        contentPane.add(textFieldSubtituloVersion);
        textFieldSubtituloVersion.setColumns(10);

        JLabel lblCuerpoVersion = new JLabel("Cuerpo versión seleccionada");
        lblCuerpoVersion.setBounds(364, 292, 180, 14);
        contentPane.add(lblCuerpoVersion);

        textAreaCuerpoVersion = new JTextArea();
        textAreaCuerpoVersion.setEditable(false);
        textAreaCuerpoVersion.setLineWrap(true);
        textAreaCuerpoVersion.setWrapStyleWord(true);
        JScrollPane scrollVersion = new JScrollPane(textAreaCuerpoVersion);
        scrollVersion.setBounds(370, 312, 323, 186);
        contentPane.add(scrollVersion);

        JLabel lblRutaImagen = new JLabel("Ruta de imagen");
        lblRutaImagen.setBounds(703, 178, 120, 14);
        contentPane.add(lblRutaImagen);

        textFieldRutaImagen = new JTextField();
        textFieldRutaImagen.setBounds(704, 196, 446, 30);
        contentPane.add(textFieldRutaImagen);
        textFieldRutaImagen.setColumns(10);

        btnAnadirImagen = new JButton("Añadir imagen");
        btnAnadirImagen.setBounds(1160, 202, 114, 23);
        contentPane.add(btnAnadirImagen);

        JLabel lblRutaVideo = new JLabel("Ruta de video");
        lblRutaVideo.setBounds(703, 239, 120, 14);
        contentPane.add(lblRutaVideo);

        textFieldRutaVideo = new JTextField();
        textFieldRutaVideo.setBounds(704, 257, 446, 30);
        contentPane.add(textFieldRutaVideo);
        textFieldRutaVideo.setColumns(10);

        btnAnadirVideo = new JButton("Añadir video");
        btnAnadirVideo.setBounds(1160, 261, 114, 23);
        contentPane.add(btnAnadirVideo);

        lblPermisos = new JLabel("");
        lblPermisos.setBounds(10, 508, 930, 14);
        contentPane.add(lblPermisos);

        btnRestaurarVersion = new JButton("Restaurar versión");
        btnRestaurarVersion.setBounds(446, 547, 160, 23);
        contentPane.add(btnRestaurarVersion);

        btnSolicitarRevision = new JButton("Solicitar revisión");
        btnSolicitarRevision.setBounds(1000, 517, 155, 23);
        contentPane.add(btnSolicitarRevision);

        btnAceptar = new JButton("Entregar");
        btnAceptar.setBounds(1165, 517, 109, 23);
        contentPane.add(btnAceptar);

        JLabel lblDefinitivo = new JLabel("DEFINITIVO");
        lblDefinitivo.setBounds(703, 292, 70, 14);
        contentPane.add(lblDefinitivo);

        JLabel lblBorrador = new JLabel("BORRADOR");
        lblBorrador.setBounds(1016, 292, 90, 14);
        contentPane.add(lblBorrador);

        JLabel lblImagenesDefinitivas = new JLabel("Imágenes");
        lblImagenesDefinitivas.setBounds(713, 318, 90, 14);
        contentPane.add(lblImagenesDefinitivas);

        JLabel lblVideosDefinitivos = new JLabel("Videos");
        lblVideosDefinitivos.setBounds(713, 393, 46, 14);
        contentPane.add(lblVideosDefinitivos);

        JLabel lblImagenesBorrador = new JLabel("Imágenes");
        lblImagenesBorrador.setBounds(1026, 318, 70, 14);
        contentPane.add(lblImagenesBorrador);

        JLabel lblVideosBorrador = new JLabel("Videos");
        lblVideosBorrador.setBounds(1026, 393, 46, 14);
        contentPane.add(lblVideosBorrador);

        comboImagenesDefinitivas = new JComboBox<>();
        comboImagenesDefinitivas.setBounds(712, 335, 275, 22);
        contentPane.add(comboImagenesDefinitivas);

        comboVideosDefinitivos = new JComboBox<>();
        comboVideosDefinitivos.setBounds(713, 418, 275, 22);
        contentPane.add(comboVideosDefinitivos);

        btnCambiarFotoABorrador = new JButton("Cambiar foto a Borrador");
        btnCambiarFotoABorrador.setBounds(758, 362, 160, 23);
        contentPane.add(btnCambiarFotoABorrador);

        btnCambiarVideoABorrador = new JButton("Cambiar video a Borrador");
        btnCambiarVideoABorrador.setBounds(758, 451, 160, 23);
        contentPane.add(btnCambiarVideoABorrador);

        comboImagenesBorrador = new JComboBox<>();
        comboImagenesBorrador.setBounds(1027, 335, 250, 22);
        contentPane.add(comboImagenesBorrador);

        comboVideosBorrador = new JComboBox<>();
        comboVideosBorrador.setBounds(1026, 418, 248, 22);
        contentPane.add(comboVideosBorrador);

        btnCambiarFotoADefinitiva = new JButton("Cambiar a Definitivo");
        btnCambiarFotoADefinitiva.setBounds(1026, 362, 138, 23);
        contentPane.add(btnCambiarFotoADefinitiva);

        btnEliminarFoto = new JButton("Eliminar foto");
        btnEliminarFoto.setBounds(1174, 362, 100, 23);
        contentPane.add(btnEliminarFoto);

        btnCambiarVideoADefinitivo = new JButton("Cambiar a Definitivo");
        btnCambiarVideoADefinitivo.setBounds(1026, 451, 138, 23);
        contentPane.add(btnCambiarVideoADefinitivo);

        btnEliminarVideo = new JButton("Eliminar video");
        btnEliminarVideo.setBounds(1174, 451, 100, 23);
        contentPane.add(btnEliminarVideo);
    }
}