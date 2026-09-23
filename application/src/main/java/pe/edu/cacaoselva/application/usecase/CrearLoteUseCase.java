package pe.edu.cacaoselva.application.usecase;

import java.math.BigDecimal;

import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

public class CrearLoteUseCase {

    private final LoteRepository repository;

    public CrearLoteUseCase(LoteRepository repository) {
        this.repository = repository;
    }

    public Lote ejecutar(
            String socio,
            BigDecimal pesoKg,
            EstadoLote estado) {

        if (socio == null || socio.isBlank()) {
            throw new IllegalArgumentException(
                    "El socio es obligatorio"
            );
        }

        if (pesoKg == null ||
                pesoKg.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El peso debe ser mayor que 0"
            );
        }

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado es obligatorio"
            );
        }

        Lote lote = new Lote(
                null,
                socio,
                pesoKg,
                estado
        );

        return repository.save(lote);
    }
}