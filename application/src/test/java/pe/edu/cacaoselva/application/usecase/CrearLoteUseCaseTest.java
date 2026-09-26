package pe.edu.cacaoselva.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearLoteUseCaseTest {

    private LoteRepository repository;
    private CrearLoteUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoteRepository.class);
        useCase = new CrearLoteUseCase(repository);
    }

    @Test
    void debeCrearUnLoteCorrectamente() {

        Lote loteGuardado = new Lote(
                4,
                "Carlos",
                new BigDecimal("150.50"),
                EstadoLote.PENDIENTE
        );

        when(repository.save(any(Lote.class)))
                .thenReturn(loteGuardado);

        Lote resultado = useCase.ejecutar(
                "Carlos",
                new BigDecimal("150.50"),
                EstadoLote.PENDIENTE
        );

        assertNotNull(resultado);
        assertEquals(4, resultado.id());
        assertEquals("Carlos", resultado.socio());
        assertEquals(
                new BigDecimal("150.50"),
                resultado.pesoKg()
        );
        assertEquals(
                EstadoLote.PENDIENTE,
                resultado.estado()
        );

        verify(repository, times(1))
                .save(any(Lote.class));
    }
}
