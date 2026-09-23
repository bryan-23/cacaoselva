package pe.edu.cacaoselva.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaLoteRepositoryAdapter implements LoteRepository {

    private final LoteJpaRepository repository;

    public JpaLoteRepositoryAdapter(LoteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Lote> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Lote> findById(Integer id) {
        return repository.findById(id.longValue())
                .map(this::toDomain);
    }

    @Override
    public Lote save(Lote lote) {

        LoteEntity entity = new LoteEntity();

        entity.setSocio(lote.socio());
        entity.setPesoKg(lote.pesoKg());
        entity.setEstado(lote.estado().name());

        LoteEntity guardado = repository.save(entity);

        return toDomain(guardado);
    }

    private Lote toDomain(LoteEntity entity) {

        EstadoLote estado =
                EstadoLote.valueOf(entity.getEstado());

        return new Lote(
                entity.getId().intValue(),
                entity.getSocio(),
                entity.getPesoKg(),
                estado
        );
    }
}