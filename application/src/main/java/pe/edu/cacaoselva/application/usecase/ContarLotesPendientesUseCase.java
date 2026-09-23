package pe.edu.cacaoselva.application.usecase;
import pe.edu.cacaoselva.application.port.LoteRepository; import pe.edu.cacaoselva.domain.model.EstadoLote;
public class ContarLotesPendientesUseCase { private final LoteRepository loteRepository; public ContarLotesPendientesUseCase(LoteRepository loteRepository){this.loteRepository=loteRepository;} public long ejecutar(){return loteRepository.findAll().stream().filter(lote->lote.estado()==EstadoLote.PENDIENTE).count();} }
