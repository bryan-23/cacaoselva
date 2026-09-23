package pe.edu.cacaoselva.infrastructure.repository;

import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryLoteRepository implements LoteRepository {

    private final List<Lote> lotes = new ArrayList<>();

    public InMemoryLoteRepository() {

        lotes.add(
            new Lote(
                1,
                "Ana",
                new BigDecimal("120.5"),
                EstadoLote.PENDIENTE
            )
        );

        lotes.add(
            new Lote(
                2,
                "Luis",
                new BigDecimal("80"),
                EstadoLote.LIQUIDADO
            )
        );

        lotes.add(
            new Lote(
                3,
                "Rosa",
                new BigDecimal("95.25"),
                EstadoLote.PENDIENTE
            )
        );
    }

    @Override
    public List<Lote> findAll() {
        return lotes;
    }

    @Override
    public Optional<Lote> findById(Integer id) {

        return lotes.stream()
                .filter(lote -> lote.id().equals(id))
                .findFirst();
    }

    @Override
    public Lote save(Lote lote) {

        int nuevoId = lotes.stream()
                .map(Lote::id)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        Lote nuevoLote = new Lote(
                nuevoId,
                lote.socio(),
                lote.pesoKg(),
                lote.estado()
        );

        lotes.add(nuevoLote);

        return nuevoLote;
    }
}