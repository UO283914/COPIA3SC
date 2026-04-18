package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.dto.EmpresaDisplayDTO;
import app.dto.informeReportajesDTO;
import giis.demo.util.ApplicationException;
import giis.demo.util.Database;

class DarAccesoEmpresaModelTest {

    private final Database db = new Database();
    private final DarAccesoEmpresaModel accesoModel = new DarAccesoEmpresaModel();
    private final informeReportajesModel informeModel = new informeReportajesModel();

    @BeforeEach
    void setUp() {
        db.loadDatabase();
    }

    @Test
    void shouldExposeAllPaymentCombinationsForEvent20WithoutAccess() {
        List<EmpresaDisplayDTO> empresas = accesoModel.getEmpresasAceptadasByAcceso(20, false);
        Set<Integer> ids = empresas.stream().map(EmpresaDisplayDTO::getIdEmpresa).collect(Collectors.toSet());

        assertTrue(ids.containsAll(Set.of(7, 8, 9, 10)),
                "El evento 20 debe incluir las empresas de prueba (7,8,9,10) en el listado sin acceso.");

        EmpresaDisplayDTO emp7 = empresas.stream().filter(e -> e.getIdEmpresa() == 7).findFirst().orElseThrow();
        EmpresaDisplayDTO emp8 = empresas.stream().filter(e -> e.getIdEmpresa() == 8).findFirst().orElseThrow();
        EmpresaDisplayDTO emp9 = empresas.stream().filter(e -> e.getIdEmpresa() == 9).findFirst().orElseThrow();
        EmpresaDisplayDTO emp10 = empresas.stream().filter(e -> e.getIdEmpresa() == 10).findFirst().orElseThrow();

        assertEquals(1, emp7.getTieneTarifa());
        assertEquals(1, emp7.getAlCorrientePago());
        assertEquals(1, emp7.getElegiblePago());

        assertEquals(1, emp8.getTieneTarifa());
        assertEquals(0, emp8.getAlCorrientePago());
        assertEquals(0, emp8.getElegiblePago());

        assertEquals(0, emp9.getTieneTarifa());
        assertEquals(1, emp9.getReportajePagado());
        assertEquals(1, emp9.getElegiblePago());

        assertEquals(0, emp10.getTieneTarifa());
        assertEquals(0, emp10.getReportajePagado());
        assertEquals(0, emp10.getElegiblePago());
    }

    @Test
    void shouldGrantAccessOnlyToEligibleCompaniesAndThenAppearInDistribution() {
        accesoModel.concederAcceso(20, 7);
        accesoModel.concederAcceso(20, 9);

        assertThrows(ApplicationException.class, () -> accesoModel.concederAcceso(20, 8));
        assertThrows(ApplicationException.class, () -> accesoModel.concederAcceso(20, 10));

        List<EmpresaDisplayDTO> conAcceso = accesoModel.getEmpresasAceptadasByAcceso(20, true);
        Set<Integer> idsConAcceso = conAcceso.stream().map(EmpresaDisplayDTO::getIdEmpresa).collect(Collectors.toSet());
        assertTrue(idsConAcceso.contains(7));
        assertTrue(idsConAcceso.contains(9));

        List<informeReportajesDTO> informeEmpresa7 =
                informeModel.obtenerInforme("Noticias Norte", "2026-10-01", "2026-10-31");
        List<informeReportajesDTO> informeEmpresa9 =
                informeModel.obtenerInforme("Revista Horizonte", "2026-10-01", "2026-10-31");

        assertEquals(1, informeEmpresa7.size());
        assertEquals("Premios Princesa: Todo un Exito", informeEmpresa7.get(0).getTituloReportaje());
        assertEquals(1, informeEmpresa9.size());
        assertEquals("Premios Princesa: Todo un Exito", informeEmpresa9.get(0).getTituloReportaje());
    }

    @Test
    void shouldHandleAcceptedAndAccessValuesStoredAsTextVariants() {
        db.executeUpdate("UPDATE Ofrecimiento SET estado = 'aceptado ', tiene_acceso = 'false' "
                + "WHERE id_evento = 20 AND id_empresa = 7");

        List<EmpresaDisplayDTO> empresas = accesoModel.getEmpresasAceptadasByAcceso(20, false);
        Set<Integer> ids = empresas.stream().map(EmpresaDisplayDTO::getIdEmpresa).collect(Collectors.toSet());
        assertTrue(ids.contains(7));
    }
}
