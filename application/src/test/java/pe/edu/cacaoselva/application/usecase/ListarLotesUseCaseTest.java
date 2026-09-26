package pe.edu.cacaoselva.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarLotesUseCaseTest {

    private LoteRepository repository;
    private ListarLotesUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoteRepository.class);
        useCase = new ListarLotesUseCase(repository);
    }

    @Test
    void debeListarTodosLosLotes() {

        List<Lote> lotes = List.of(
                new Lote(
                        1,
                        "Ana",
                        new BigDecimal("120.5"),
                        EstadoLote.PENDIENTE
                ),
                new Lote(
                        2,
                        "Luis",
                        new BigDecimal("80"),
                        EstadoLote.LIQUIDADO
                )
        );

        when(repository.findAll()).thenReturn(lotes);

        List<Lote> resultado = useCase.ejecutar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository, times(1)).findAll();
    }
}
