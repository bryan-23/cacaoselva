package pe.edu.cacaoselva.application.port;

import pe.edu.cacaoselva.domain.model.Lote;

import java.util.List;
import java.util.Optional;

public interface LoteRepository {

    List<Lote> findAll();

    Optional<Lote> findById(Integer id);

    Lote save(Lote lote);
}