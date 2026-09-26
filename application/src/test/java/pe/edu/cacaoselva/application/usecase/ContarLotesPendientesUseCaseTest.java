package pe.edu.cacaoselva.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContarLotesPendientesUseCaseTest {

    private LoteRepository repository;
    private ContarLotesPendientesUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoteRepository.class);
        useCase = new ContarLotesPendientesUseCase(repository);
    }

    @Test
    void debeContarLotesPendientes() {

        List<Lote> lotes = List.of(
                new Lote(
                        1,
                        "Carlos",
                        new BigDecimal("150.50"),
                        EstadoLote.PENDIENTE
                ),
                new Lote(
                        2,
                        "Ana",
                        new BigDecimal("100.00"),
                        EstadoLote.LIQUIDADO
                ),
                new Lote(
                        3,
                        "Luis",
                        new BigDecimal("80.00"),
                        EstadoLote.PENDIENTE
                )
        );

        when(repository.findAll()).thenReturn(lotes);

        long resultado = useCase.ejecutar();

        assertEquals(2, resultado);

        verify(repository, times(1)).findAll();
    }
}