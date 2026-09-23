package pe.edu.cacaoselva.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.cacaoselva.api.dto.CrearLoteRequest;
import pe.edu.cacaoselva.application.usecase.BuscarLotePorIdUseCase;
import pe.edu.cacaoselva.application.usecase.CrearLoteUseCase;
import pe.edu.cacaoselva.application.usecase.ListarLotesUseCase;
import pe.edu.cacaoselva.domain.model.Lote;

@RestController
@RequestMapping("/lotes")
@CrossOrigin(origins = "*")
public class LoteController {

    private final ListarLotesUseCase listarLotes;
    private final BuscarLotePorIdUseCase buscarLote;
    private final CrearLoteUseCase crearLote;

    public LoteController(
            ListarLotesUseCase listarLotes,
            BuscarLotePorIdUseCase buscarLote,
            CrearLoteUseCase crearLote) {

        this.listarLotes = listarLotes;
        this.buscarLote = buscarLote;
        this.crearLote = crearLote;
    }

    // ==========================================
    // GET /lotes
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Lote>> listar() {

        return ResponseEntity.ok(
                listarLotes.ejecutar()
        );
    }

    // ==========================================
    // GET /lotes/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                buscarLote.ejecutar(id)
        );
    }

    // ==========================================
    // POST /lotes
    // ==========================================

    @PostMapping
    public ResponseEntity<Lote> crear(
            @RequestBody CrearLoteRequest request) {

        Lote lote = crearLote.ejecutar(
                request.socio(),
                request.pesoKg(),
                request.estado()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lote);
    }
}