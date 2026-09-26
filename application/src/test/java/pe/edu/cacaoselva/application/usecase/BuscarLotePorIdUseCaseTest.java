package pe.edu.cacaoselva.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.domain.model.EstadoLote;
import pe.edu.cacaoselva.domain.model.Lote;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarLotePorIdUseCaseTest {

    private LoteRepository repository;
    private BuscarLotePorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoteRepository.class);
        useCase = new BuscarLotePorIdUseCase(repository);
    }

    @Test
    void debeEncontrarUnLotePorId() {

        Lote lote = new Lote(
                1,
                "Ana",
                new BigDecimal("120.5"),
                EstadoLote.PENDIENTE
        );

        when(repository.findById(1))
                .thenReturn(Optional.of(lote));

        Lote resultado = useCase.ejecutar(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        assertEquals("Ana", resultado.socio());
        assertEquals(
                new BigDecimal("120.5"),
                resultado.pesoKg()
        );

        verify(repository, times(1))
                .findById(1);
    }

    @Test
    void debeLanzarExcepcionSiNoExisteElLote() {

        when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> useCase.ejecutar(999)
        );

        verify(repository, times(1))
                .findById(999);
    }
}
