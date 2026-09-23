package pe.edu.cacaoselva.application.port;
import pe.edu.cacaoselva.domain.model.EstadoLote; import java.math.BigDecimal;
public record LoteDto(Integer id, String socio, BigDecimal pesoKg, EstadoLote estado) { }
