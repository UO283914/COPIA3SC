package app.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import app.dto.AccederReportajeDTO;
import app.model.AccederReportajeModel;
import app.view.AccederReportajeView;
import giis.demo.util.SwingUtil;

public class AccederReportajeController {

    private AccederReportajeModel model;
    private AccederReportajeView view;
    private String nombreEmpresa;
    private AccederReportajeDTO detalleActual;

    public AccederReportajeController(AccederReportajeModel m, AccederReportajeView v, String empresa) {
        this.model = m;
        this.view = v;
        this.nombreEmpresa = empresa;
        this.initView();
    }

    public void initView() {
        view.lblNombreEmpresa.setText("EMPRESA: " + nombreEmpresa.toUpperCase());
        cargarEventos();

        view.tableEventos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarDetalleReportaje();
                }
            }
        });

        view.btnDescargarJson.addActionListener(e -> descargarReportajeJson());
        view.setVisible(true);
    }

    private void cargarEventos() {
        List<AccederReportajeDTO> eventos = model.getEventosConAcceso(nombreEmpresa);
        String[] columnas = { "id_evento", "nombre_evento" };
        view.tableEventos.setModel(SwingUtil.getTableModelFromPojos(eventos, columnas));
    }

    private void cargarDetalleReportaje() {
        int row = view.tableEventos.getSelectedRow();
        if (row == -1) {
            limpiarDetalle();
            return;
        }

        int idEvento = (int) view.tableEventos.getValueAt(row, 0);
        detalleActual = model.getDetalleUltimaVersion(idEvento);

        if (detalleActual == null) {
            limpiarDetalle();
            return;
        }

        view.txtTitulo.setText(detalleActual.getTitulo());
        view.txtSubtitulo.setText(detalleActual.getSubtitulo());
        view.txtCuerpo.setText(detalleActual.getCuerpo());
        view.lblFechaVersion.setText("Fecha: " + detalleActual.getFecha());
        view.lblHoraVersion.setText("Hora: " + detalleActual.getHora());
        view.lblPreviewFotos.setText(generarHtmlMultimedia("No hay fotos definitivas asociadas.", detalleActual.getFotos()));
        view.lblPreviewVideos.setText(generarHtmlMultimedia("No hay videos definitivos asociados.", detalleActual.getVideos()));
    }

    private void limpiarDetalle() {
        detalleActual = null;
        view.txtTitulo.setText("");
        view.txtSubtitulo.setText("");
        view.txtCuerpo.setText("");
        view.lblFechaVersion.setText("Fecha: --/--/----");
        view.lblHoraVersion.setText("Hora: --:--");
        view.lblPreviewFotos.setText(generarHtmlMultimedia("No hay fotos definitivas asociadas.", null));
        view.lblPreviewVideos.setText(generarHtmlMultimedia("No hay videos definitivos asociados.", null));
    }

    private String generarHtmlMultimedia(String mensajeVacio, List<String> rutas) {
        if (rutas == null || rutas.isEmpty()) {
            return "<html><div style='text-align:center; padding:12px;'>" + mensajeVacio + "</div></html>";
        }

        StringBuilder sb = new StringBuilder("<html><div style='padding:8px;'>");
        for (int i = 0; i < rutas.size(); i++) {
            sb.append(i + 1).append(". ").append(rutas.get(i));
            if (i < rutas.size() - 1) {
                sb.append("<br/><br/>");
            }
        }
        sb.append("</div></html>");
        return sb.toString();
    }

    private void descargarReportajeJson() {
        if (detalleActual == null) {
            SwingUtil.showMessage("Debes seleccionar un reportaje antes de descargarlo.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reportaje en JSON");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON", "json"));
        fileChooser.setSelectedFile(new File(sanitizarNombreFichero(detalleActual.getTitulo()) + ".json"));

        int userSelection = fileChooser.showSaveDialog(view);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = asegurarExtensionJson(fileChooser.getSelectedFile());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(fileToSave, crearJsonExportable());

            int idEvento = (int) view.tableEventos.getValueAt(view.tableEventos.getSelectedRow(), 0);
            model.marcarReportajeComoDescargado(idEvento, nombreEmpresa);

            SwingUtil.showMessage("Reportaje exportado correctamente en:\n" + fileToSave.getAbsolutePath(), "Exito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            SwingUtil.showMessage("Error al guardar el archivo JSON.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File asegurarExtensionJson(File fichero) {
        if (fichero.getName().toLowerCase().endsWith(".json")) {
            return fichero;
        }
        return new File(fichero.getParentFile(), fichero.getName() + ".json");
    }

    private String sanitizarNombreFichero(String nombre) {
        return nombre.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    private Map<String, Object> crearJsonExportable() {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("titulo", detalleActual.getTitulo());
        json.put("subtitulo", detalleActual.getSubtitulo());
        json.put("cuerpo", detalleActual.getCuerpo());
        json.put("fotos", detalleActual.getFotos());
        json.put("videos", detalleActual.getVideos());
        return json;
    }
}